package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.junit.Test;

import model.DeliveryPoint;
import model.MetodoDiConsegna;
import model.Payment;
import model.Product;
import model.User;

/**
 * This class tests the <i>Purchase</i> java class.
 * @author Paolo D'Arienzo
 * @version 1.5
 */
public class TestPurchase {

	/**
	 * This method tests the creation of a purchase.
	 * This implies the creation of products and of an user.
	 * So, the test is about creation of shopping cart, purchase,
	 * and DAO of products, user, delivery point, payment.
	 * Furthermore, tests the management of the shopping cart.
	 * @throws UnknownHostException if an error occurs with the determination the IP address
	 * @throws NoSuchAlgorithmException if an error occurs with the encryption of the password of the user
	 */
	@Test
	public void testCreation() throws UnknownHostException, NoSuchAlgorithmException{
		
		List<Product> products = InitializationForTests.productInit();
		
		for(Product prodotto : products) {
			prodotto.addInDatabase();
		}
		
		User userToTest = InitializationForTests.userInit().get(0);
		userToTest.addInDatabase();
		
		//Testing delivery point
		////Comprehensive of DAO
		DeliveryPoint dp1 = new DeliveryPoint(userToTest.getMail(), "Verona", "Via a", "Civ2", "37064");
		DeliveryPoint dp2 = new DeliveryPoint(userToTest.getMail(), "Verona", "Via b", "Civ2", "80123");
		DeliveryPoint dp3 = new DeliveryPoint(userToTest.getMail(), "Perugia", "Via Cioccolata", "4B", "60281");
		
		userToTest.addDeliveryPoint(dp1);
		userToTest.addDeliveryPoint(dp2);
		userToTest.addDeliveryPoint(dp3);
		assertEquals(userToTest.getDeliveryPoint().get(0).getVia(), "Via a");
		assertEquals(userToTest.getDeliveryPoint().get(1).getVia(), "Via b");
		userToTest.removeDeliveryPoint(dp1);
		assertEquals(userToTest.getDeliveryPoint().get(0).getVia(), "Via b");
		
		//Testing payment
		Payment pagamento = new Payment(userToTest.getMail(), "VISA", "321-321-321");
		//Comprehensive of DAO
		userToTest.addPayment(pagamento);
		assertEquals(userToTest.getPayment().get(0).getCredenziali(), "321-321-321");
		
		//Testing cart
		//Comprehensive of DAO
		userToTest.getShoppingCart().addToCart(products.get(0));
		userToTest.getShoppingCart().addToCart(products.get(1), 3);
		
		assertEquals(userToTest.getShoppingCart().getArticoliInCarrello().get(1).getNumeroProdotto(), 3);
		
		userToTest.getShoppingCart().decrementInCart(products.get(1));
		assertEquals(userToTest.getShoppingCart().getArticoliInCarrello().get(1).getNumeroProdotto(), 2);
		
		//Testing purchase
		userToTest.confirmPurchase(	userToTest.getPayment().get(0),
									MetodoDiConsegna.INGIORNATA,
									userToTest.getDeliveryPoint().get(1),
									InetAddress.getLocalHost().getHostAddress());
		
		//Final price: price n1 + n2*2 (with discount): 89.00 + 582.10*2
		assertEquals(userToTest.getPurchase(0).getFinalPriceFromPurchase(), (float) 1257.20, 0.0f);
		
		//Testing activation of discount when minimum number is reached
		//After purchase, the cart is empty
		assertTrue(userToTest.getShoppingCart().getArticoliInCarrello().isEmpty());
		//Double check: the total should be 0
		assertEquals(userToTest.getShoppingCart().getTotalPrice(), (float) 0.00, 0.0f);
		
		userToTest.getShoppingCart().addToCart(products.get(2), 7);
		assertEquals(userToTest.getShoppingCart().getNumberOfItems(), 7);
		assertEquals(userToTest.getShoppingCart().getTotalPrice(), 315.00, 0.0f);
		//Adding the last item that triggers the discount to be applied
		userToTest.getShoppingCart().addToCart(products.get(2));
		//NB: adding the same product results in adding 1 item of that product
		assertEquals(userToTest.getShoppingCart().getNumberOfItems(), 8);
		assertEquals(userToTest.getShoppingCart().getTotalPrice(), 306.00, 0.0f);
		
		//Testing purchase
		userToTest.confirmPurchase(	userToTest.getPayment().get(0),
									MetodoDiConsegna.CORRIERE,
									userToTest.getDeliveryPoint().get(0),
									InetAddress.getLocalHost().getHostAddress());
		int lastIndex = userToTest.getPurchase().size();
		assertEquals(userToTest.getPurchase(lastIndex - 1).getFinalPriceFromPurchase(), (float) 306.00, 0.0f);
		
		//Cleaning the Database
		userToTest.removePayment(pagamento);
		userToTest.removeDeliveryPoint(dp2);
		userToTest.removeDeliveryPoint(dp3);
		
		for(Product prodotto : products) {
			prodotto.deleteFromDatabase();
		}
		userToTest.deleteFromDatabase();
		
	}

}

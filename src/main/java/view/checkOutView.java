package view;

import java.net.UnknownHostException;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

import controller.Authentication;
import controller.MyUI;
import model.DeliveryPoint;
import model.MetodoDiConsegna;
import model.Payment;
import model.ProductInCart;
import model.User;

/**
 * This class creates the view of the checkout.
 * @author Paolo D'Arienzo
 * @version 2.0
 *
 */
@SuppressWarnings("serial")
public class checkOutView extends VerticalLayout implements View {

	public static final String NAME = "checkout";
	
	private RadioButtonGroup<String> shippingMethod;
	private ComboBox<Payment> paymtMethod;
	private ComboBox<DeliveryPoint> dlvPoint;
	
	/**
	 * Constructor that creates the view of the checkout.
	 */
	public checkOutView() {
		
		Authentication localAuth = (Authentication) UI.getCurrent().getSession().getAttribute("AUTH");
		User user = localAuth.getUser();
		
		if(user.getDeliveryPoint().isEmpty()) {
			Notification notification = new Notification("At least one delivery point \n associated to the account is required.", Type.WARNING_MESSAGE);
			notification.setPosition(Position.MIDDLE_CENTER);
			notification.show(Page.getCurrent());
		}
		else if(user.getPayment().isEmpty()) {
			Notification notification = new Notification("At least one payment method \n associated to the account is required.", Type.WARNING_MESSAGE);
			notification.setPosition(Position.MIDDLE_CENTER);
			notification.show(Page.getCurrent());
		}
		else { //payment and delivery point existing
			
			VerticalLayout riepilogo = new VerticalLayout();
			
			GridLayout gridCart = gridCartCreation(user);
			//gridCart.setMargin(true);
			//gridCart.setWidth("100%");
			
			FormLayout checkout = formLayoutCreation(user);
			
			riepilogo.addComponents(gridCart, checkout);
			riepilogo.setComponentAlignment(gridCart, Alignment.MIDDLE_CENTER);
			riepilogo.setComponentAlignment(checkout, Alignment.MIDDLE_CENTER);
			checkout.setSizeFull();
			
			Button confirmedPurchase = new Button("Confirm purchase");
			confirmedPurchase.setSizeFull();
			confirmedPurchase.setStyleName(ValoTheme.BUTTON_FRIENDLY);
			confirmedPurchase.addStyleName(ValoTheme.BUTTON_LARGE);
			confirmedPurchase.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
			confirmedPurchase.setIcon(VaadinIcons.CHECK);
			
			confirmedPurchase.addClickListener(e -> {
				
				if(		shippingMethod.isEmpty() ||
						paymtMethod.isEmpty() ||
						dlvPoint.isEmpty()) {
					Notification.show("All fields are required", Notification.Type.WARNING_MESSAGE);
				}
				else {
					goConfirmPurchase(localAuth);
					UI.getCurrent().getNavigator().addView(PurchaseConfirmedView.NAME, PurchaseConfirmedView.class);
					UI.getCurrent().getNavigator().navigateTo(PurchaseConfirmedView.NAME);
				}
			});
			
			riepilogo.addComponent(confirmedPurchase);
			
			addComponent(riepilogo);
			setComponentAlignment(riepilogo, Alignment.MIDDLE_CENTER);
			
		} //end else on existence of payment methods and delivery points
		
	}
	
	/**
	 * Creates the grid for the cart
	 * @param user is the user from whom take the cart
	 * @return the grid containing the cart of the user
	 */
	private GridLayout gridCartCreation(User user) {
		
		int cols = 5;
		int rows = 5 + user.getShoppingCart().getArticoliInCarrello().size();
		
		GridLayout gridCart = new GridLayout(cols, rows);
		
		Label captionLabel = new Label("Products in the cart");
		captionLabel.setStyleName("gridlayout-slot");
		
		gridCart.addComponent(captionLabel, 0, 0, cols - 1, 0);
		
		gridCart.addComponent(new Label("Product name"), 0, 1);
		gridCart.addComponent(new Label("Unitary price"), 1, 1);
		gridCart.addComponent(new Label("Number of items"), 2, 1);
		gridCart.addComponent(new Label("Discount rate"), 3, 1);
		gridCart.addComponent(new Label("Total price"), 4, 1);
		
		for(ProductInCart productInCart : user.getShoppingCart().getArticoliInCarrello()) {
			
			java.util.Formatter unitaryPrice = new java.util.Formatter();
			java.util.Formatter productsPrice = new java.util.Formatter();
			
			gridCart.addComponent(new Label(productInCart.getProduct().getNome()));
			
			gridCart.addComponent(new Label(unitaryPrice.format("%.2f", productInCart.getProduct().getPrezzo()).toString() + " €"));
			gridCart.addComponent(new Label(String.valueOf(productInCart.getNumeroProdotto())));
			
			gridCart.addComponent(new Label(String.valueOf(productInCart.getProduct().getSconto()) + "%"));
			
			gridCart.addComponent(new Label(productsPrice.format("%.2f", productInCart.getPrezzo()).toString() + " €"));
			
			unitaryPrice.close();
			productsPrice.close();

		}
		
		Label riepilogoLabel = new Label("RIEPILOGO");
		riepilogoLabel.setStyleName(ValoTheme.LABEL_BOLD);
		gridCart.addComponent(riepilogoLabel, 0, rows - 3, cols - 1, rows - 3);
		
		gridCart.addComponent(new Label("Articoli totali:"), 0, rows - 2, 2, rows - 2);
		gridCart.addComponent(new Label(String.valueOf(user.getShoppingCart().getNumberOfItems())), 
								3, rows - 2, cols - 1, rows - 2);
		
		gridCart.addComponent(new Label("Totale: "), 0, rows - 1, 2, rows - 1);
		
		java.util.Formatter totalPrice = new java.util.Formatter();
		String totalPriceToString = totalPrice.format("%.2f", user.getShoppingCart().getTotalPrice()).toString() +  " €";
		Label totalPriceLabel = new Label(totalPriceToString);
		
		HorizontalLayout finalPriceLayout = new HorizontalLayout();
		finalPriceLayout.addComponent(totalPriceLabel);
		finalPriceLayout.setMargin(true);
		finalPriceLayout.addStyleName("backgroud-colored");
		
		gridCart.addComponent(finalPriceLayout, 3, rows - 1, cols - 1, rows - 1);
		
		totalPrice.close();
		
		return gridCart;
	}
	
	/**
	 * Creates the form for proceeding to the checkout
	 * @param user is the user from whom take the informations of delivery points and payment methods
	 * @return the form for proceeding to the checkout
	 */
	private FormLayout formLayoutCreation(User user) {
		FormLayout checkout = new FormLayout();
		checkout.setSizeFull();
		
		shippingMethod = new RadioButtonGroup<>("Choose shipping method");
		shippingMethod.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
		shippingMethod.setItems("Corriere", "Rapido", "In giornata");
		
		checkout.addComponent(shippingMethod);
		
		paymtMethod = new ComboBox<>("Choose payment method", user.getPayment());
		paymtMethod.setItemCaptionGenerator(Payment::toString);
		
		checkout.addComponent(paymtMethod);
		paymtMethod.setSizeFull();
		
		dlvPoint = new ComboBox<>("Choose delivery point", user.getDeliveryPoint());
		dlvPoint.setItemCaptionGenerator(DeliveryPoint::toString);
		
		checkout.addComponent(dlvPoint);
		dlvPoint.setSizeFull();
		
		return checkout;
	}
	
	/**
	 * Completes the purchase
	 * @param localAuth where is stored the user; updated after the purchase
	 */
	private void goConfirmPurchase(Authentication localAuth) {
		
		User user = localAuth.getUser();
		
		MetodoDiConsegna shippingMethodToEnum;
		
		switch(shippingMethod.getValue()) {
			case "Corriere":
				shippingMethodToEnum = MetodoDiConsegna.CORRIERE;
				break;
			case "Rapido":
				shippingMethodToEnum = MetodoDiConsegna.RAPIDO;
				break;
			case "In giornata":
				shippingMethodToEnum = MetodoDiConsegna.INGIORNATA;
				break;
			default:
				shippingMethodToEnum = MetodoDiConsegna.CORRIERE;
				break;
		}
		
		String IPClient = ((MyUI) UI.getCurrent()).getClientIpAddr(VaadinService.getCurrentRequest());
		
		try {
			user.confirmPurchase(paymtMethod.getValue(), shippingMethodToEnum, dlvPoint.getValue(), IPClient);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		localAuth.setUser(user);
		UI.getCurrent().getSession().setAttribute("AUTH", localAuth);
		
	}
	
}

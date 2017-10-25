package view;

import com.vaadin.navigator.View;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import controller.Authentication;
import model.ProductInCart;
import model.User;

@SuppressWarnings("serial")
public class checkOutView extends VerticalLayout implements View {

	public static final String NAME = "checkout";
	
	public checkOutView() {
		
		Authentication localAuth = (Authentication) UI.getCurrent().getSession().getAttribute("AUTH");
		User user = localAuth.getUser();
		
		HorizontalLayout riepilogo = new HorizontalLayout();
		
		FormLayout checkout = new FormLayout();
		
		Grid<ProductInCart> cart = new Grid<>();
		
		cart.setItems(user.getShoppingCart().getArticoliInCarrello());
		for(ProductInCart productInCart : user.getShoppingCart().getArticoliInCarrello()) {
			cart.addColumn(productInCart.getProduct().getNome());
			cart.addColumn(String.valueOf(productInCart.getNumeroProdotto()));
		}
		
		riepilogo.addComponent(cart);
		riepilogo.addComponent(checkout);
		
		addComponent(riepilogo);
		
	}

}

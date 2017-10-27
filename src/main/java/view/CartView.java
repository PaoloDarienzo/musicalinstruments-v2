package view;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

import controller.Authentication;
import model.ProductInCart;
import model.User;

/**
 * This class creates the view of the cart of the user.
 * @author Paolo D'Arienzo
 * @version 2.0
 *
 */
@SuppressWarnings("serial")
public class CartView extends VerticalLayout implements View {
	
	public static final String NAME = "cart";
	
	/**
	 * Constructor that creates the cart of the user.
	 */
	public CartView(){
		
		Authentication localAuth = (Authentication) UI.getCurrent().getSession().getAttribute("AUTH");
		User user = localAuth.getUser();
		
		Panel cart = new Panel("<b>Cart</b>");
		cart.setCaptionAsHtml(true);
		cart.setIcon(VaadinIcons.CART_O);
		
		cart.setSizeFull();
		
		if(user.getShoppingCart().getArticoliInCarrello().isEmpty()) { //Cart is empty
			VerticalLayout cartEmpty = new VerticalLayout();
			cartEmpty.setSizeFull();
			cartEmpty.addComponent(new Label("The cart is empty."));
			Button goShoppingBtn = new Button("Go shopping!");
			goShoppingBtn.setStyleName(ValoTheme.BUTTON_FRIENDLY);

			goShoppingBtn.addClickListener(new ClickListener() {
	            @Override
	            public void buttonClick(final ClickEvent event) {
	            	UI.getCurrent().getNavigator().navigateTo(HomeView.NAME);
	            }
	        });
			
			cartEmpty.addComponent(goShoppingBtn);
			
			cart.setContent(cartEmpty);
		}
		else { //Cart is not empty
			
			VerticalLayout cartRecap = new VerticalLayout();
			cartRecap.setSizeFull();
			
			int cols = 6;
			int rows = 5 + user.getShoppingCart().getArticoliInCarrello().size();
			
			GridLayout gridCart = new GridLayout(cols, rows);
			gridCart.setMargin(true);
			gridCart.setWidth("100%");
			
			Label captionLabel = new Label("Products in the cart");
			captionLabel.setStyleName("gridlayout-slot");
			
			gridCart.addComponent(captionLabel, 0, 0, cols - 1, 0);
			
			gridCart.addComponent(new Label("Product name"), 0, 1);
			gridCart.addComponent(new Label("Unitary price"), 1, 1);
			gridCart.addComponent(new Label("Number of items"), 2, 1, 3, 1);
			gridCart.addComponent(new Label("Discount rate"), 4, 1);
			gridCart.addComponent(new Label("Total price"), 5, 1);
			
			for(ProductInCart productInCart : user.getShoppingCart().getArticoliInCarrello()) {
				
				java.util.Formatter unitaryPrice = new java.util.Formatter();
				java.util.Formatter productsPrice = new java.util.Formatter();
				
				TextArea nameText = new TextArea();
				nameText.setWidth(100, Unit.PERCENTAGE);
				nameText.setValue(productInCart.getProduct().getNome());
				nameText.setReadOnly(true);
				gridCart.addComponent(nameText);
				
				gridCart.addComponent(new Label(unitaryPrice.format("%.2f", productInCart.getProduct().getPrezzo()).toString() + " €"));
				gridCart.addComponent(new Label(String.valueOf(productInCart.getNumeroProdotto())));
				
				HorizontalLayout btns = new HorizontalLayout();
				btns.setMargin(true);
				Button plus = new Button();
				plus.addClickListener(e -> {
					user.getShoppingCart().incrementInCart(productInCart.getProduct());
					localAuth.setUser(user);
					UI.getCurrent().getSession().setAttribute("AUTH", localAuth);
					UI.getCurrent().getNavigator().navigateTo(CartView.NAME);
				});
				plus.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
				plus.addStyleName(ValoTheme.BUTTON_FRIENDLY);
				plus.addStyleName(ValoTheme.BUTTON_TINY);
				plus.setIcon(VaadinIcons.PLUS);
				Button minus = new Button();
				minus.addClickListener(e -> {
					user.getShoppingCart().decrementInCart(productInCart.getProduct());
					localAuth.setUser(user);
					UI.getCurrent().getSession().setAttribute("AUTH", localAuth);
					UI.getCurrent().getNavigator().navigateTo(CartView.NAME);
				});
				minus.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
				minus.addStyleName(ValoTheme.BUTTON_DANGER);
				minus.addStyleName(ValoTheme.BUTTON_TINY);
				minus.setIcon(VaadinIcons.MINUS);
				Button remove = new Button();
				remove.addClickListener(e -> {
					user.getShoppingCart().removeFromCart(productInCart.getProduct());
					localAuth.setUser(user);
					UI.getCurrent().getSession().setAttribute("AUTH", localAuth);
					UI.getCurrent().getNavigator().navigateTo(CartView.NAME);
				});
				remove.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
				remove.addStyleName(ValoTheme.BUTTON_PRIMARY);
				remove.addStyleName(ValoTheme.BUTTON_TINY);
				remove.setIcon(VaadinIcons.ERASER);
				btns.addComponents(plus, minus, remove);
				btns.setComponentAlignment(plus, Alignment.MIDDLE_LEFT);
				btns.setComponentAlignment(minus, Alignment.MIDDLE_CENTER);
				btns.setComponentAlignment(remove, Alignment.MIDDLE_RIGHT);
				
				gridCart.addComponent(btns);
				
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
			gridCart.addComponent(new Label(totalPrice.format("%.2f", user.getShoppingCart().getTotalPrice()).toString() +  " €"), 
					3, rows - 1, cols - 2, rows - 1);
			
			HorizontalLayout checkoutBtnLayout = new HorizontalLayout();
			checkoutBtnLayout.setMargin(true);
			
			Button checkOutBtn = new Button("Check out!");
			checkOutBtn.setStyleName(ValoTheme.BUTTON_FRIENDLY);
			checkOutBtn.addStyleName(ValoTheme.BUTTON_SMALL);
			checkOutBtn.addClickListener(e -> {
				UI.getCurrent().getNavigator().addView(checkOutView.NAME, checkOutView.class);
				UI.getCurrent().getNavigator().navigateTo(checkOutView.NAME);
			});
			checkoutBtnLayout.addComponent(checkOutBtn);
			
			gridCart.addComponent(checkoutBtnLayout, cols - 1, rows - 1);
			
			cartRecap.addComponent(gridCart);
			
			cart.setContent(cartRecap);
			
			totalPrice.close();
			
		}
				
		addComponent(cart);
		
	}

}

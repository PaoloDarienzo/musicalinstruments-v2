package view;

import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

import controller.Authentication;
import model.DeliveryPoint;
import model.Payment;
import model.User;

/**
 * This class creates the lists of payment methods and delivery points of the logged user.
 * @author Paolo D'Arienzo
 * @version 2.0
 *
 */
@SuppressWarnings("serial")
public class UserListsLayout extends VerticalLayout {
	
	private User user;

	/**
	 * Constructor of the layout of the lists of the user (payment methods and delivery points).
	 */
	UserListsLayout(){
		
		Authentication localAuth = (Authentication) UI.getCurrent().getSession().getAttribute("AUTH");
		this.user = localAuth.getUser();
		
		setSizeUndefined();
		
		paymentMethods();
		deliveryPoints();
		
	}
	
	/**
	 * Creates the list of the payment methods of the user
	 */
	private void paymentMethods(){
		
		Button addBtn = new Button("Add payment method");
		addBtn.setStyleName(ValoTheme.BUTTON_PRIMARY);
		
		Grid<Payment> payments = new Grid<>("Payment methods");
		
		payments.setItems(user.getPayment());
		payments.addColumn(Payment::getNomeMetodo).setCaption("Method name");
		payments.addColumn(Payment::getCredenzialiCensurate).setCaption("Credentials");
		
		// Render a button that deletes the data row (item)
		payments.addColumn(pmt -> "Delete",
			new ButtonRenderer<Payment>(clickEvent -> {
				user.removePayment((Payment) clickEvent.getItem());
				payments.setItems(user.getPayment());
				    }));
		
		payments.setHeightByRows(5);
		
		addComponents(payments, addBtn);
		
		addBtn.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
            	
                Window subWindow = new AddPaymentWindow("Add payment");

                // Center it in the browser window
                subWindow.center();

                // Open it in the UI
                UI.getCurrent().addWindow(subWindow);
                
            }
        });
		
	}
	
	/**
	 * Creates the list of the delivery points of the user
	 */
	private void deliveryPoints(){
		
		Button addBtn = new Button("Add delivery point");
		addBtn.setStyleName(ValoTheme.BUTTON_PRIMARY);
		
		Grid<DeliveryPoint> deliveryPoints = new Grid<>("Delivery points");
		
		deliveryPoints.setItems(user.getDeliveryPoint());
		deliveryPoints.addColumn(DeliveryPoint::getCitta).setCaption("City");
		deliveryPoints.addColumn(DeliveryPoint::getVia).setCaption("Address");
		deliveryPoints.addColumn(DeliveryPoint::getCivico).setCaption("Street number");
		deliveryPoints.addColumn(DeliveryPoint::getCAP).setCaption("Postal code");
		
		// Render a button that deletes the data row (item)
		deliveryPoints.addColumn(delPnt -> "Delete",
		      new ButtonRenderer<DeliveryPoint>(clickEvent -> {
		    	  user.removeDeliveryPoint((DeliveryPoint) clickEvent.getItem());
		    	  deliveryPoints.setItems(user.getDeliveryPoint());
		    }));
		
		deliveryPoints.setHeightByRows(5);
		
		addComponents(deliveryPoints, addBtn);
		
		addBtn.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
            	
                Window subWindow = new AddDlvPointWindow("Add delivery point");

                // Center it in the browser window
                subWindow.center();

                // Open it in the UI
                UI.getCurrent().addWindow(subWindow);
                
            }
        });
		
	}
	
}

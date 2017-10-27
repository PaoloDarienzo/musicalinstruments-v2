package view;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import controller.Authentication;
import model.DeliveryPoint;
import model.User;

/**
 * This class creates the pop-up window that manages the adding of a delivery point.
 * @author Paolo D'Arienzo
 * @version 2.0
 *
 */
@SuppressWarnings("serial")
public class addDlvPointWindow extends Window {

	/**
	 * Extends the constructor of the window, adding the form used for adding the delivery point.
	 * @param caption is the caption string to pass to the constructor of the Window
	 */
	addDlvPointWindow(String caption){
		
		super(caption);
		
		FormLayout addDlvPointForm = new FormLayout();
		addDlvPointForm.setMargin(true);
		
		TextField city = new TextField("City");
		city.setPlaceholder("City");
		TextField address = new TextField("Address");
		address.setPlaceholder("Address");
		TextField streetNum = new TextField("Street number");
		streetNum.setPlaceholder("Street number");
		TextField CAP = new TextField("Postal code");
		CAP.setPlaceholder("Postal code");
		
		HorizontalLayout btns = new HorizontalLayout();
		Button cancelBtn = new Button("Cancel");
		cancelBtn.setStyleName(ValoTheme.BUTTON_DANGER);
		cancelBtn.setIcon(VaadinIcons.ARROW_BACKWARD);
		cancelBtn.addClickListener(e -> close());
		Button confirmBtn = new Button("Confirm");
		confirmBtn.setStyleName(ValoTheme.BUTTON_PRIMARY);
		confirmBtn.setIcon(VaadinIcons.PLUS);
		btns.addComponents(cancelBtn, confirmBtn);
		
		addDlvPointForm.addComponents(city, address, streetNum, CAP, btns);
		
		confirmBtn.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
            	            	
            	if(	city.getValue().isEmpty() ||
            		address.getValue().isEmpty() ||
            		streetNum.getValue().isEmpty() ||
            		CAP.getValue().isEmpty()
            	) {
            		Notification notification = new Notification("All fields are required", Notification.Type.WARNING_MESSAGE);
                    notification.show(Page.getCurrent());
                	return;
                }
            	else {
            		Authentication localAuth = (Authentication) UI.getCurrent().getSession().getAttribute("AUTH");
            		User user = localAuth.getUser();
            		DeliveryPoint newDlvPnt = new DeliveryPoint(user.getMail(), city.getValue(), 
            									address.getValue(), streetNum.getValue(), CAP.getValue());
            		user.addDeliveryPoint(newDlvPnt);
            		localAuth.setUser(user);
            		UI.getCurrent().getSession().setAttribute("AUTH", localAuth);
            		close();
            		UI.getCurrent().getPage().reload();
            	}
            	
            }
        });
		
		setContent(addDlvPointForm);
	}
	
}

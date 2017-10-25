package view;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

import controller.Authentication;
import model.Payment;
import model.User;

@SuppressWarnings("serial")
public class addPaymentWindow extends Window {
	
	addPaymentWindow(String caption){
		
		super(caption);
		
		FormLayout addPmtForm = new FormLayout();
		addPmtForm.setSizeFull();
		
		TextField nomeMetodo = new TextField("Method name");
		nomeMetodo.setPlaceholder("Method name");
		
		TextField credenzialiMetodo = new TextField("Credentials");
		credenzialiMetodo.setPlaceholder("Credentials");
		
		HorizontalLayout btns = new HorizontalLayout();
		Button cancelBtn = new Button("Cancel");
		cancelBtn.setStyleName(ValoTheme.BUTTON_PRIMARY);
		cancelBtn.setIcon(VaadinIcons.ARROW_BACKWARD);
		cancelBtn.addClickListener(e -> close());
		Button confirmBtn = new Button("Confirm");
		confirmBtn.setStyleName(ValoTheme.BUTTON_PRIMARY);
		confirmBtn.setIcon(VaadinIcons.PLUS);
		btns.addComponents(cancelBtn, confirmBtn);
		
		addPmtForm.addComponents(nomeMetodo, credenzialiMetodo, btns);
		
		confirmBtn.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
            	            	
            	if(nomeMetodo.getValue().isEmpty() ||
            	credenzialiMetodo.getValue().isEmpty()) {
                	Notification.show("All fields are required", Notification.Type.WARNING_MESSAGE);
                	return;
                }
            	else {
            		Authentication localAuth = (Authentication) UI.getCurrent().getSession().getAttribute("AUTH");
            		User user = localAuth.getUser();
            		Payment newPaymt = new Payment(user.getMail(), nomeMetodo.getValue(), credenzialiMetodo.getValue());
            		user.addPayment(newPaymt);
            		localAuth.setUser(user);
            		UI.getCurrent().getSession().setAttribute("AUTH", localAuth);
            		close();
            		UI.getCurrent().getPage().reload();
            	}
            	
            }
        });
		
		setContent(addPmtForm);
		
	}
	
	
}

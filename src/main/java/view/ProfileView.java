package view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import controller.Authentication;
import model.User;

@SuppressWarnings("serial")
public class ProfileView extends HorizontalLayout implements View {
	
	public static final String NAME = "profileView";
	
	public ProfileView(){
		
		Authentication localAuth = (Authentication) UI.getCurrent().getSession().getAttribute("AUTH");
		User user = localAuth.getUser();
		
		setSizeUndefined();
		
		VerticalLayout leftLayout = new userListsLayout(user);
		VerticalLayout rightLayout = new userInfoLayout(user);
		
		addComponents(leftLayout, rightLayout);
		
		setComponentAlignment(leftLayout, Alignment.MIDDLE_LEFT);
		setComponentAlignment(rightLayout, Alignment.MIDDLE_RIGHT);
				
	}

}

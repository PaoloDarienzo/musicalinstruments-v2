package view;

import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import controller.Authentication;
import model.User;

/**
 * This class creates the layout containing the informations of the logged user.
 * @author Paolo D'Arienzo
 * @version 2.0
 *
 */
@SuppressWarnings("serial")
public class UserInfoLayout extends VerticalLayout {
	
	/**
	 * Constructor of the user's infos layout
	 */
	UserInfoLayout(){
		
		Authentication localAuth = (Authentication) UI.getCurrent().getSession().getAttribute("AUTH");
		User user = localAuth.getUser();
		
		setSizeUndefined();
		
		Label labelTitle = new Label("My profile");
		labelTitle.setStyleName("userProfile");
		
		addComponent(labelTitle);
		
		VerticalLayout userInfo = new VerticalLayout();
		userInfo.addStyleName("layout-with-border");
		
		Label username = new Label("Username: " + user.getNomeUtente());
		Label name = new Label("Name: " + user.getNome());
		Label surname = new Label("Surname: " + user.getCognome());
		Label city = new Label("City: " + user.getCittaDiResidenza());
		Label cf = new Label("Fiscal code: " + user.getCF());
		Label mail = new Label("E-mail: " + user.getMail());
		Label telNum = new Label("Telephone number: " + user.getNumeroTelefono());
		Label celNum = new Label("Cellphone number: " + user.getNumeroCellulare());
		Label userType = new Label("Type of user: " + user.getTipo().toString());
		
		userInfo.addComponents(username, name, surname, city, cf, mail, telNum, celNum, userType);
		
		addComponent(userInfo);
		
	}

}

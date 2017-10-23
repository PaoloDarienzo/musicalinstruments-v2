package view;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import model.User;

@SuppressWarnings("serial")
public class userInfoLayout extends VerticalLayout {
	
	userInfoLayout(User user){
		
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

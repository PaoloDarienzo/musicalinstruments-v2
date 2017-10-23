package view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import controller.Authentication;
import model.User;

@SuppressWarnings("serial")
public class HomeView extends VerticalLayout implements View {

	public static final String NAME = "homepageView";
	
	public HomeView(){
		
		Authentication localAuth = (Authentication) UI.getCurrent().getSession().getAttribute("AUTH");
		User user = localAuth.getUser();
		String name = user.getNomeUtente();
		
		Panel panel = new Panel("homepageView: hi " + name);
		panel.setSizeUndefined();
		addComponent(panel);
		
		/*
		//TODO
		if(user.getNumeroCellulare().equals("N/A")) {
			user.setNumeroCellulare("0");
		}
		
		int foo = Integer.parseInt(user.getNumeroCellulare());
		
		if(user.getNomeUtente().equals("paolodarienzo")) {
			foo = foo + 15;
		}
		else {
			foo++;
		}
		
		String newNum = Integer.toString(foo);
		
		user.setNumeroCellulare(newNum);
		
		localAuth.setUser(user);
		
		VaadinSession.getCurrent().setAttribute("AUTH", localAuth);
		
		String intero = localAuth.getUser().getNumeroCellulare();
		
		Label num = new Label(intero);
		
		addComponent(num);
		*/
		
	}

}

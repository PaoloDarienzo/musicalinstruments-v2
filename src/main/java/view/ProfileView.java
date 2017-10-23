package view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class ProfileView extends VerticalLayout implements View {
	
	public static final String NAME = "profileView";
	
	public ProfileView(){
		
		Panel panel = new Panel("profileView");
		panel.setSizeUndefined();
		addComponent(panel);
		
	}

}

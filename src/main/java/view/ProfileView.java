package view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class ProfileView extends HorizontalLayout implements View {
	
	public static final String NAME = "profileView";
	
	public ProfileView(){
				
		setSizeUndefined();
		
		VerticalLayout leftLayout = new userListsLayout();
		VerticalLayout rightLayout = new userInfoLayout();
		
		addComponents(leftLayout, rightLayout);
		
		setComponentAlignment(leftLayout, Alignment.MIDDLE_LEFT);
		setComponentAlignment(rightLayout, Alignment.MIDDLE_RIGHT);
				
	}

}

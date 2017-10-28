package view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * This class creates the view for the profile of the user.
 * @author Paolo D'Arienzo
 * @version 2.0
 *
 */
@SuppressWarnings("serial")
public class ProfileView extends HorizontalLayout implements View {
	
	public static final String NAME = "user_profile";
	
	/**
	 * Constructor of the profile view.
	 */
	public ProfileView(){
				
		setSizeUndefined();
		
		VerticalLayout leftLayout = new UserListsLayout();
		VerticalLayout rightLayout = new UserInfoLayout();
		
		addComponents(leftLayout, rightLayout);
		
		setComponentAlignment(leftLayout, Alignment.MIDDLE_LEFT);
		setComponentAlignment(rightLayout, Alignment.MIDDLE_RIGHT);
				
	}

}

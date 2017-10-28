package view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * This class creates the view that confirms the purchase.
 * @author Paolo D'Arienzo
 * @version 2.0
 *
 */
@SuppressWarnings("serial")
public class PurchaseConfirmedView extends VerticalLayout implements View {
	
	public static final String NAME = "purchase_confirmed";
	
	/**
	 * Constructor of the purchase's confirmation view.
	 */
	public PurchaseConfirmedView(){
		
		Label success = new Label("Success! Order confirmed!");
		success.addStyleName(ValoTheme.LABEL_H1);
		
		addComponent(success);
		setComponentAlignment(success, Alignment.MIDDLE_CENTER);
		
		Button goTo = new Button("Go to homepage");
		goTo.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		goTo.addStyleName(ValoTheme.BUTTON_LARGE);
		goTo.addClickListener(e -> {
			UI.getCurrent().getNavigator().navigateTo(HomeView.NAME);
		});
		
		addComponent(goTo);
		setComponentAlignment(goTo, Alignment.MIDDLE_CENTER);
		
	}

}

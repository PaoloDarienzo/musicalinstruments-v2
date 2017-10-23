package view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class CartView extends VerticalLayout implements View {
	
	public static final String NAME = "cartView";
	
	public CartView(){
		
		Panel panel = new Panel("cartView");
		panel.setSizeUndefined();
		addComponent(panel);
		
	}

}

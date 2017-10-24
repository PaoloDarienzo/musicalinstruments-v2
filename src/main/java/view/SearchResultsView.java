package view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class SearchResultsView extends VerticalLayout implements View {
	
	public static final String NAME = "resultsView";
	
	SearchResultsView(String search, String brandToSearch, String instrumentTypeToSearch,
			String usedStatusToSearch, String productTypeToSearch){
		
		addComponent(new Label("Uè"));
		
		System.out.println("search: " + search);
        System.out.println("brand: " + brandToSearch);
        System.out.println("instrument type: " + instrumentTypeToSearch);
        System.out.println("used status: " + usedStatusToSearch);
        System.out.println("product type: " + productTypeToSearch);
		
	}

}

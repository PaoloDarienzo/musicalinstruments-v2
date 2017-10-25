package view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class HomeView extends VerticalLayout implements View {

	public static final String NAME = "homepage";
	
	private TextField searchField;
    private ComboBox<String> brandToSearch;
    private ComboBox<String> instrumentTypeToSearch;
    private ComboBox<String> usedStatusToSearch;
    private ComboBox<String> productTypeToSearch;
	
	public HomeView(){
		
		/*
		Authentication localAuth = (Authentication) UI.getCurrent().getSession().getAttribute("AUTH");
		User user = localAuth.getUser();
		*/
		
		VerticalLayout searchPanelLayout = new VerticalLayout();
    	        
        Label labelTitle = new Label("Search products");
        labelTitle.setStyleName(ValoTheme.LABEL_BOLD);
        labelTitle.addStyleName(ValoTheme.LABEL_COLORED);
        labelTitle.addStyleName(ValoTheme.LABEL_HUGE);
        
        searchPanelLayout.addComponent(labelTitle);
        searchPanelLayout.setComponentAlignment(labelTitle, Alignment.TOP_LEFT);
        
        FormLayout searchLayout = new FormLayout();
        HorizontalLayout filterLayout = new HorizontalLayout();
        
        //Creating lists of elements hosted inside the comboBoxes
        List<String> brandsQuery = dao.QueriesDAO.getBrands();
        brandsQuery.add(0, "All");
        
        List<String> instrTypeQuery = dao.QueriesDAO.getInstrumentType();
        instrTypeQuery.add(0, "All");
        
        List<String> status = new ArrayList<String>();
        status.add("All"); status.add("Used"); status.add("Not used");
        
        List<String> productType = new ArrayList<String>();
        productType.add("All"); productType.add("Classic product"); 
        productType.add("Professional product"); productType.add("Scholastic product");

       // Create UI components
        searchField = new TextField("Search terms");
        searchField.setPlaceholder("Insert keywords...");
        brandToSearch = new ComboBox<>("Brand to search", brandsQuery);
        brandToSearch.setPlaceholder("All");
        instrumentTypeToSearch = new ComboBox<>("Instrument type to search", instrTypeQuery);
        instrumentTypeToSearch.setPlaceholder("All");
        usedStatusToSearch = new ComboBox<>("Used status to search", status);
        usedStatusToSearch.setPlaceholder("All");
        productTypeToSearch = new ComboBox<>("Product type to search", productType);
        productTypeToSearch.setPlaceholder("All");

        /* Add all the created components to the corresponding layout */
        searchLayout.addComponent(searchField);
        searchField.setSizeFull();
        
        filterLayout.addComponent(brandToSearch);
        filterLayout.addComponent(instrumentTypeToSearch);
        filterLayout.addComponent(usedStatusToSearch);
        filterLayout.addComponent(productTypeToSearch);
        
        Button goSearchBtn = new Button("Search");
        goSearchBtn.setClickShortcut(KeyCode.ENTER);
        goSearchBtn.setStyleName(ValoTheme.BUTTON_LARGE);
        goSearchBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        goSearchBtn.setIcon(VaadinIcons.SEARCH);
        
        goSearchBtn.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
            	UI.getCurrent().getNavigator().addView(SearchResultsView.NAME, 
                		new SearchResultsView(	searchField.getValue(), brandToSearch.getValue(), instrumentTypeToSearch.getValue(),
                								usedStatusToSearch.getValue(), productTypeToSearch.getValue()));
            	UI.getCurrent().getNavigator().navigateTo(SearchResultsView.NAME);
            }
        });
        
        searchPanelLayout.addComponents(searchLayout, filterLayout, goSearchBtn);
        searchLayout.setSizeFull();
        filterLayout.setSizeFull();
        goSearchBtn.setSizeUndefined();
        searchPanelLayout.setComponentAlignment(searchLayout, Alignment.MIDDLE_CENTER);
        searchPanelLayout.setComponentAlignment(filterLayout, Alignment.MIDDLE_CENTER);
        searchPanelLayout.setComponentAlignment(goSearchBtn, Alignment.MIDDLE_CENTER);
        
        addComponent(searchPanelLayout);
        
	}

}

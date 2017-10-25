package view;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import model.Product;

@SuppressWarnings("serial")
public class SearchResultsView extends VerticalLayout implements View {
	
	public static final String NAME = "results";
	
	SearchResultsView(String search, String brandToSearch, String instrumentTypeToSearch,
			String usedStatusToSearch, String productTypeToSearch){
        
        Label labelTitle = new Label("Search results");
        labelTitle.setStyleName(ValoTheme.LABEL_BOLD);
        labelTitle.addStyleName(ValoTheme.LABEL_COLORED);
        labelTitle.addStyleName(ValoTheme.LABEL_LARGE);
        
        addComponent(labelTitle);
        setComponentAlignment(labelTitle, Alignment.TOP_LEFT);
        
        if(brandToSearch == null) {
        	brandToSearch = "All";
        }
        
        if(instrumentTypeToSearch == null) {
        	instrumentTypeToSearch = "All";
        }
        
        
        if(productTypeToSearch == null) {
        	productTypeToSearch = "All";
        }
        
        if(usedStatusToSearch == null) {
        	usedStatusToSearch = "0";
        }
        else {
        	switch(usedStatusToSearch) {
        	case "All":
        		usedStatusToSearch = "0";
        		break;
        	case "Used":
        		usedStatusToSearch = "true";
        		break;
        	case "Not used":
        		usedStatusToSearch = "false";
        		break;
        	default:
        		usedStatusToSearch = "0";
        		break;
        	}
        }
        
        Boolean searchStringIsVoid = true;
        if(!search.isEmpty()) {
        	searchStringIsVoid = false;
        }
        
        List<Product> queryResults = new ArrayList<Product>();
        
        try {
        	queryResults = dao.QueriesDAO.getProducts(searchStringIsVoid, search, brandToSearch, instrumentTypeToSearch, usedStatusToSearch, productTypeToSearch);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        for(Product product : queryResults) {
        	
        	GridLayout newGrid = new GridLayout();
        	
        	Panel productInfo = new Panel(product.getNome());
        	
        	VerticalLayout productInfoLayout = new VerticalLayout();
        	productInfoLayout.addStyleName("layout-with-border");
        	
        	Label description = new Label(product.getDescrizione());
        	Label price = new Label(String.valueOf(product.getPrezzo()));
        	
        	productInfoLayout.addComponents(description, price);
        	
        	productInfo.setContent(productInfoLayout);
        	
        	addComponent(productInfo);
        }
		
	}

}

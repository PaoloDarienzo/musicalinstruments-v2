package view;

import com.vaadin.navigator.View;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import controller.HomeUI;

//import controller.DashboardNavigator;

@SuppressWarnings("serial")
public class MainView extends HorizontalLayout implements View{

    public MainView() {
        setSizeFull();
        addStyleName("mainview");
        setSpacing(false);

        //addComponent(new DashboardMenu());
        addComponent(new Label("Inside and logged; welcome, " + HomeUI.AUTH.getUser().getNomeUtente() + "!"));

        ComponentContainer content = new CssLayout();
        content.addStyleName("view-content");
        content.setSizeFull();
        addComponent(content);
        setExpandRatio(content, 1.0f);

        //new DashboardNavigator(content);
        
    }
}
package view;

import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

import controller.Authentication;
import controller.VerticalMenu;

@SuppressWarnings("serial")
public class MainView extends VerticalLayout implements View{

    public MainView() {
    	
    	Authentication localAuth = (Authentication) UI.getCurrent().getSession().getAttribute("AUTH");
    	
        addStyleName("mainview");
        
        //MenuBar header = new MenuBar();
        //VerticalLayout contentLayout = new VerticalLayout();
        
        HorizontalSplitPanel panel = new HorizontalSplitPanel();
        
        VerticalMenu verticalMenu = new VerticalMenu();
        
        panel.setFirstComponent(verticalMenu);
        panel.setSecondComponent(new Label("[ here goes the pretty body ]"));
        
        panel.setSplitPosition(25, Unit.PERCENTAGE);
        
        addComponent(panel);
        
        //addComponents(header, contentLayout);
        
        //Navigator navigator = new Navigator(UI.getCurrent(), contentLayout);
        
        Notification notification = new Notification("Welcome, " +localAuth.getUser().getNomeUtente(), Type.TRAY_NOTIFICATION);
        notification.show(Page.getCurrent());

        /*
        addComponent(new Label("Inside and logged; welcome, " + MyUI.AUTH.getUser().getNomeUtente() + "!"));

        ComponentContainer content = new CssLayout();
        content.addStyleName("view-content");
        content.setSizeFull();
        addComponent(content);
        setExpandRatio(content, 1.0f);
        */
        
    }

}
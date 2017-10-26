package view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

import controller.Authentication;

@SuppressWarnings("serial")
public class MainView extends VerticalLayout implements View{

    public MainView() {
    	
    	Authentication localAuth = (Authentication) UI.getCurrent().getSession().getAttribute("AUTH");
    	
        addStyleName("mainview");
        
        HorizontalSplitPanel panel = new HorizontalSplitPanel();
        
        VerticalMenu verticalMenu = new VerticalMenu();
        
        VerticalLayout contentView = new VerticalLayout();
        
        new Navigator(UI.getCurrent(), contentView);
        
        UI.getCurrent().getNavigator().addView(HomeView.NAME, HomeView.class);
        UI.getCurrent().getNavigator().addView(ProfileView.NAME, ProfileView.class);
        UI.getCurrent().getNavigator().addView(CartView.NAME, CartView.class);
        
        panel.setFirstComponent(verticalMenu);
        panel.setSecondComponent(contentView);
        
        panel.setSplitPosition(22, Unit.PERCENTAGE);
        
        addComponent(panel);
        
        Notification notification = new Notification("Welcome, " + localAuth.getUser().getNomeUtente(), Type.TRAY_NOTIFICATION);
        notification.show(Page.getCurrent());
        
        UI.getCurrent().getNavigator().navigateTo(HomeView.NAME);
        
    }

}
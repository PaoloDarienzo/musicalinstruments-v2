package view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import controller.MyUI;
import controller.VerticalMenu;

@SuppressWarnings("serial")
public class MainView extends VerticalLayout implements View{

    public MainView() {
    	
        addStyleName("mainview");
        
        //MenuBar header = new MenuBar();
        VerticalLayout contentLayout = new VerticalLayout();
        
        HorizontalSplitPanel panel = new HorizontalSplitPanel();
        
        VerticalMenu verticalMenu = new VerticalMenu();
        
        panel.setFirstComponent(verticalMenu);
        panel.setSecondComponent(new Label("[ here goes the pretty body ]"));
        
        addComponent(panel);
        
        //addComponents(header, contentLayout);
        
        //Navigator navigator = new Navigator(UI.getCurrent(), contentLayout);
        
        Notification notification = new Notification("Welcome", Type.TRAY_NOTIFICATION);
        notification.show(Page.getCurrent());

        /*
        addComponent(new Label("Inside and logged; welcome, " + MyUI.AUTH.getUser().getNomeUtente() + "!"));
        Button logoutBtn = new Button("Logout");
        addComponent(logoutBtn);
        
        logoutBtn.addClickListener(e -> doLogout());

        ComponentContainer content = new CssLayout();
        content.addStyleName("view-content");
        content.setSizeFull();
        addComponent(content);
        setExpandRatio(content, 1.0f);
        */
        
    }

	private void doLogout() {
		
		Notification notification = new Notification("Redirect to login page...");
        notification.setStyleName("tray dark small closable login-help");
        notification.setPosition(Position.MIDDLE_CENTER);
        notification.setDelayMsec(20000);
        notification.show(Page.getCurrent());
        
		((MyUI) UI.getCurrent()).userLoggedOut();
	}

}
package controller;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import view.CartView;
import view.HomeView;
import view.ProfileView;

@SuppressWarnings("serial")
public class VerticalMenu extends VerticalLayout{
	
    //initialize root & other components
    {
        initRoot();
        initComponents();
    }
    
    private void initRoot(){
        setStyleName("vertical-menu");
    }
    
    private void initComponents(){
    	initLogo();
        initButtons();
    }
    
    private void initLogo() {
    	
    	HorizontalLayout logo = new HorizontalLayout();
    	logo.setSizeUndefined();
    	Label nameLogo = new Label("ZUMZUM.IT");
        nameLogo.setStyleName("v-label-LOGO");
        nameLogo.addStyleName(ValoTheme.LABEL_COLORED);
        Image image = new Image("", new ThemeResource("img/duck-walking.gif"));
        image.setWidth(50, Unit.PIXELS);
        image.setHeight(50, Unit.PIXELS);
        logo.addComponents(image, nameLogo);
        addComponent(logo);
        logo.setComponentAlignment(nameLogo, Alignment.BOTTOM_CENTER);
        logo.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
        
    }
    
    private void initButtons(){
    	
    	Button homeBtn = new Button("Home");
    	homeBtn.setIcon(VaadinIcons.HOME);
    	homeBtn.addClickListener(e -> goToHomeView());
    	
    	Button userProfileBtn = new Button("Profile");
    	userProfileBtn.setIcon(VaadinIcons.USER);
    	userProfileBtn.addClickListener(e -> goToProfileView());
    	
    	Button cartBtn = new Button("Cart");
    	cartBtn.setIcon(VaadinIcons.CART);
    	cartBtn.addClickListener(e -> goToCartView());
        
        Button logoutBtn = new Button("Logout");
        logoutBtn.setIcon(VaadinIcons.EXIT);
        logoutBtn.addClickListener(e -> doLogout());
        
        addComponents(homeBtn, userProfileBtn, cartBtn, logoutBtn);
        
        
    }
    
    private void doLogout() {
		
		Notification notification = new Notification("Redirect to login page...");
        notification.setStyleName("tray dark small closable login-help");
        notification.setPosition(Position.MIDDLE_CENTER);
        notification.setDelayMsec(20000);
        notification.show(Page.getCurrent());
        
		((MyUI) UI.getCurrent()).userLoggedOut();
		
	}
    
    private void goToHomeView() {
    	Notification notification = new Notification("Redirect to goToHomeView page...");
    	notification.setDelayMsec(3000);
    	notification.show(Page.getCurrent());
    	
    	UI.getCurrent().getNavigator().navigateTo(HomeView.NAME);
		
	}
    
    private void goToProfileView() {
    	Notification notification = new Notification("Redirect to goToProfileView page...");
    	notification.setDelayMsec(3000);
    	notification.show(Page.getCurrent());
    	
    	UI.getCurrent().getNavigator().navigateTo(ProfileView.NAME);
    	
	}

	private void goToCartView() {
		Notification notification = new Notification("Redirect to goToCartView page...");
    	notification.setDelayMsec(3000);
    	notification.show(Page.getCurrent());
    	
    	UI.getCurrent().getNavigator().navigateTo(CartView.NAME);
    	
	}

}
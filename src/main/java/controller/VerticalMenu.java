package controller;

import java.util.ArrayList;
import java.util.List;

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

@SuppressWarnings("serial")
public class VerticalMenu extends VerticalLayout{
	
	private List<Button> buttons;
	
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
        
    	buttons = new ArrayList<Button>();
        
        buttons.add(new Button("First"));
        buttons.add(new Button("Second"));
        buttons.add(new Button("Third"));
        
        
        for(Button button:buttons){
            button.addClickListener((clickEvent)->{
                Notification.show("i'm "+button.getCaption()+" Button");
            });
            switch(button.getCaption()){
            case "First":{button.setIcon(VaadinIcons.USER);}break;
            case "Second":{button.setIcon(VaadinIcons.CLOUD);}break;
            case "Third":{button.setIcon(VaadinIcons.MAGIC);}break;
            
            }
            addComponent(button);
        }
        
        Button logoutBtn = new Button("Logout");
        logoutBtn.setIcon(VaadinIcons.EXIT);
        logoutBtn.addClickListener(e -> doLogout());
        
        addComponent(logoutBtn);
        
        
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
package controller;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import model.User;
import view.LoginView;
import view.MainView;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@SuppressWarnings("serial")
@Theme("mytheme")
@Title("zumzum.it")
public class MyUI extends UI {
	
	public static Authentication AUTH;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	
    	AUTH = Authentication.getInstance();
    	
    	setTheme("mytheme");
    	addStyleName(ValoTheme.UI_WITH_MENU);
        
        updateContent();
        
    }

    /**
     * Updates the correct content for this UI based on the current user status.
     * If the user is logged in with appropriate privileges, main view is shown.
     * Otherwise login view is shown.
     */
    public void updateContent() {
    	User user = (User) VaadinSession.getCurrent().getAttribute("user");
        if (user != null) {
            // Authenticated user
        	Page.getCurrent().setTitle("zumzum.it");
            setContent(new MainView());
            removeStyleName("loginview");
        } else {
        	Page.getCurrent().setTitle("zumzum.it - login");
            setContent(new LoginView());
            addStyleName("loginview");
        }
    }
    
    public void userLoggedOut() {
        // When the user logs out, current VaadinSession gets closed and the
        // page gets reloaded on the login screen. Do notice that this doesn't
        // invalidate the current HttpSession.
    	AUTH.doLogout();
        VaadinSession.getCurrent().close();
        Page.getCurrent().reload();
    }
    
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}

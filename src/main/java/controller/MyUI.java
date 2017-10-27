package controller;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.PreserveOnRefresh;
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
 * @author Paolo D'Arienzo
 * @version 2.0
 * 
 */
@SuppressWarnings("serial")
@Theme("mytheme")
@Title("zumzum.it")
@PreserveOnRefresh
public class MyUI extends UI {
	
	@Override
    protected void init(VaadinRequest vaadinRequest) {
		
		Authentication auth = new Authentication();
		VaadinSession.getCurrent().setAttribute("AUTH", auth);
    	
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
    	
    	Authentication localAuth = (Authentication) UI.getCurrent().getSession().getAttribute("AUTH");
    	User user = localAuth.getUser();
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
    
    /**
     * Do the logout of the user;
     * resets the user status in the session and reload the page, resulting in a redirect
     */
    public void userLoggedOut() {
        // When the user logs out, current VaadinSession gets closed and the
        // page gets reloaded on the login screen. Do notice that this doesn't
        // invalidate the current HttpSession.
    	Authentication localAuth = (Authentication) UI.getCurrent().getSession().getAttribute("AUTH");
    	localAuth.doLogout();
    	UI.getCurrent().getSession().setAttribute("AUTH", localAuth);
        VaadinSession.getCurrent().close();
        Page.getCurrent().reload();
    }
    
    /**
	 * Gets the IP address (IPv6) of the client
	 * @param request passed from client
	 * @return String containing the IP address
	 */
	public String getClientIpAddr(VaadinRequest request) {
        String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }
    
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
    
}

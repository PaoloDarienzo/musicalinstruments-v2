package view;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import controller.Authentication;

@SuppressWarnings("serial")
public class LoginView extends VerticalLayout{

    public LoginView() {
    	
        setSizeFull();
        setMargin(false);
        setSpacing(false);
        
        HorizontalLayout logo = new HorizontalLayout();
        Label nameLogo = new Label("ZUMZUM.IT");
        nameLogo.setStyleName("v-label-LOGO");
        nameLogo.addStyleName(ValoTheme.LABEL_COLORED);
        Image image = new Image("", new ThemeResource("img/duck-walking.gif"));
        image.setWidth(50, Unit.PIXELS);
        image.setHeight(50, Unit.PIXELS);
        logo.addComponents(image, nameLogo);
        logo.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
        logo.setComponentAlignment(nameLogo, Alignment.BOTTOM_CENTER);

        Component loginForm = buildLoginForm();
        addComponents(logo, loginForm);
        setExpandRatio(loginForm, 1);
        
        setComponentAlignment(logo, Alignment.MIDDLE_CENTER);        
        setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);

        Notification notification = new Notification(
                "Welcome to zumzum.it");
        notification.setHtmlContentAllowed(true);
        notification.setStyleName("tray dark small closable login-help");
        notification.setPosition(Position.BOTTOM_CENTER);
        notification.setDelayMsec(1500);
        notification.show(Page.getCurrent());
        
    }

    private Component buildLoginForm() {
        final VerticalLayout loginPanel = new VerticalLayout();
        loginPanel.setSizeUndefined();
        loginPanel.setMargin(false);
        Responsive.makeResponsive(loginPanel);
        loginPanel.addStyleName("login-panel");

        loginPanel.addComponent(buildLabels());
        loginPanel.addComponent(buildAuthentication());
        
        return loginPanel;
    }

    private Component buildAuthentication() {
    	
    	VerticalLayout authentication = new VerticalLayout();
    	
        HorizontalLayout fields = new HorizontalLayout();
        fields.addStyleName("fields");

        final TextField username = new TextField("Username");
        username.setPlaceholder("Username or e-mail");
        username.setIcon(VaadinIcons.USER);
        username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final PasswordField password = new PasswordField("Password");
        password.setPlaceholder("Password");
        password.setIcon(VaadinIcons.LOCK);
        password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final Button signin = new Button("Sign In");
        signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
        signin.setClickShortcut(KeyCode.ENTER);
        signin.focus();
        
        fields.addComponents(username, password, signin);
        fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);
        
        authentication.addComponent(fields);
        
        HorizontalLayout options = new HorizontalLayout();
        CheckBox checkBox = new CheckBox("Remember me", false);
        Button registerBtn = new Button("Not registered?");
        registerBtn.addStyleName(ValoTheme.BUTTON_LINK);
        options.addComponents(checkBox, registerBtn);
        options.setComponentAlignment(checkBox, Alignment.MIDDLE_LEFT);
        options.setExpandRatio(checkBox, 1);
        options.setComponentAlignment(registerBtn, Alignment.BOTTOM_RIGHT);
        
        authentication.addComponent(options);
        
        options.setSizeFull();

        signin.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
            	if(username.getValue().isEmpty()) {
            		Notification.show("Insert username or e-mail", Notification.Type.WARNING_MESSAGE);
            		return;
            	}
            	if(password.getValue().isEmpty()) {
            		Notification.show("Insert password", Notification.Type.WARNING_MESSAGE);
            		return;
            	}
            	
            	Authentication localAuth = (Authentication) UI.getCurrent().getSession().getAttribute("AUTH");
            	if(localAuth.authenticate(username.getValue(), password.getValue())) {
            		goToMainView();
            	}
            	else {
            		Notification.show("Invalid credentials", Notification.Type.ERROR_MESSAGE);
            	}
            }
        });
        
        registerBtn.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
            	goToRegisterView();
            }
        });
        
        return authentication;
    }

    private Component buildLabels() {
        CssLayout labels = new CssLayout();
        labels.addStyleName("labels");

        Label welcome = new Label("Welcome to");
        welcome.setSizeUndefined();
        welcome.addStyleName(ValoTheme.LABEL_H3);
        welcome.addStyleName(ValoTheme.LABEL_COLORED);
        labels.addComponent(welcome);

        Label title = new Label(": the best e-commerce of musical instruments!");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_LIGHT);
        labels.addComponent(title);
        return labels;
    }
    
    private void goToMainView() {
		Page.getCurrent().setTitle("zumzum.it");
    	UI.getCurrent().setContent(new MainView());
        removeStyleName("loginview");
	}
	
	private void goToRegisterView() {
		Page.getCurrent().setTitle("zumzum.it - register");
    	UI.getCurrent().setContent(new RegisterView());
        removeStyleName("loginview");
	}

}
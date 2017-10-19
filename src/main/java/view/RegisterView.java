package view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class RegisterView extends VerticalLayout {

	public RegisterView() {
		
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
        
        addComponent(logo);
        setComponentAlignment(logo, Alignment.TOP_CENTER);
        
        VerticalLayout mainContent = new VerticalLayout();
        
        FormLayout loginForm = new FormLayout();
        
        TextField mail = new TextField("E-mail");
        mail.setMaxLength(50);
        mail.setPlaceholder("Enter e-mail");
        
        TextField username = new TextField("Username");
        username.setMaxLength(30);
        username.setPlaceholder("Enter username");
        
        PasswordField psw = new PasswordField("Password");
        psw.setPlaceholder("Enter password");
        
        PasswordField pswRepeated = new PasswordField("Repeat password");
        pswRepeated.setPlaceholder("Repeat password");
        
        TextField name = new TextField("Name");
        name.setMaxLength(30);
        name.setPlaceholder("Enter name");
        
        TextField surname = new TextField("Surname");
        surname.setMaxLength(30);
        surname.setPlaceholder("Enter surname");
        
        TextField fiscalCode = new TextField("Fiscal code");
        fiscalCode.setMaxLength(16);
        fiscalCode.setPlaceholder("Enter fiscal code");
        
        TextField telNum = new TextField("Telephone number");
        telNum.setMaxLength(15);
        telNum.setPlaceholder("Enter telephone number");
        
        TextField cellNum = new TextField("Cellphone number");
        cellNum.setMaxLength(50);
        cellNum.setPlaceholder("Enter cellphone number");
        
        TextField city = new TextField("City");
        city.setMaxLength(40);
        city.setPlaceholder("Enter city");
        
        ComboBox<String> userType = new ComboBox<>("Select type of user");
        List<String> typeOfUsers = new ArrayList<>();
        typeOfUsers.add("Casual user");
        typeOfUsers.add("Professional user");
        typeOfUsers.add("Scholastic user");
        userType.setItems(typeOfUsers);
        
        loginForm.addComponents(mail, username, psw, pswRepeated, 
        		name, surname, fiscalCode, telNum, cellNum, city, userType);
        
        HorizontalLayout btns = new HorizontalLayout();
        Button confirm = new Button("Confirm");
        confirm.addStyleName(ValoTheme.BUTTON_PRIMARY);
        Button cancel = new Button("Cancel");
        btns.addComponents(cancel, confirm);
        btns.setComponentAlignment(cancel, Alignment.MIDDLE_LEFT);
        btns.setComponentAlignment(cancel, Alignment.MIDDLE_RIGHT);
        
        loginForm.addComponent(btns);
        
        mainContent.addComponent(loginForm);
        
        cancel.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
            	goToLoginView();
            }
        });
        
        confirm.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
            	System.out.println(mail.getValue());
            	System.out.println(username.getValue());
            	System.out.println(name.getValue());
            	System.out.println(surname.getValue());
            	System.out.println(psw.getValue());
            	System.out.println(telNum.getValue());
            	System.out.println(cellNum.getValue());
            }
        });
        
	}

	protected void goToLoginView() {
		Page.getCurrent().setTitle("zumzum.it - login");
        UI.getCurrent().setContent(new LoginView());
        addStyleName("loginview");	
	}

}

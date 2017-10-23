package view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

import controller.Authentication;
import dao.UserDAO;
import model.Encode;
import model.TipoCliente;
import model.User;

@SuppressWarnings("serial")
public class RegisterView extends VerticalLayout {

	public RegisterView() {
		
		HorizontalLayout logo = new HorizontalLayout();
        Label nameLogo = new Label("ZUMZUM.IT");
        nameLogo.setStyleName("LOGO");
        nameLogo.addStyleName(ValoTheme.LABEL_COLORED);
        Image image = new Image("", new ThemeResource("img/duck-walking.gif"));
        image.setWidth(50, Unit.PIXELS);
        image.setHeight(50, Unit.PIXELS);
        logo.addComponents(image, nameLogo);
        logo.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
        logo.setComponentAlignment(nameLogo, Alignment.BOTTOM_CENTER);
        
        addComponent(logo);
        setComponentAlignment(logo, Alignment.TOP_CENTER);
        
        Panel mainContent = new Panel("Registration Form");
        mainContent.setSizeUndefined();
        
        //VerticalLayout mainContent = new VerticalLayout();
        
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
        
        TextField cellNum = new TextField("Cellphone number*");
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
        
        //mainContent.addComponent(loginForm);
        
        loginForm.setSizeUndefined(); // Shrink to fit
        loginForm.setMargin(true);
        mainContent.setContent(loginForm);
        
        Label infoLabel1 = new Label("<i>Fields marked with *</i>", ContentMode.HTML);
        Label infoLabel2 = new Label("<i>are not required.</i>", ContentMode.HTML);
        //mainContent.addComponent(infoLabel);
        loginForm.addComponents(infoLabel1, infoLabel2);
        
        HorizontalLayout btns = new HorizontalLayout();
        Button confirm = new Button("Confirm");
        confirm.addStyleName(ValoTheme.BUTTON_PRIMARY);
        Button cancel = new Button("Cancel");
        btns.addComponents(cancel, confirm);
        btns.setComponentAlignment(cancel, Alignment.MIDDLE_LEFT);
        btns.setComponentAlignment(cancel, Alignment.MIDDLE_RIGHT);
        
        //mainContent.addComponent(btns);
        loginForm.addComponent(btns);
        
        addComponent(mainContent);
        setComponentAlignment(mainContent, Alignment.MIDDLE_CENTER);
        
        cancel.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
            	goToLoginView();
            }
        });
        
        confirm.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
            	
            	String regex = "[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
            	
            	if(		username.getValue().isEmpty() ||
                		psw.getValue().isEmpty() || pswRepeated.getValue().isEmpty() ||
                		name.getValue().isEmpty() ||
                		surname.getValue().isEmpty() ||
                		fiscalCode.getValue().isEmpty() ||
                		telNum.getValue().isEmpty() ||
                		city.getValue().isEmpty() ||
                		userType.isEmpty()
            			) {
                		Notification.show("All fields are required", Notification.Type.WARNING_MESSAGE);
                		return;
                	}
            	if(!(mail.getValue().matches(regex))) {
            		Notification.show("E-mail not valid", Notification.Type.WARNING_MESSAGE);
            		return;
            	}
            	if(!(psw.getValue().equals(pswRepeated.getValue()))) {
            		Notification.show("Password doesn't match: repeat password", Notification.Type.WARNING_MESSAGE);
            		return;
            	}
            	
            	//Fiscal code of a person
            	regex = "^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]";
            	if(!fiscalCode.getValue().matches(regex)) {
            		//Fiscal code of an entity or company
            		regex = "[a-zA-Z]{8,9}";
            		if(!(fiscalCode.getValue().matches(regex) && userType.getValue().equals("Scholastic user"))) {
            			Notification.show("Fiscal code inserted is wrong.", Notification.Type.WARNING_MESSAGE);
            			return;
            		}
            	}
            	
            	//TODO
            	System.out.println("mail: " + mail.getValue());
            	System.out.println("username: " + username.getValue());
            	System.out.println("psw: " + psw.getValue());
            	System.out.println("psw repeated: " + pswRepeated.getValue());
            	System.out.println("name: " + name.getValue());
            	System.out.println("surname: " + surname.getValue());
            	System.out.println("cf: " + fiscalCode.getValue());
            	System.out.println("tel: " + telNum.getValue());
            	System.out.println("cell: " + cellNum.getValue());
            	System.out.println("city: " + city.getValue());
            	System.out.println("usertype: " + userType.getValue());
            	
            	User utente = null;
            	
            	TipoCliente userTypeEnum;
            	switch(userType.getValue()) {
    			case "Casual user":
    				userTypeEnum = TipoCliente.OCCASIONALE;
    				break;
    			case "Professional user":
    				userTypeEnum = TipoCliente.PROFESSIONISTA;
    				break;
    			case "Scholastic user":
    				userTypeEnum = TipoCliente.TITOLARE;
    				break;
    			default:
    				userTypeEnum = TipoCliente.OCCASIONALE;
    			}
            	
            	if (!cellNum.getValue().isEmpty()) {
    				utente = new User(mail.getValue(), username.getValue(), Encode.cryptingString(psw.getValue()), 
    						name.getValue(), surname.getValue(), telNum.getValue(), city.getValue(),
    						fiscalCode.getValue(), userTypeEnum, cellNum.getValue());
    			}
    			else {
    				utente = new User(mail.getValue(), username.getValue(), Encode.cryptingString(psw.getValue()), 
    						name.getValue(), surname.getValue(), telNum.getValue(), city.getValue(),
    						fiscalCode.getValue(), userTypeEnum);
    			}
            	
            	UserDAO.addInDatabase(utente);
            	
            	goToMainView(mail.getValue(), psw.getValue());
            	
            }
        });
        
	}

	protected void goToLoginView() {
		Page.getCurrent().setTitle("zumzum.it - login");
        UI.getCurrent().setContent(new LoginView());
        addStyleName("loginview");	
	}
	
	protected void goToMainView(String mail, String psw) {
		Authentication localAuth = (Authentication) UI.getCurrent().getSession().getAttribute("AUTH");
		if(localAuth.authenticate(mail, psw)) {
			Page.getCurrent().setTitle("zumzum.it");
			UI.getCurrent().setContent(new MainView());
	        removeStyleName("loginview");
		}
		else {
			Notification.show("Error in register or in redirect.", Notification.Type.ERROR_MESSAGE);
			try {
				Thread.sleep(3000);
				goToLoginView();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

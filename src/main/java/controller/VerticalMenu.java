package controller;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

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
        initButtons();
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
        
    }

}
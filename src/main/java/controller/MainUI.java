package controller;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

import view.MainView;

public class MainUI extends UI {

	@Override
	protected void init(VaadinRequest request) {
		
		new MainView();
		
	}

}

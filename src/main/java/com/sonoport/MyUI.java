/*
 * Copyright 2014 sonoport.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.sonoport;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.sonoport.model.account.User;
import com.sonoport.service.account.UserService;
import com.sonoport.spring.ApplicationContextLocator;
import com.sonoport.util.UISessionState;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Our main UI class.
 * 
 * @author Peter Back
 * 
 */
@Theme("mytheme")
@SuppressWarnings("serial")
@PreserveOnRefresh
@Configurable
public class MyUI extends UI {
	
	private static final Logger LOG = Logger.getLogger(MyUI.class.getName());
	
	@Autowired
	private UISessionState sessionState;

	@Autowired
	private UserService userService;
	
	private final VerticalLayout layout = new VerticalLayout();

	/**
	 * Need to detect browser refresh and kill any open Windows because
	 * they will be associated to a stale UI instance
	 */
	@Override
	protected void refresh(VaadinRequest request)
	{
		for (Window w : getWindows()) {
			w.close();
		}
		init(request);
		super.refresh(request);
	}
	
	@Override
	protected void init(VaadinRequest request)
	{	
		if (sessionState == null) {
			// This initialises the Spring Context from ContextConfiguration.java
			ApplicationContextLocator.getApplicationContext().getAutowireCapableBeanFactory().autowireBean(this);
		}
		
		if (sessionState.isReplaceSession()) {
			// We've just been redirected back on session expiry
			sessionState.setVaadinSession(VaadinSession.getCurrent());
			sessionState.setReplaceSession(false);
		}
		
		if (sessionState.staleSessionCheck(VaadinSession.getCurrent(), request)) {
			return;
		}
				
		getPage().setTitle("My UI");
		this.setContent(layout);
		layout.setSizeFull();
		
		Label testLabel = new Label("I'm a label");
		
		layout.addComponent(testLabel);
		
		List<User> userList = this.userService.listAll();
		
		LOG.info("ALL USERS : "+userList.toString());
	
	}

	public UISessionState getSessionState() {
		return sessionState;
	}

	public void setSessionState(UISessionState sessionState) {
		this.sessionState = sessionState;
	}

}

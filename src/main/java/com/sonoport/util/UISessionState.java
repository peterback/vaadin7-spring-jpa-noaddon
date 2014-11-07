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
package com.sonoport.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.WrappedHttpSession;

/**
 * This Spring managed bean is used to detect stale Vaadin sessions.
 * 
 * @author Peter Back
 *
 */
public class UISessionState {

	private static Logger LOG = Logger.getLogger(UISessionState.class.getName());

	private VaadinSession vaadinSession;

	private boolean replaceSession = true;
	
	public UISessionState() {
		
	}
	
	public boolean staleSessionCheck(VaadinSession inSession, VaadinRequest request) {
		if (!vaadinSession.equals(inSession)) {
			LOG.log(Level.INFO, "Stale session detected, invalidating HTTPSession and reloading!");
			HttpSession httpSession = ((WrappedHttpSession) request.getWrappedSession()).getHttpSession();
			httpSession.invalidate();
			Page.getCurrent().setLocation("/");
			replaceSession = true;
			return true;
		}
		return false;
	}

	public void setVaadinSession(VaadinSession vaadinSession) {
		this.vaadinSession = vaadinSession;
	}

	public VaadinSession getVaadinSession() {
		return vaadinSession;
	}

	public boolean isReplaceSession() {
		return replaceSession;
	}

	public void setReplaceSession(boolean replaceSession) {
		this.replaceSession = replaceSession;
	}
}

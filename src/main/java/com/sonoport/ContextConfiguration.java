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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;
import com.sonoport.service.account.UserService;
import com.sonoport.service.account.jpa.UserServiceImpl;
import com.sonoport.util.UISessionState;

/**
 * Our Spring context
 * @author Peter Back
 *
 */
@Configuration
public class ContextConfiguration {

	@Bean
	@Scope(WebApplicationContext.SCOPE_SESSION)
	public UISessionState getUISessionState() {
		return new UISessionState();
	}

	// SERVICES

	@Bean
	@Scope(WebApplicationContext.SCOPE_SESSION)
	public UserService getUserService() {
		return new UserServiceImpl();
	}
}

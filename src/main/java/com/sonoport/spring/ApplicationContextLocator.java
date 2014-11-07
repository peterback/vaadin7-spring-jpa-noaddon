/*
 * Copyright 2012 Lexaden.com
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
package com.sonoport.spring;

import org.springframework.context.ApplicationContext;

import java.net.URL;
import java.util.Collection;

/**
 * Static util class to access spring application context via static method.
 *
 * Note from Peter Back: We borrowed this from the Lexladen.com Vaadin 7 Spring example
 * the only modification is the package name.
 *
 * @author Aliaksei Papou
 */
public class ApplicationContextLocator {

    private static final Object monitor = new Object();
    protected static ApplicationContext applicationContext;
    protected static Collection<URL> webFlowConfiguration;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ApplicationContextLocator.applicationContext = applicationContext;
        synchronized (monitor) {
            monitor.notifyAll();
        }
    }

    public static ApplicationContext getApplicationContext() {
        try {
            synchronized (monitor) {
                while (ApplicationContextLocator.applicationContext == null) {
                    monitor.wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ApplicationContextLocator.applicationContext;
    }

    public static void setWebFlowConfiguration(Collection<URL> webFlowConfiguration) {
        ApplicationContextLocator.webFlowConfiguration = webFlowConfiguration;
        synchronized (monitor) {
            monitor.notifyAll();
        }
    }

    public static Collection<URL> getWebFlowConfiguration() {
        try {
            synchronized (monitor) {
                while (ApplicationContextLocator.webFlowConfiguration == null) {
                    monitor.wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ApplicationContextLocator.webFlowConfiguration;
    }

    @SuppressWarnings("unchecked")
	public static <T extends Object> T getBean(String beanId) {
        return (T) getApplicationContext().getBean(beanId);
    }

    public static ApplicationContext getApplicationContextOrNull() {
        return ApplicationContextLocator.applicationContext;
    }
}

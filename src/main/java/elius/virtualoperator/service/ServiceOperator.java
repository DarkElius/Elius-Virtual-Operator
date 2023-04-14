/**
    Licensed to the Apache Software Foundation (ASF) under one
    or more contributor license agreements.  See the NOTICE file
    distributed with this work for additional information
    regarding copyright ownership.  The ASF licenses this file
    to you under the Apache License, Version 2.0 (the
    "License"); you may not use this file except in compliance
    with the License.  You may obtain a copy of the License at
    
      http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.
*/

package elius.virtualoperator.service;

import elius.virtualoperator.VirtualOperator;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import jakarta.servlet.annotation.WebListener;

@WebListener("/ApplicationServlet")
public class ServiceOperator implements ServletContextListener {

	// Application
	private VirtualOperator application;

	
	/**
	 * Initialize application service
	 */
	public void contextInitialized(ServletContextEvent context) {
		// Create application
		application = new VirtualOperator();
		// Initialize application
		application.initialization();
	}	
	
	/**
	 * Destroy application service
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// Destroy application
		application.destroy();
	}

}




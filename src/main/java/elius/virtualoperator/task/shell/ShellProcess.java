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

package elius.virtualoperator.task.shell;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import elius.webapp.framework.properties.PropertiesManager;
import elius.webapp.framework.properties.PropertiesManagerFactory;
import elius.virtualoperator.task.Task;
import elius.virtualoperator.task.TaskProcess;
import elius.virtualoperator.task.TaskStatus;


public class ShellProcess implements TaskProcess {
	
	// Get logger
	private static Logger logger = LogManager.getLogger(ShellProcess.class);
	
	// Property file
	private PropertiesManager appProperties;
	
	// Process Result
	private String pResult;
	
	// Task Status
	private TaskStatus tStatus;
	
	
	@Override
	public void execute(Task task) {
		// Application properties
		appProperties = PropertiesManagerFactory.getInstance(ShellAttributes.DEFAULT_SHELL_PROPERTIES_FILE);
		
		// Load shell properties
		if(appProperties.isLoaded()) {
			// Set error
			tStatus = TaskStatus.ERROR;
			// Set error message
			pResult = "Error loading properties";
			// Log message
			logger.error("Error loading " + ShellAttributes.DEFAULT_SHELL_PROPERTIES_FILE + " properties file");
			// Exit
			return;
		}
		
		// Get path where scripts are stored
		String pathScripts = appProperties.get(ShellAttributes.PROP_TASK_SHELL_SCRIPTS_PATH);
		
		// Get the working directory
		String pathWork = appProperties.get(ShellAttributes.PROP_TASK_SHELL_WORK_PATH);
		
		// Check if EVO is running on windows
		boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

		// Process Builder instance
		ProcessBuilder builder = new ProcessBuilder();
		
		// Set command with the right settings
		if (isWindows) {
			// Set command for windows
			builder.command("cmd.exe", "/c", "dir");
		} else {
			// Set command on nix systems
			builder.command("sh", "-c", pathScripts + "/" + task.getDetails());
		}

		// Set working directory
		builder.directory(new File(pathWork));
		
		try {
			
			// Create process
			Process process = builder.start();

			// Initialize output catcher
			StringBuilder output = new StringBuilder();

			// Get output from process
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			// Final output string
			String line;
			// Concatenate output to one string
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
	
			// Wait for the end and set the exit code
			int exitCode = process.waitFor(); 
			
			// Redirect script output to task result
			pResult = output.toString();

			
			// Set the correct task exit code
			if (0 == exitCode)
				tStatus = TaskStatus.ENDED;
			else
				tStatus = TaskStatus.ERROR;


		} catch (Exception e) {
			// Log the error
			logger.error(e.getMessage());
			// Set result
			pResult = "Generic error, check system log";
			// Set task error code
			tStatus = TaskStatus.ERROR;
		}
		
	}

	
	@Override
	public String getProcessResult() {
		return pResult;
	}

	
	@Override
	public TaskStatus getTaskStatusResult() {
		return tStatus;
	}
	
}

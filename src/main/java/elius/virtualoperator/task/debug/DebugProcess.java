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

package elius.virtualoperator.task.debug;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import elius.virtualoperator.task.Task;
import elius.virtualoperator.task.TaskProcess;
import elius.virtualoperator.task.TaskStatus;

public class DebugProcess implements TaskProcess {
	
	// Get logger
	private static Logger logger = LogManager.getLogger(DebugProcess.class);
	
	// Random exit status
	int exitStatus;

	@Override
	public void execute(Task task) {
		
		// Random number generator
		Random rand = new Random();
		
		// Generate a status from ENDED, WARNING, ERROR
		exitStatus =  5 + rand.nextInt(3);
		
		
		try { 
			// Do nothing
		    Thread.sleep(rand.nextInt(4000));
		    
		} catch (InterruptedException e) {
			// Log error
			logger.error("Error processing task");
			// Log message
			logger.error(e.getMessage());
		}
		
		
		
		// Log process start
		logger.info("End processing task(" + task.getUuid() + ") status(" + TaskStatus.getById(exitStatus).getName() + ")");		
	}

	@Override
	public String getProcessResult() {
		return "This is the result";
	}

	@Override
	public TaskStatus getTaskStatusResult() {		
		return TaskStatus.getById(exitStatus);
	}

}
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

package elius.virtualoperator.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import elius.virtualoperator.VirtualOperatorAttributes;
import elius.virtualoperator.VirtualOperatorID;
import elius.virtualoperator.lock.LockManager;
import elius.webapp.framework.properties.PropertiesManager;
import elius.webapp.framework.properties.PropertiesManagerFactory;

public class TaskSubmitter implements Runnable {

	// Get logger
	private static Logger logger = LogManager.getLogger(TaskSubmitter.class);
	
	// Task Database
	private TaskDatabase dbTask;
	
	// Pointer to the executor service
	private ExecutorService executor;
	
	// The current list of task submitted by the executor
	private List<Future<?>> cFutures;
	
	// Property file
	private PropertiesManager appProperties;
	
	// Lock Manager
	private LockManager lockManager;
	
	
	/**
	 * Constructor
	 * @param executor Executor service pointer
	 */
	public TaskSubmitter(ExecutorService executor) {
		// Error Checking
		if(null == executor) {
			logger.error("Null executor service passed");
		}

		// Assign pointer
		this.executor = executor;
		
		// The current list of task submitted by the executor
		cFutures = new ArrayList<Future<?>>();
		
		// Task Database instance
		dbTask = new TaskDatabase();
		
		// Application properties
		appProperties = PropertiesManagerFactory.getInstance(VirtualOperatorAttributes.EVO_PROPERTIES_FILE);
		
		// Create lock manager
		lockManager = new LockManager();
		
		// Initialize lock manager
		lockManager.initialization();
	}
	
	@Override
	public void run() {
		// Log search start
		logger.debug("Submitter started");
			
		// Set task number to own 
		int nTasksToOwn = appProperties.getInt(VirtualOperatorAttributes.PROP_TASK_SUBMITTER_TASK_TO_OWN, VirtualOperatorAttributes.DEFAULT_TASK_SUBMITTER_TASK_TO_OWN);

		// Reorganize task list
		int runningTasks = taskReorg();
		
		// Can own tasks
		if ((nTasksToOwn - runningTasks) > 0) {

			// Get lock
			if(0 != lockManager.lock())
				return;
			
	        // Fetch first x new tasks - the task that are currently running
			List<Task> tasks = dbTask.getNew(nTasksToOwn - runningTasks);
			
			// Submit all task for the execution
			if(null != tasks) {
				
				// Scroll tasks list
				for(Task t : tasks) {
					
					// Update task status and set the current owner that will change during task execution
					dbTask.own(t.getUuid(), TaskStatus.OWNED, VirtualOperatorID.get());
					
					// Submit task for the execution
					Future<?> f = executor.submit(new TaskExecutor(t));
					
					// Add future to list
					cFutures.add(f);
				}
			}

			// Release lock
			lockManager.unlock();
			
		} else {
			// Log that no task will be owned because the old one are still running
			logger.debug("Old process are still running");
		}
	

		
		// Log search start
		logger.debug("Submitter ended");	
	}

	
	/**
	 * Get the number of current running tasks and cleanup the list
	 * @return The number of current running tasks
	 */
	private int taskReorg() {
		
		// The number of current running tasks
		int nTasks = 0;
		
		// The list of objects to be deleted
		List<Future<?>> toBeRemoved = new ArrayList<Future<?>>();
		
		// Scroll previous process
		for (Future<?> future : cFutures) {
			// Check if previous process are completed for any reason
			if(future.isDone()) {
				// Set to be deleted
				toBeRemoved.add(future);
			} else {
				// Some process are still running
				nTasks ++;
			}
		}
		
		// Scroll the list of items to be deleted
		for (Future<?> future : toBeRemoved) {
			// Remove from the current
			cFutures.remove(future);
		}
		
		// Clean the "to be removed" list
		toBeRemoved.clear();
		
		// Return current running tasks
		return nTasks;
	}
}

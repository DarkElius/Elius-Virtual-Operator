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

package elius.virtualoperator.task.job;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import elius.virtualoperator.task.Task;
import elius.virtualoperator.task.TaskProcess;
import elius.virtualoperator.task.TaskStatus;
import elius.virtualoperator.task.job.flow.JobFlowResult;
import elius.virtualoperator.task.job.flow.JobFlowScript;



public class JobProcessScript  implements TaskProcess {
	
	// Get logger
	private static Logger logger = LogManager.getLogger(JobProcessScript.class);
	
	// Process Result
	private String pResult;
	
	// Task Status
	private TaskStatus tStatus;

	
	@Override
	public void execute(Task task) {

		// Set default value
		Job job = null;

		// Initialize JSON to object converter
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
		
			// Convert JSON task details to object
			job = objectMapper.readValue(task.getDetails(), Job.class);

		} catch (Exception e) {
			
			// Print error
			logger.error(e.getMessage());
			
			// Set invalid object
			job = null;
		} 
		
		
		// Verify the conversion
		if(null == job) {
			
			// Set result message
			pResult = "Invalid data: please check task details";
			// Set result code
			tStatus = TaskStatus.ERROR;
			// Update log
			logger.error(pResult);
			
		} else {
			
			// Initialize the correct flow
			JobFlowScript jfs = new JobFlowScript(job);
			
			// Execute the flow
			jfs.run();
			
			// Map result codes from flow to task
			if(JobFlowResult.OK == jfs.getFlowResultCode()) {
				
				// Set result code
				tStatus = TaskStatus.ENDED;			
				// Set result message
				pResult = jfs.getFlowResultMessage();
				
			} else if(JobFlowResult.WARNING == jfs.getFlowResultCode()) {
				
				// Set result code
				tStatus = TaskStatus.WARNING;			
				// Set result message
				pResult = jfs.getFlowResultMessage();
				
			} else if(JobFlowResult.ERROR == jfs.getFlowResultCode()) {
				
				// Set result code
				tStatus = TaskStatus.ERROR;			
				// Set result message
				pResult = jfs.getFlowResultMessage();
				
			} else if(JobFlowResult.UNKNOWN == jfs.getFlowResultCode()) {
				
				// Set result code
				tStatus = TaskStatus.ERROR;	
				// Set result message
				pResult = "Flow execution is uncompleted / unknown error";
				
			}

		
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

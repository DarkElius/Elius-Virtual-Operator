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

package elius.virtualoperator.task.job.flow;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import elius.virtualoperator.task.job.Job;
import elius.virtualoperator.task.job.JobStep;
import elius.virtualoperator.task.job.log.JobLogScript;

public class JobFlowScript extends JobFlow {
	
	// Get logger
	private static Logger logger = LogManager.getLogger(JobFlowScript.class);
	
	// Job Step
	private JobStep jobStep;
	

	/**
	 * Constructor
	 * @param job Job
	 */
	public JobFlowScript(Job job) {
		super(job);	
		
		// Initialize job step
		jobStep = null;
	}
	

	@Override
	protected void checkConditions() {

		// Log
		logger.trace("Check conditions");
	}

	
	@Override
	protected void fetchLog() {
		// Log
		logger.trace("Fetch Log");
		
		// Set job
		JobLogScript jls = new JobLogScript(job);
		
		// Initialize
		jls.initialize();
		
		// Fetch log
		if(0 == jls.fetch()) {
			
			// Get parsed log
			jobStep = jls.getJobStep();
			
		} else {
			
			// Set Flow Error
			flowResultCode = JobFlowResult.ERROR;
			
			// Set Flow Error
			flowResultMessage = "Unable to fetch log";
		}
		
	}
	

	@Override
	protected void checkInstructions() {
		// Log
		logger.trace("Check instructions");
		
		// Instruction can usually be stored in the output or in the scheduler in case of jobs

	}

	
	@Override
	protected void searchMessages() {
		// Log
		logger.trace("Search messages");

	}

	
	@Override
	protected void evaluate() {
		logger.trace("evaluate");
		
	}

	
	@Override
	protected void doActions() {
		logger.info("doActions");
		
	}

}

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

import elius.virtualoperator.task.job.Job;

public abstract class JobFlow {
	
	// Job to be evaluated with the flow
	protected Job job;
	
	// Flow result code
	protected JobFlowResult flowResultCode;
	
	// Flow result message
	protected String flowResultMessage;
	
	// Go to the next step of the flow
	protected boolean goNextStep;
	
	
	/**
	 * Constructor
	 * @param job Job
	 */
	public JobFlow(Job job) {
		// Set job
		this.job = job;
		
		// Flow result code
		flowResultCode = JobFlowResult.UNKNOWN;
		
		// Flow result message
		flowResultMessage = "";
		
		// Enable flow next-step
		goNextStep = true;
	}
	
	
	/**
	 * Start flow execution
	 * @return 0 OK, otherwise error
	 */
	public void run() {
		
		// Go to the next flow step
		if(goNextStep) {
			// Check conditions
			checkConditions();			
		}
		
		// Fetch log
		if(goNextStep) 
			fetchLog();
			
		// Check instructions
		if(goNextStep) 
			checkInstructions();

		
		// Search messages
		if(goNextStep)
			searchMessages();

		
		// Job evaluation
		if(goNextStep)
			evaluate();

		
		// Execute actions
		if(goNextStep)
			doActions();
		
	}

	
	/**
	 * Get final flow result code
	 * @return Flow result code
	 */
	public JobFlowResult getFlowResultCode() {
		return flowResultCode; 
	}
	

	/**
	 * Get final flow result message
	 * @return Flow result message
	 */
	public String getFlowResultMessage() {
		return flowResultMessage; 
	}
	
	
	/**
	 * Check conditions
	 */
	protected abstract void checkConditions();
	
	
	/**
	 * Fetch log
	 * Extract log info for selected job
	 */
	protected abstract void fetchLog();
	
	
	/**
	 * Check instructions
	 * Instruction can usually be stored in the output or in the scheduler in case of jobs
	 */
	protected abstract void checkInstructions();
	
	
	/**
	 * Search messages in the log
	 */
	protected abstract void searchMessages();
	
	
	/**
	 * Job evaluation
	 */
	protected abstract void evaluate();
	
	
	/**
	 * Execute actions
	 */
	protected abstract void doActions();

	
}

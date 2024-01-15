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
	}
	
	
	/**
	 * Start flow execution
	 * @return 0 OK, otherwise error
	 */
	public void run() {
		
		// Next step if result code is not yet defined
		if(JobFlowResult.UNKNOWN == flowResultCode) {
			// Check conditions
			checkConditions();			
		}
		
		// Next step if result code is not yet defined
		if(JobFlowResult.UNKNOWN == flowResultCode) {
			// Fetch log
			fetchLog();
		}
			
		// Next step if result code is not yet defined
		if(JobFlowResult.UNKNOWN == flowResultCode) {
			// Check instructions
			checkInstructions();
		}
		
		// Next step if result code is not yet defined
		if(JobFlowResult.UNKNOWN == flowResultCode) {
			// Search messages
			searchMessages();
		}
		
		// Next step if result code is not yet defined
		if(JobFlowResult.UNKNOWN == flowResultCode) {
			// Job evaluation
			evaluate();
		}
		
		// Next step if result code is not yet defined
		if(JobFlowResult.UNKNOWN == flowResultCode) {
			// Execute actions
			doActions();
		}
		
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
	
	
	// Check conditions
	protected abstract void checkConditions();
	
	
	// Fetch log
	protected abstract void fetchLog();
	
	
	// Check instructions
	protected abstract void checkInstructions();
	
	
	// Search messages
	protected abstract void searchMessages();
	
	
	// Job evaluation
	protected abstract void evaluate();
	
	
	// Execute actions
	protected abstract void doActions();

	
}

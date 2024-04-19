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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import elius.virtualoperator.task.job.Job;
import elius.virtualoperator.task.job.JobStep;
import elius.virtualoperator.task.job.JobType;
import elius.virtualoperator.task.job.log.JobLogBetaSystems;
import elius.virtualoperator.task.job.search.JobSearch;
import elius.virtualoperator.task.job.search.JobSearchRepository;
import elius.virtualoperator.task.job.search.JobSearchRepositoryAttributes;

public class JobFlowBetaSystems extends JobFlow {
	
	// Get logger
	private static Logger logger = LogManager.getLogger(JobFlowBetaSystems.class);
	
	// Job Steps
	private List<JobStep> jobSteps;
	
	// Job Search match list
	private JobSearch jobSearch;
	
	// Job Type
	private JobType jobType;
	

	/**
	 * Constructor
	 * @param job Job
	 */
	public JobFlowBetaSystems(Job job, JobType jobType) {
		
		super(job);	
		
		// Set job type
		this.jobType = jobType;
		
		// Initialize job steps
		jobSteps = null;
		
		// Initialize job search
		jobSearch = new JobSearch();
		
	}
	


	@Override
	protected void checkConditions() {};
	

	
	@Override
	protected void fetchLog() {
		// Log
		logger.trace("Fetch Log");
		
		// Set job
		JobLogBetaSystems jlbs = new JobLogBetaSystems(job);
		
		// Initialize
		jlbs.initialize();
		
		// Fetch log
		if(0 == jlbs.fetch()) {
			
			// Get parsed log
			jobSteps = jlbs.getJobSteps();
			
			// Log
			logger.debug("Log fetched");

		} else {
			
			// Set Flow Error
			flowResultCode = JobFlowResult.ERROR;
			
			// Set Flow Error
			flowResultMessage = "Unable to fetch log";
			
			// Stop the flow
			goNextStep = false;
		}
		
	}
	

	@Override
	protected void checkInstructions() {};

	
	@Override
	protected void searchMessages() {
		
		// Log
		logger.trace("Search messages");
		
		// Create repository
		JobSearchRepository jsRepo = new JobSearchRepository();
		
		String repo = "";
		
		// Select search entries repository for the specified job type: default is open
		switch(jobType) {
			
			case OPEN:
				repo = JobSearchRepositoryAttributes.SEARCH_ENTRIES_JOB_SCRIPT;
				break;

			case MAINFRAME:
				repo = JobSearchRepositoryAttributes.SEARCH_ENTRIES_JOB_MAINFRAME;
				break;
			
			default:
				repo = JobSearchRepositoryAttributes.SEARCH_ENTRIES_JOB_SCRIPT;
				break;
		}
		
		
		// Load from repository the search entries
		if(0 == jsRepo.load(repo)) {
			
			// Search in every job step
			for (JobStep jobStep : jobSteps) {
				
				// Search for a match
				jobSearch.search(jobStep, jsRepo);
				
			}
			
		} else {
			
			// Set flow code
			flowResultCode = JobFlowResult.ERROR;
			
			// Set flow message
			flowResultMessage = "Unable to load search entries from repository";
			
			// Stop the flow
			goNextStep = false;
			
		}

	}

	
	@Override
	protected void evaluate() {

		// Log
		logger.trace("Evaluate");
		
		// At least one message found
		if(jobSearch.getNMatches().size() > 0) {
			
			// Inform that some messages were found in the log
			flowResultCode = JobFlowResult.WARNING;
			
			
			
			ObjectMapper objectMapper = new ObjectMapper();


			try {
				
				// Set result message in JSON format
				flowResultMessage = objectMapper.writeValueAsString(jobSearch);
				
			} catch (JsonProcessingException e) {
				
				// Set flow code
				flowResultCode = JobFlowResult.ERROR;
				
				// Set flow message
				flowResultMessage = "Unable to write Result Message in JSON format";
				
				// Stop the flow
				goNextStep = false;
				
			}
		
		} else {

			// Inform that some messages were found in the log
			flowResultCode = JobFlowResult.OK;
			
		}
		

	
		
	}

	
	@Override
	protected void doActions() {};

}

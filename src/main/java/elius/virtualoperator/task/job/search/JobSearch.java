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


package elius.virtualoperator.task.job.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import elius.virtualoperator.task.job.JobStep;

public class JobSearch {

	// Get logger
	private static Logger logger = LogManager.getLogger(JobSearch.class);
	
	// Matches
	List<JobSearchMatch> jsMatches;
	
	
	/**
	 * Search matches in the job step log
	 * @param jobStep Job step
	 * @param jsRepo Job search repository
	 * @return The number of matches
	 */
	public int search(JobStep jobStep, JobSearchRepository jsRepo) {
		
		// Log
		logger.trace("Search: use repositorty(" + jsRepo.getRepository() + ") for job step("+ jobStep.getName() + ")");
		
		
		// Initialize match list
		if(null == jsMatches)
			jsMatches = new ArrayList<JobSearchMatch>();
		else
			jsMatches.clear();
		
		
		// Get search entries
		List<String> searchEntries = jsRepo.getEntries();
		
		// Search entries in the output
		for (String se : searchEntries) {
			
			// Read log looking for a match
			for (String line : jobStep.getLog()) {
				
				// Match found
				if(line.matches(se)) {
					
					// Initialize match element
					JobSearchMatch jsm = new JobSearchMatch();
					
					// Set message where match is present
					jsm.setMessage(line);
					
					// Set regular expression used
					jsm.setRegEx(se);

					// Add match to list
					jsMatches.add(jsm);
					
					// Log
					logger.trace("Found(" + se + " on line (" + line + ")");
					
				}
				
			}
			
		}
	
		// Log
		logger.debug("Search ended");
		
		// Return the number of matches
		return jsMatches.size();
	}
	
	
	/**
	 * Get the list of matches of the last search
	 * @return List of job search matches
	 */
	public List<JobSearchMatch> getNMatches() {
		// Return the number of matches of the last search
		return jsMatches;
	}
	
	
}

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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import elius.webapp.framework.application.ApplicationAttributes;

public class JobSearchRepository {
	
	// Get logger
	private static Logger logger = LogManager.getLogger(JobSearchRepository.class);

	// Entries list
	private List<String> entries;
	
	// Repository
	private String repository;
	
	
	/**
	 * Constructor
	 */
	public JobSearchRepository() {
		
		// Initialize entries list
		entries = new ArrayList<String>();
	}
	
	
	/**
	 * Read search entries
	 * @param repository Repository
	 * @return 0 OK, otherwise error
	 */
	public int load(String repository) {
		
		// Log
		logger.debug("Start read entries from repository(" + JobSearchRepositoryAttributes.SEARCH_ENTRIES_REPOSITORY + "/" + repository + ")");
		
		// Save repository name
		this.repository = repository;
		
		// Remove previous elements
		entries.clear();
		
		// Reader
		BufferedReader reader;

		try {
			
			// Initialize reader
			reader = new BufferedReader(new FileReader(System.getProperty(ApplicationAttributes.APP_PATH) + 
														"/" + JobSearchRepositoryAttributes.SEARCH_ENTRIES_REPOSITORY + 
														"/" + repository));
			
			// Read first line
			String line = reader.readLine();

			// Read the remaining lines
			while (line != null) {
				
				// Remove leading and trailing white spaces
				line = line.strip();
				
				// Remove empty lines and comments
				if((!"".equals(line)) && (!line.startsWith("#")) ) {
					
					// Add entries to list
					entries.add(line);
					
					// Log read line
					logger.trace(" > " + line);
				}
					
				// Read next line
				line = reader.readLine();
			}

			// Close file
			reader.close();
			
		} catch (IOException e) {
			
			// Error reading file
			logger.error("Error reading file");
			// Log Message
			logger.error(e.getMessage());
			// Set return code
			return 1;
			
		}
		
		// Log
		logger.trace("Entries successfully loaded");
		
		// Return OK
		return 0;
	}
	
	
	/**
	 * Get loaded entries
	 * @return Entries list
	 */
	public List<String> getEntries() {
		
		// Return entries list
		return entries;
		
	}

	
	/**
	 * Get repository name
	 */
	public String getRepository() {
		return repository;
	}
	
}

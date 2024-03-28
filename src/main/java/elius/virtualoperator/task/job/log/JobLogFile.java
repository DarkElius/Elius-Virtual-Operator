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

package elius.virtualoperator.task.job.log;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import elius.virtualoperator.task.job.Job;
import elius.virtualoperator.task.job.JobAttributes;
import elius.virtualoperator.task.job.JobStep;
import elius.webapp.framework.properties.PropertiesManager;

public class JobLogFile extends JobLog {
	
	// Get logger
	protected static Logger logger = LogManager.getLogger(JobLogFile.class);	

	// Properties file
	private PropertiesManager appProperties;
	
	// Path where find logs
	private String path;
	
	// Filename mask
	private String mask;
	
	// Max log size (bytes)
	private long maxLogSize;
	
	// Job step
	private JobStep jobStep;
	
	
	/**
	 * Constructor
	 * @param job Job to fetch
	 */
	public JobLogFile(Job job) {
		super(job);
		
		// Application properties
		appProperties = new PropertiesManager();
		
		// Job step
		jobStep = new JobStep();
	}

	
	/**
	 * Initialize
	 * @return 0 OK, otherwise error
	 */
	@Override
	public int initialize() {
		
		// Log
		logger.trace("Initialize job log file");
		
		// Load properties
		if(0 != appProperties.load(JobAttributes.DEFAULT_JOB_PROPERTIES_FILE))
			return -1;
		
		// Path where find logs
		path = appProperties.get(JobAttributes.PROP_TASK_JOB_LOG_FILE_PATH, JobAttributes.DEFAULT_TASK_JOB_LOG_FILE_PATH);
		
		// Filename mask
		mask = appProperties.get(JobAttributes.PROP_TASK_JOB_LOG_FILE_MASK, JobAttributes.DEFAULT_TASK_JOB_LOG_FILE_MASK);
		
		// Get Log max size to prevent overload
		maxLogSize = appProperties.getLong(JobAttributes.PROP_TASK_JOB_LOG_FILE_MAXSIZE, JobAttributes.DEFAULT_TASK_JOB_LOG_FILE_MAXSIZE);
		
		// Log
		logger.debug("Job log file initialized");
		
		return 0;
	}

	
	/**
	 * Fetch log
	 * @return 0 OK, otherwise error
	 */
	@Override
	public int fetch() {
		
		// Log
		logger.trace("Fetch job log file");
			
		// Check if URL is already defined
		if((null == job.getUrl()) || "".equalsIgnoreCase(job.getUrl())) {

			// Log
			logger.debug("Job URL is empty");

			// Get file path based on job info, mask and settings
			job.setUrl(getLogFilePath());

		}
		
		// Log
		logger.debug("Job URL(" + job.getUrl() + ")");
		
		// Create File object with URL information
		File file = new File(job.getUrl());
		
		// Check if file exist
		if (!file.exists()) {
			
        	// Log the error
        	logger.error("Job url (" + job.getUrl() + ") not found");
        	// Return error
        	return 1;
			
		}

        // Get file size
        long fileSize = file.length();
        
        // Log file size
        logger.debug("File size in bytes(" + fileSize + ")");
        
        
        // Verify if the file is too big and prevent an overload
        if(fileSize > maxLogSize) {
        	
        	// Log the error
        	logger.error("Log file is too big (bytes): file(" + fileSize + ") limit(" + maxLogSize + ")");
        	// Return error
        	return 2;
        	
        } 


        // Set complete filename path
    	Path path = Paths.get(job.getUrl());
    	
		try {

			// Set content
			jobStep.setLog(Files.readAllLines(path, StandardCharsets.UTF_8));

	        // Create job step
	        // Scripts or daemon output in general has one step
	        jobStep.setnLines(jobStep.getnLines());

	        // Because mono-step, set the same job information
	        // Name
	        jobStep.setName(job.getName());
	        // Return code
	        jobStep.setRc(job.getRc());
	        // Log URL
	        jobStep.setUrl(job.getUrl());
			
		} catch (IOException e) {
			
			// Log error
			logger.error("Error reading log(" + job.getUrl() + ")");
			logger.error(e.getMessage());
			
			// Return error
			return 1;
		}

		
		// Log
		logger.debug("Job log file fetched");
		
		// Return OK
		return 0;
	}
	
	
	
	/**
	 * Retrieve real job log filename using job info, mask and path
	 * @return Blank in case of errors, otherwise the filename
	 */
	public String getLogFilePath() {
		
		// Log
		logger.trace("Get job log file path");
		
		
		// Initialize final path with log mask
		String filePath = mask;

		// Replace name
		filePath = filePath.replaceAll("<name>", job.getName());
		
		// Replace id
		filePath = filePath.replaceAll("<id>", job.getId());
		
		// Replace return code
		filePath = filePath.replaceAll("<rc>", job.getRc());
		
	
		// Get calendar instance
		Calendar calendar = Calendar.getInstance();
		
		// Set job start date
		calendar.setTimeInMillis(job.getStarted().getTime());
		
		// Replace start date year
		filePath = filePath.replaceAll("<st-yyyy>", new SimpleDateFormat("yyyy").format(job.getEnded().getTime()));
		
		// Replace start date month
		filePath = filePath.replaceAll("<st-MM>", new SimpleDateFormat("MM").format(job.getEnded().getTime()));

		// Replace start date day
		filePath = filePath.replaceAll("<st-dd>", new SimpleDateFormat("dd").format(job.getEnded().getTime()));
		
		// Replace start date hours
		filePath = filePath.replaceAll("<st-HH>", new SimpleDateFormat("HH").format(job.getEnded().getTime()));
		
		// Replace start date minutes
		filePath = filePath.replaceAll("<st-mm>", new SimpleDateFormat("mm").format(job.getEnded().getTime()));
		
		// Replace start date seconds
		filePath = filePath.replaceAll("<st-ss>", new SimpleDateFormat("ss").format(job.getEnded().getTime()));
		
		
		// Set job end date
		calendar.setTimeInMillis(job.getEnded().getTime());
		
		// Replace end date year
		filePath = filePath.replaceAll("<et-yyyy>", new SimpleDateFormat("yyyy").format(job.getEnded().getTime()));
		
		// Replace end date month
		filePath = filePath.replaceAll("<et-MM>", new SimpleDateFormat("MM").format(job.getEnded().getTime()));

		// Replace end date day
		filePath = filePath.replaceAll("<et-dd>", new SimpleDateFormat("dd").format(job.getEnded().getTime()));
		
		// Replace end date hours
		filePath = filePath.replaceAll("<et-HH>", new SimpleDateFormat("HH").format(job.getEnded().getTime()));
		
		// Replace end date minutes
		filePath = filePath.replaceAll("<et-mm>", new SimpleDateFormat("mm").format(job.getEnded().getTime()));
		
		// Replace end date seconds
		filePath = filePath.replaceAll("<et-ss>", new SimpleDateFormat("ss").format(job.getEnded().getTime()));

		// Add log path
		filePath = path + "/" + filePath;
				
		
		
		// Log
		logger.debug("Job log file path(" + filePath + ")");
		
		// Return the real job log file path
		return filePath;
		
	}

	
	
	/**
	 * Get job step for selected job
	 * @return JobStep with all information related to the selected job
	 */
	public JobStep getJobStep() {
		return jobStep;
	}
	
	

}

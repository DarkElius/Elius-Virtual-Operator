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

import java.util.List;
import java.util.ArrayList;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import elius.virtualoperator.task.job.Job;
import elius.virtualoperator.task.job.JobAttributes;

import elius.virtualoperator.task.job.JobStep;
import elius.webapp.framework.properties.PropertiesManager;
import elius.webapp.framework.security.secret.SecretCredentials;
import elius.webapp.framework.http.HttpConnection;


public class JobLogBetaSystems extends JobLog {
	
	// Get logger
	protected static Logger logger = LogManager.getLogger(JobLogBetaSystems.class);	

	// Properties file
	private PropertiesManager appProperties;
	
	// Job steps
	private List<JobStep> jobSteps;
	
	// Base URI
	private String baseUri;
	
	// Select URI
	private String selectUri;
	
	// HTTP Connection 
	private HttpConnection httpConnection;
	
	// HTTP Credentials
	private SecretCredentials httpCredentials;
	
	
	/**
	 * Constructor
	 * @param job Job to fetch
	 */
	public JobLogBetaSystems(Job job) {
		super(job);
		
		// Application properties
		appProperties = new PropertiesManager();
		
		// Job step
		jobSteps = new ArrayList<JobStep>();
		
		// HTTP Connection 
		httpConnection = new HttpConnection();
		
		// HTTP Credentials
		httpCredentials = new SecretCredentials();
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
		
		// Base URI
		baseUri = appProperties.get(JobAttributes.PROP_TASK_JOB_LOG_BETASYSTEMS_SELECT_URI);
		
		// Select URI
		selectUri = appProperties.get(JobAttributes.PROP_TASK_JOB_LOG_BETASYSTEMS_BASE_URI);
		
		// UserId
		httpCredentials.setUserId(appProperties.get(JobAttributes.PROP_TASK_JOB_LOG_BETASYSTEMS_USERID));
		
		// Password
		httpCredentials.setPassword(appProperties.get(JobAttributes.PROP_TASK_JOB_LOG_BETASYSTEMS_PASSWORD));
		
		
		// Log
		logger.debug("Job log file initialized");
		
		
		return 0;
	}

	
	/**
	 * Fetch log from BetaSystems Control
	 * @return 0 OK, otherwise error
	 */
	@Override
	public int fetch() {
		
		// Log
		logger.trace("Fetch job log from BetaSystems Control");
		
		// Clear previous data
		jobSteps.clear();
		
		
		// Get job URI
		String jobUri = getJobUri();
		
		// Check for errors
		if("".equals(jobUri)) {
			logger.error("Error getting job URI for " + 
							job.getName() + "." + 
							job.getId() + "." +
							job.getRc() + "." +
							job.getStarted() + "." +
							job.getEnded());
		}
		
		// Get Steps
		if(0 == getJobStepsInfo(jobUri)) {
			logger.error("Error getting step info for " + 
					job.getName() + "." + 
					job.getId() + "." +
					job.getRc() + "." +
					job.getStarted() + "." +
					job.getEnded());		
		}
		
		// Log
		logger.debug("Job log fetched from BetaSystems Control");
		
		return 0;
	}
	
	
	
	
	
	
	/**
	 * Get the steps of the selected jobs
	 * @return
	 */
	public List<JobStep> getJobSteps() {
		return jobSteps;
	}
	
	
	/**
	 * Get the job URI from BetaSystems Control
	 * @return The URI of job inside the BetaSystems Control
	 */
	private String getJobUri() {
		
		// Initialize job URI
		String jobUri = "";	
		
		// Prepare URI
		String uri = selectUri + "?p:jobname=" + job.getName() + "&p:jobid=" + job.getId() + "&p:fromdate=" + job.getStarted() + "&p:todate=" + job.getEnded() + "&format=json";
		
		// Get job info
		// TODO move ignore cert on properties
		if(0 != httpConnection.get(uri, httpCredentials, true))
			return jobUri;
		
		// Parse JSON from text to object
		JSONObject jo = new JSONObject(httpConnection.getResponseContent());
		
		// Get the item with the job info	
		JSONObject a4dp = jo.getJSONObject("a4dp:return").getJSONObject("RESULT_TABLE").getJSONObject("a4dp:row");
		
		// Get job details
		JSONArray ja = a4dp.getJSONArray("a4dp:col");
		
		// Read details
		for (int i = 0; i < ja.length(); i++) {
			
			// Get URI if exist
			if("URI".equals(ja.getJSONObject(i).get("name"))) {
				
				// Set final URI
				jobUri = baseUri + ja.getJSONObject(i).get("content").toString() + "&format=json";
				
			}
			
		}

		// Return job URI
		return jobUri;
	}
	
	
	/**
	 * Return the job steps list information
	 * @param jobUri The job URI
	 * @return 0 OK, otherwise the error code
	 */
	private int getJobStepsInfo(String jobUri) {
	
		
		// Get job info
		if(0 != httpConnection.get(jobUri, httpCredentials, true))
			return 1;
		
		// Parse JSON from text to object
		JSONObject jo = new JSONObject(httpConnection.getResponseContent());
		// Get the item with the steps info
		JSONObject a4dp = jo.getJSONObject("a4dp:return").getJSONObject("JOB_LOGTYPES");
		// Get steps
		JSONArray ja = a4dp.getJSONArray("a4dp:row");
		
		// Read steps
		for (int i = 0; i < ja.length(); i++) {
			// JSON Steps array info
			JSONArray steps = ja.getJSONObject(i).getJSONArray("a4dp:col");
			// Job Step item
			JobStep js = new JobStep();
			// Temporary variable for StepName creation
			String stepName = "";
			// Read all step info
			for (int s = 0; s < steps.length(); s++) {
				// Get step info if is present
				if("URI".equals(steps.getJSONObject(s).get("name"))) {
					// Set step URI
					js.setUrl(baseUri + steps.getJSONObject(s).get("content").toString() + "&format=std");
					
					// Get step info
					if(0 == httpConnection.get(js.getUrl(), httpCredentials, true)) {
						// Get output
						String jobLog = httpConnection.getResponseContent();
						// Remove header and footer
						jobLog = StringUtils.substringBetween(jobLog, "<div id=\"output\">", "</div>");
						// Unescpae HTML
						jobLog = StringEscapeUtils.unescapeHtml4(jobLog);
						// Output is defined in a single row
						List<String> jobLogList = new ArrayList<String>();
						// Add single row to the list
						jobLogList.add(jobLog);
						// Set step log
						js.setLog(jobLogList);	
					} else {
						// Warning
						logger.warn("Unable to get step info from URI (" + js.getUrl() + ")");
					}
				}
				
				// Get step-name if it's present
				if("STEPNAME".equals(steps.getJSONObject(s).get("name")) & (steps.getJSONObject(s).has("content"))) {
					stepName += steps.getJSONObject(s).get("content").toString();
				}
				// Get proc-step if it's present
				if("PROCSTEP".equals(steps.getJSONObject(s).get("name")) & (steps.getJSONObject(s).has("content"))) {
					stepName += "." + steps.getJSONObject(s).get("content").toString();
				}

			}
			// Set complete step-name
			js.setName(stepName);
			
			// Add step info to list
			jobSteps.add(js);
		}

		// Return step list
		return 0;
	}

}

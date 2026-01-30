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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import elius.virtualoperator.task.job.Job;
import elius.virtualoperator.task.job.JobAttributes;
import elius.virtualoperator.task.job.JobStep;
import elius.webapp.framework.properties.PropertiesManager;
import elius.webapp.framework.properties.PropertiesManagerFactory;
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
	
	// Trust certificate
	private boolean trustAll;
	
	// Select Path
	private String selectPath;
	
	// HTTP Connection 
	private HttpConnection httpConnection;
	
	// HTTP Credentials
	private SecretCredentials httpCredentials;
	
	// Fetch all
	private String fetchAll;
	
	// Step lines
	List<String> jobLogList;
	
	
	/**
	 * Constructor
	 * @param job Job to fetch
	 */
	public JobLogBetaSystems(Job job) {
		super(job);
		
		// Application properties
		appProperties = PropertiesManagerFactory.getInstance(JobAttributes.DEFAULT_JOB_PROPERTIES_FILE);
		
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
				
		// Verify that properties are loaded
		if(!appProperties.isLoaded())
			return -1;
		
		// Base URI
		baseUri = appProperties.get(JobAttributes.PROP_TASK_JOB_LOG_BETASYSTEMS_BASE_URI);
		
		// Trust certificate
		trustAll = ("Y".equalsIgnoreCase(appProperties.get(JobAttributes.PROP_TASK_JOB_LOG_BETASYSTEMS_BASE_URI_TRUST, JobAttributes.DEFAULT_TASK_JOB_LOG_BETASYSTEMS_BASE_URI_TRUST))) ? true : false;
		
		// Select Path
		selectPath = appProperties.get(JobAttributes.PROP_TASK_JOB_LOG_BETASYSTEMS_SELECT_PATH);
		
		// UserId
		httpCredentials.setUserId(appProperties.get(JobAttributes.PROP_TASK_JOB_LOG_BETASYSTEMS_USERID));
		
		// Password
		httpCredentials.setPassword(appProperties.get(JobAttributes.PROP_TASK_JOB_LOG_BETASYSTEMS_PASSWORD));
		
		// Global Fetch
		fetchAll = appProperties.get(JobAttributes.PROP_TASK_JOB_LOG_BETASYSTEMS_GLOBAL_FETCH, JobAttributes.DEFAULT_TASK_JOB_LOG_BETASYSTEMS_GLOBAL_FETCH);
		
		
		
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
			// Return error
			return 1;
		}
		
		// Get Steps
		if(0 != getJobStepsInfo(jobUri)) {
			logger.error("Error getting step info for " + 
					job.getName() + "." + 
					job.getId() + "." +
					job.getRc() + "." +
					job.getStarted() + "." +
					job.getEnded());
			
			// Return error
			return 2;
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

		// Convert timestamps
		String dateFrom = new SimpleDateFormat("yyyy-MM-dd").format(job.getStarted());
		//String timeFrom = new SimpleDateFormat("HH:mm:ss").format(job.getStarted());
		String dateTo = new SimpleDateFormat("yyyy-MM-dd").format(job.getEnded());
		//String timeTo = new SimpleDateFormat("HH:mm:ss").format(job.getEnded());
		
		// Prepare URI
		String uri = baseUri + selectPath;
		
		try {
		    uri += "?p:mode=save"
				+ "&p:fromdate=" + URLEncoder.encode(dateFrom, StandardCharsets.UTF_8.name())
//				+ "&p:fromtime=" + URLEncoder.encode(timeFrom, StandardCharsets.UTF_8.name())
				+ "&p:fromtime="
				+ "&p:todate=" + URLEncoder.encode(dateTo, StandardCharsets.UTF_8.name())
//				+ "&p:totime=" + URLEncoder.encode(timeTo, StandardCharsets.UTF_8.name())
				+ "&p:totime="
				+ "&p:jobname=" + URLEncoder.encode(job.getName(), StandardCharsets.UTF_8.name())
				+ "&p:jobid=" + URLEncoder.encode(job.getId(), StandardCharsets.UTF_8.name())
				+ "&p:joberrt="
				+ "&p:jobqueue=ALL"
				+ "&p:jobnetid="
				+ "&p:ljobname="
				+ "&p:hostname="
				+ "&format=json";
		} catch (UnsupportedEncodingException e) {
			logger.error("Errror encoding jobName(" + job.getName() + ") jobId(" + job.getId() + ") for http call");
		    logger.error(e.getMessage());
		    return "";
		}

		
		// Get job info
		if(0 != httpConnection.get(uri, httpCredentials, trustAll))
			return jobUri;
		
		// Parse JSON from text to object
		JSONObject jo = new JSONObject(httpConnection.getResponseContent());
		
		
		try {
		
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
		} catch (JSONException e) {
			// Return error 
			return "";
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
		if(0 != httpConnection.get(jobUri, httpCredentials, trustAll))
			return 1;
		
		try {
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
				String jlistkey = "";
				String procstep = "";
				String ddname = "";
				String uri = "";
				boolean fetchStep = false;
				
				// Read all step info
				for (int s = 0; s < steps.length(); s++) {
		
					if("JLISTKEY".equals(steps.getJSONObject(s).get("name")) & (steps.getJSONObject(s).has("content")))
						jlistkey = steps.getJSONObject(s).get("content").toString();
					if("STEPNAME".equals(steps.getJSONObject(s).get("name")) & (steps.getJSONObject(s).has("content")))
						stepName = steps.getJSONObject(s).get("content").toString();
					if("PROCSTEP".equals(steps.getJSONObject(s).get("name")) & (steps.getJSONObject(s).has("content")))						
						procstep = steps.getJSONObject(s).get("content").toString();
					if("DDNAME".equals(steps.getJSONObject(s).get("name")) & (steps.getJSONObject(s).has("content")))
						ddname = steps.getJSONObject(s).get("content").toString();
					if("URI".equals(steps.getJSONObject(s).get("name")) & (steps.getJSONObject(s).has("content")))
						uri = steps.getJSONObject(s).get("content").toString();
					
				}
				
				// Select steps to fetch
				if(("Y".equalsIgnoreCase(fetchAll)) && ("ALL".equals(jlistkey)))
					fetchStep = true;
				else if((!"Y".equalsIgnoreCase(fetchAll)) && (!"ALL".equals(jlistkey)))
					fetchStep = true;
				
				if(fetchStep) {
					// Set step URI
					js.setUrl(baseUri + uri + "&format=json");
					
					// Get step info
					if(0 == httpConnection.get(js.getUrl(), httpCredentials, trustAll)) {
						
						// Output is defined in a single row
						jobLogList = new ArrayList<String>();
						
						// Parse JSON from text to object
						JSONObject joStep = new JSONObject(httpConnection.getResponseContent());
						
						// JSON get pages
						Object oPages = joStep.getJSONObject("a4dp:return").getJSONObject("RESULT_TABLE").get("a4dp:page");
						
						// Parse pages
						parsePages(oPages);
						
						// Set step log
						js.setLog(jobLogList);	
						
						if("Y".equalsIgnoreCase(fetchAll)) {
							stepName = "ALL";
						} else {
							// Set complete step name
							if(!"".equals(stepName))
								stepName += ".";
							stepName += procstep;
							if(!"".equals(stepName))
								stepName += ".";
							stepName += ddname;
						}
						
						// Set complete step-name
						js.setName(stepName);
						
						// Add step info to list
						jobSteps.add(js);						
						
					} else {
						// Warning
						logger.warn("Unable to get step info from URI (" + js.getUrl() + ")");
					}
				}
					
			}
		} catch (JSONException e) {
			logger.error(e);
			// Return error 
			return 1;
		}

		// Return step list
		return 0;
	}

	
	/**
	 * Parse pages from a4dp JSON Object
	 * @param oPages Pages object
	 * @throws JSONException
	 */
	private void parsePages(Object oPages) throws JSONException {
		
		// Single page or multiple pages
		if(oPages instanceof JSONObject) {
			
			// Convert JSON object
			JSONObject page = (JSONObject)oPages;

			// Child check
			if(page.has("a4dp:line")) {
				// JSON get lines
				Object oLines = page.get("a4dp:line");
				
				// Parse lines
				parseLines(oLines);				
			}
			
			
		} else if(oPages instanceof JSONArray) {
			
			// Convert object to Array
			JSONArray pages = (JSONArray)oPages;
			
			// Read pages
			for (int p = 0; p < pages.length(); p++) {
				
				// Child check
				if(pages.getJSONObject(p).has("a4dp:line")) {
					// JSON get lines
					Object oLines = pages.getJSONObject(p).get("a4dp:line");
					
					// Parse lines
					parseLines(oLines);
				}				
			}
			
		}
	}
	
	
	/**
	 * Parse lines from a4dp JSON object
	 * @param oLines Lines object
	 * @throws JSONException
	 */
	private void parseLines(Object oLines)  throws JSONException {
		
		// Single page or multiple lines
		if(oLines instanceof JSONObject) {

			// Convert JSON object
			JSONObject line = (JSONObject)oLines;			

			if(line.has("content"))
				jobLogList.add(line.get("content").toString());
			
		} else if(oLines instanceof JSONArray) {
			
			// Convert object to Array
			JSONArray lines = (JSONArray)oLines;
			
			// Read lines
			for (int l = 0; l < lines.length(); l++) {
				// Blank lines are note saved
				if(lines.getJSONObject(l).has("content"))
					jobLogList.add(lines.getJSONObject(l).get("content").toString());
			}
		}

	}
}

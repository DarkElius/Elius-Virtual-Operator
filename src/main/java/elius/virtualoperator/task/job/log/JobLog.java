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

import java.util.ArrayList;
import java.util.List;

import elius.virtualoperator.task.job.Job;
import elius.virtualoperator.task.job.JobStep;

public abstract class JobLog {
	
	// Job to be evaluated with the flow
	protected Job job;
	
	// Job steps
	protected List<JobStep> jobSteps;
	
	
	/**
	 * Constructor
	 * @param job Job
	 */
	public JobLog(Job job) {
		
		// Set job
		this.job = job;
		
		// Define step list
		jobSteps = new ArrayList<JobStep>();
		
	}
	
	
	/**
	 * Initialize log engine
	 * @return 0 OK, otherwise error
	 */
	public abstract int initialize();
	
	
	/**
	 * Fetch log from source to job steps
	 * @return 0 OK, otherwise error
	 */
	public abstract int fetch();
	
	
	/**
	 * Get the steps of the selected jobs
	 * @return List of job steps
	 */
	public List<JobStep> getSteps() {
		return jobSteps;
	}
	
}

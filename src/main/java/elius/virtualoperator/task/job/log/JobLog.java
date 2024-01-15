

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

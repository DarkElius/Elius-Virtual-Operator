package elius.virtualoperator.task.job;

public class JobAttributes {
	
	// Default - Application properties file
	public static final String DEFAULT_JOB_PROPERTIES_FILE = "evo-job.properties";
	
	// Default - Job script, path where find logs
	public static final String DEFAULT_TASK_JOB_SCRIPT_LOG_PATH = "/";
	
	// Default - Job script filename mask
	public static final String DEFAULT_TASK_JOB_SCRIPT_LOG_MASK = "<name>.<id>.log";
	
	// Default -  Job script, log max size (bytes): used to prevent overloading when file is too big
	public static final long DEFAULT_TASK_JOB_SCRIPT_LOG_MAXSIZE = 50000000;
	
	
	// Properties - Job script, path where find logs
	public static final String PROP_TASK_JOB_SCRIPT_LOG_PATH = "task.job.script.log.path";
	
	// Properties - Job script filename mask
	public static final String PROP_TASK_JOB_SCRIPT_LOG_MASK = "task.job.script.log.mask";
	
	// Properties -  Job script, log max size (bytes): used to prevent overloading when file is too big
	public static final String PROP_TASK_JOB_SCRIPT_LOG_MAXSIZE = "task.job.script.log.maxSize";
}

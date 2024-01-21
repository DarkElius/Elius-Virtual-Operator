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

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

package elius.virtualoperator;

public class VirtualOperatorAttributes {
	
	// Elius Virtual Operator properties file
	public static final String EVO_PROPERTIES_FILE = "evo.properties";
	
	
	// Default - Number of executor service thread
	public static final int DEFAULT_EXECUTOR_SERVICE_THREADS = 2;
	
	// Default - Executor service schedule status
	public static final int DEFAULT_EXECUTOR_SERVICE_SCHEDULE_ACTIVE = 15;
	
	// Default - Number of task to own from queue
	public static final int DEFAULT_TASK_SUBMITTER_TASK_TO_OWN = 4;
		
	// Default - Lock Manager application name
	public static final String DEFAULT_LOCK_MANAGER_APPLICATION = "EVO";
	
	// Default - Lock Manager expire in seconds
	public static final int DEFAULT_LOCK_MANAGER_EXPIRE = 120;

	// Default - Lock Manager max retry to get lock
	public static final int DEFAULT_LOCK_MANAGER_MAX_RETRY = 3;

	// Default - Lock Manager max retry sleep time in seconds to get lock
	public static final int DEFAULT_LOCK_MANAGER_MAX_RETRY_SLEEP = 2;


	
	// Database
	public static final String DATASOURCE_EVO = "jdbc/evodb";
	
	
	
	// Properties - Database initialization on startup (cold start) (y/n) , default is n
	public static final String PROP_DATABASE_INIT_ON_BOOT = "database.initOnBoot";

	// Properties - Executor service schedule status
	public static final String PROP_EXECUTOR_SERVICE_SCHEDULE_ACTIVE = "executor.service.schedule.active";
	
	// Properties - Number of executor service thread
	public static final String PROP_EXECUTOR_SERVICE_THREADS = "executor.service.threads";
	
	// Properties - Polling time interval
	public static final String PROP_EXECUTOR_SERVICE_TIME_INTERVAL = "executor.service.timeInterval";
	
	// Properties - Number of task to own from queue
	public static final String PROP_TASK_SUBMITTER_TASK_TO_OWN = "task.submitter.taskToOwn";
	
	// Properties - Lock Manager application
	public static final String PROP_LOCK_MANAGER_APPLICATION = "lock.manager.application";
	
	// Properties - Lock Manager expire in seconds
	public static final String PROP_LOCK_MANAGER_EXPIRE = "lock.manager.expire";

	// Properties - Lock Manager max retry to get lock
	public static final String PROP_LOCK_MANAGER_MAX_RETRY = "lock.manager.maxRetry";

	// Properties - Lock Manager max retry sleep time in seconds to get lock
	public static final String PROP_LOCK_MANAGER_MAX_RETRY_SLEEP = "lock.manager.maxRetry.sleep";
}

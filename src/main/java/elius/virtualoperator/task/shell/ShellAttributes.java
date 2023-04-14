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

package elius.virtualoperator.task.shell;

public class ShellAttributes {
	
	// Default - Application properties file
	public static final String DEFAULT_SHELL_PROPERTIES_FILE = "evo-shell.properties";
	
	
	// Properties - Path where scripts stored
	public static final String PROP_TASK_SHELL_SCRIPTS_PATH = "task.shell.scripts.path";
	
	// Properties - Path where scripts are executed (working directory)
	public static final String PROP_TASK_SHELL_WORK_PATH = "task.shell.work.path";	
}

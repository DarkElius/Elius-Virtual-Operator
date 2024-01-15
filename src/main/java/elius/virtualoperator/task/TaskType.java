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

package elius.virtualoperator.task;

public enum TaskType {

	UNKNOWN(0, "Unknown"), 
	DEBUG(1, "Debug"), 
	SHELL(2, "Shell"),
	JOB_SCRIPT(3, "Job Script");

	// Type name
	private final String name;
	// Type id
	private final int id;

	
	/**
	 * Constructor
	 * @param id Type id
	 * @param name Type name
	 */
	TaskType(int id, String name) {
		this.name = name;
		this.id = id;
	}

	
	/**
	 * Get type name
	 * @return Type name
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * Get type id
	 * @return Type id
	 */
	public int getId() {
		return id;
	}

	
	/**
	 * Get type by id
	 * @param id Type id
	 * @return Type
	 */
	public static TaskType getById(int id) {
	    for(TaskType e : values()) {
	        if(e.id == id) return e;
	    }
	    return UNKNOWN;
	}
};

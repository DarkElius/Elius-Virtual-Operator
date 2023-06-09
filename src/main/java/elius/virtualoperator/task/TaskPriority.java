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

public enum TaskPriority {

	UNDEFINED(0, "Low"),
	LOW(1, "Low"), 
	MEDIUM(2, "Medium"), 
	HIGH(3, "High");

	// Priority name
	private final String name;
	// Priority it
	private final int id;

	
	/**
	 * Constructor
	 * @param id Priority id
	 * @param name Priority name
	 */
	TaskPriority(int id, String name) {
		this.name = name;
		this.id = id;
	}

	
	/**
	 * Get priority name
	 * @return Priority name
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * Get priority id
	 * @return Priority id
	 */
	public int getId() {
		return id;
	}
	
	
	/**
	 * Get priority by id
	 * @param id Priority id
	 * @return Priority
	 */
	public static TaskPriority getById(int id) {
	    for(TaskPriority e : values()) {
	        if(e.id == id) return e;
	    }
	    return UNDEFINED;
	}
};

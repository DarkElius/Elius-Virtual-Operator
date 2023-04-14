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

public enum TaskStatus {

	UNKNOWN(0, "Unknown"), 
	NEW(1, "New"), 
	OWNED(2, "Owned"),
	STARTED(3, "Started"),
	RUNNING(4, "Running"),
	ENDED(5, "Ended"),
	ERROR(6, "Error"),
	WARNING(7, "Warning");

	// Status name
	private final String name;
	// Status id
	private final int id;

	
	/**
	 * Constructor
	 * @param id Status id
	 * @param name Status name
	 */
	TaskStatus(int id, String name) {
		this.name = name;
		this.id = id;
	}

	
	/**
	 * Get status name
	 * @return Status name
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * Get status id
	 * @return Status id
	 */
	public int getId() {
		return id;
	}

	
	/**
	 * Get status by id
	 * @param id Status id
	 * @return Status
	 */
	public static TaskStatus getById(int id) {
	    for(TaskStatus e : values()) {
	        if(e.id == id) return e;
	    }
	    return UNKNOWN;
	}
}

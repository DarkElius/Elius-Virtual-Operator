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

import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Task {
	// Universal unique identifier
	private UUID uuid;
	// Task time-stamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp date;
	// Status
	private TaskStatus status;
	// Type 
	private TaskType type;
	// Priority
	private TaskPriority priority;
	// Details of the request
	private String details;
	// Result of the execution
	private String result;
	// Who request the task
	private String requester;
	// Who manage/execute the task
	private String owner;

	
	/**
	 * Constructor
	 */
	public Task() {
		// Generate a random id
		this.uuid = UUID.randomUUID();
		// Set default time-stamp
		date = new Timestamp(0);
	}

	
	/**
	 * Get universal unique identifier
	 * @return Universal unique identifier
	 */
	public UUID getUuid() {
		return uuid;
	}

	
	/**
	 * Set universal unique identifier
	 * @param uuid Universal unique identifier
	 */
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	
	/**
	 * Get task time-stamp
	 * @return Task time-stamp
	 */
	public Timestamp getDate() {
		return date;
	}

	
	/**
	 * Set task time-stamp
	 * @param date Task time-stamp
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}

	
	/**
	 * Get task status
	 * @return Task status
	 */
	public TaskStatus getStatus() {
		return status;
	}

	
	/**
	 * Set task status
	 * @param status Task status
	 */
	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	
	/**
	 * Get task type
	 * @return Task type
	 */
	public TaskType getType() {
		return type;
	}

	
	/**
	 * Set task type
	 * @param type Task type
	 */
	public void setType(TaskType type) {
		this.type = type;
	}

	
	/**
	 * Get task priority
	 * @return Task priority
	 */
	public TaskPriority getPriority() {
		return priority;
	}

	
	/**
	 * Set task priority
	 * @param priority Task priority
	 */
	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}

	
	/**
	 * Get task details
	 * @return Task details
	 */
	public String getDetails() {
		return details;
	}

	
	/**
	 * Set task details
	 * @param details Task details
	 */
	public void setDetails(String details) {
		this.details = details;
	}

	
	/**
	 * Get task result
	 * @return Task result
	 */
	public String getResult() {
		return result;
	}

	
	/**
	 * Set task result
	 * @param result Task result
	 */
	public void setResult(String result) {
		this.result = result;
	}

	
	/**
	 * Get task requester
	 * @return Task requester
	 */
	public String getRequester() {
		return requester;
	}

	
	/**
	 * Set task requester
	 * @param requester
	 */
	public void setRequester(String requester) {
		this.requester = requester;
	}	

	
	/**
	 * Get task owner
	 * @return Task owner
	 */
	public String getOwner() {
		return owner;
	}

	
	/**
	 * Set task owner
	 * @param owner Task owner
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}	
	
}

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

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Job {

	// Name
	@JsonProperty("name")
	private String name;
	
	// Id
	@JsonProperty("id")
	private String id;
	
	// Start timestamp
	@JsonProperty("started")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp started;
	
	// End timestamp
	@JsonProperty("ended")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp ended;
	
	// Return code
	@JsonProperty("rc")
	private String rc;
	
	// URL where find the job
	@JsonProperty("url")
	private String url;
	
	
	/**
	 * Get job name
	 * @return Job name
	 */
	public String getName() {
		return name;
	}
	

	/**
	 * Set job name
	 * @param name Name
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * Get job id
	 * @return Job id
	 */
	public String getId() {
		return id;
	}
	

	/**
	 * Set job id
	 * @param id Job id
	 */
	public void setId(String id) {
		this.id = id;
	}

	
	/**
	 * Get job start timestamp
	 * @return Job start timestamp
	 */
	public Timestamp getStarted() {
		return started;
	}
	

	/**
	 * Set job start timestamp
	 * @param started Job start timestamp
	 */
	public void setStarted(Timestamp started) {
		this.started = started;
	}

	
	/**
	 * Get job end timestamp
	 * @return Job end timestamp
	 */
	public Timestamp getEnded() {
		return ended;
	}
	

	/**
	 * Set job end timestamp
	 * @param ended Job end timestamp
	 */
	public void setEnded(Timestamp ended) {
		this.ended = ended;
	}

	
	/**
	 * Get job return code
	 * @return Job return code
	 */
	public String getRc() {
		return rc;
	}
	

	/**
	 * Set job return code
	 * @param rc Job return code
	 */
	public void setRc(String rc) {
		this.rc = rc;
	}

	
	/**
	 * Get job URL
	 * @return Job URL
	 */
	public String getUrl() {
		return url;
	}
	

	/**
	 * Set job URL
	 * @param url Job URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}

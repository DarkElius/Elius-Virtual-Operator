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

import java.util.List;

public class JobStep {
	
	// Name
	private String name;
	
	// Return code
	private String rc;
	
	// URL where find the job
	private String url;	
	
	// Log
	private List<String> log;
	
	// Number of log lines
	private int nLines;
	
	
	
	
	/**
	 * Get step name
	 * @return Step name
	 */
	public String getName() {
		return name;
	}
	

	/**
	 * Set step name
	 * @param name Name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * Get step return code
	 * @return Step return code
	 */
	public String getRc() {
		return rc;
	}
	

	/**
	 * Set step return code
	 * @param rc Step return code
	 */
	public void setRc(String rc) {
		this.rc = rc;
	}

	
	/**
	 * Get step URL
	 * @return Step URL
	 */
	public String getUrl() {
		return url;
	}
	

	/**
	 * Set step URL
	 * @param url Step URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}


	/**
	 * Get step log
	 * @return log Log
	 */
	public List<String> getLog() {
		return log;
	}


	/**
	 * Set step log
	 * @param log Step log
	 */
	public void setLog(List <String> log) {
		this.log = log;
	}


	/**
	 * Get step log lines number
	 * @return Step log lines number
	 */
	public int getnLines() {
		return nLines;
	}


	/**
	 * Set step log lines number
	 * @param nLines Step log lines number
	 */
	public void setnLines(int nLines) {
		this.nLines = nLines;
	}
	
	
	
}

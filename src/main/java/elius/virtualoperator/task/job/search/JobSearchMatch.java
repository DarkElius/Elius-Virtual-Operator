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


package elius.virtualoperator.task.job.search;

public class JobSearchMatch {

	// Search regEx
	private String regEx;
	
	// Message that match with the search
	private String message;

	// Free label where the message was found
	private String label;
	
	
	/**
	 * Get regular expression
	 * @return Regular expression
	 */
	public String getRegEx() {
		return regEx;
	}

	
	/**
	 * Set regular expression
	 * @param regEx Regular expression
	 */
	public void setRegEx(String regEx) {
		this.regEx = regEx;
	}

	
	/**
	 * Get message		
	 * @return Message that match with the regular expression
	 */
	public String getMessage() {
		return message;
	}

	
	/**
	 * Set the message that match with the regular expression
	 * @param message Message
	 */
	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * Get label
	 * @return Label
	 */
	public String getLabel() {
		return label;
	}


	/**
	 * Set label
	 * @param label Label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	
}

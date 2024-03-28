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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import elius.virtualoperator.VirtualOperatorID;
import elius.virtualoperator.task.debug.DebugProcess;
import elius.virtualoperator.task.shell.ShellProcess;
import elius.virtualoperator.task.job.JobProcessBetaSystems;
import elius.virtualoperator.task.job.JobProcessScript;
import elius.virtualoperator.task.job.JobType;

public class TaskExecutor implements Runnable {

	// Get logger
	private static Logger logger = LogManager.getLogger(TaskExecutor.class);
	
	// Task Database
	private TaskDatabase dbTask;
	
	// Task pointer
	Task task;
	
	
	/**
	 * Constructor
	 * @param t Task to be executed
	 */
	public TaskExecutor(Task t) {
		// Set task
		this.task = t;
		
		// Task Database instance
		dbTask = new TaskDatabase();
	}
	
	
	@Override
	public void run() {	
		// Log task information
		logger.info("Start - Task Id(" + task.getUuid() + ") Status(" + task.getStatus().getName() + ") Type(" + task.getType().getName() + ") Priority(" + task.getPriority().getName() + ") Details(" + task.getDetails() + ")");

		// Update task owner with the real thread executor
		dbTask.updateOwner(task.getUuid(), VirtualOperatorID.get());
		
		// Start correct process
		switch(task.getType()) {
			case DEBUG:
				// Update task status
				dbTask.updateStatus(task.getUuid(), TaskStatus.RUNNING);

				// Create process instance
				DebugProcess debugProcess = new DebugProcess();

				// Execute task
				debugProcess.execute(task);
				
				// Update message
				dbTask.updateResult(task.getUuid(), debugProcess.getTaskStatusResult(), debugProcess.getProcessResult());
				
				// Update task info for log
				task.setStatus(debugProcess.getTaskStatusResult());
				task.setResult(debugProcess.getProcessResult());


				break;

			case SHELL:
				// Update task status
				dbTask.updateStatus(task.getUuid(), TaskStatus.RUNNING);

				// Create shell instance
				ShellProcess shellProcess = new ShellProcess();

				// Execute task
				shellProcess.execute(task);
				
				// Update message
				dbTask.updateResult(task.getUuid(), shellProcess.getTaskStatusResult(), shellProcess.getProcessResult());
						
				// Update task info for log
				task.setStatus(shellProcess.getTaskStatusResult());
				task.setResult(shellProcess.getProcessResult());

				break;	
				
			case JOB_SCRIPT:
				// Update task status
				dbTask.updateStatus(task.getUuid(), TaskStatus.RUNNING);

				// Create shell instance
				JobProcessScript jobProcess = new JobProcessScript();

				// Execute task
				jobProcess.execute(task);
				
				// Update message
				dbTask.updateResult(task.getUuid(), jobProcess.getTaskStatusResult(), jobProcess.getProcessResult());
				
				// Update task info for log
				task.setStatus(jobProcess.getTaskStatusResult());
				task.setResult(jobProcess.getProcessResult());
						
				break;	
				
			case JOB_BETASYSTEMS_OPEN:
				// Update task status
				dbTask.updateStatus(task.getUuid(), TaskStatus.RUNNING);

				// Create shell instance
				JobProcessBetaSystems jobProcessBetaSystemsOpen = new JobProcessBetaSystems(JobType.OPEN);

				// Execute task
				jobProcessBetaSystemsOpen.execute(task);
				
				// Update message
				dbTask.updateResult(task.getUuid(), jobProcessBetaSystemsOpen.getTaskStatusResult(), jobProcessBetaSystemsOpen.getProcessResult());
				
				// Update task info for log
				task.setStatus(jobProcessBetaSystemsOpen.getTaskStatusResult());
				task.setResult(jobProcessBetaSystemsOpen.getProcessResult());
						
				break;		
			
			case JOB_BETASYSTEMS_MAINFRAME:
				// Update task status
				dbTask.updateStatus(task.getUuid(), TaskStatus.RUNNING);

				// Create shell instance
				JobProcessBetaSystems jobProcessBetaSystemsMainframe = new JobProcessBetaSystems(JobType.MAINFRAME);

				// Execute task
				jobProcessBetaSystemsMainframe.execute(task);
				
				// Update message
				dbTask.updateResult(task.getUuid(), jobProcessBetaSystemsMainframe.getTaskStatusResult(), jobProcessBetaSystemsMainframe.getProcessResult());
				
				// Update task info for log
				task.setStatus(jobProcessBetaSystemsMainframe.getTaskStatusResult());
				task.setResult(jobProcessBetaSystemsMainframe.getProcessResult());
						
				break;		
			
			default:
				// Log error message for missing process
				logger.error("Undefined process for task type " + task.getType().getName());
				
				// Update task status
				dbTask.updateStatus(task.getUuid(), TaskStatus.ERROR);
		}

		// Log end task
		logger.debug("End - Task Id(" + task.getUuid() + ") Status(" + task.getStatus().getName() + ") Result(" + task.getResult() + ") Type(" + task.getType().getName() + ") Priority(" + task.getPriority().getName() + ") Details(" + task.getDetails() + ")");

	}

}

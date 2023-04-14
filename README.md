Elius Virtual Operator
-----



Description
-----
The virtual operator is a software that can execute different tasks independently.

By default it has only two tasks, but the user can implement their own class to execute particular actions.

Task types:

 * **DEBUG**, used mainly for debugging phase
 
 * **SHELL**, used to execute scripts inside a directory set in the evo-shell property file
 
Requests can be submitted and monitored using http interface.

The software is scalable and can be installed on multiple nodes to better manage high workloads.

This application is powered by Elius WebApp Framework.




Components
-----
When the project is compiled it'll generate a **war** file

The **resources** directory can be placed every where in your file system.

It contains:
 * **ewa.properties**, the framework properties file
 
 
 * **log4j2.xml**, the Apache Log4j2 log configuration file
 
 
 * **evo.properties**, the virtual operator generic properties file

 
 * **evo-shell.properties**, the shell type task properties file



Compilation
-----
To compile this project you need to compile and install the Elius WebApp Framework on your local Maven repository.

When the Elius WebApp Framework will be stable it'll be released on official Maven repository.




Run
-----
This project must be deployed on an application server: I'm currently using Apache Tomcat 10.0.6.


**Parameters**

There are two parameters that muset be set at jvm level:
 * **ewa.path**, set the resource directory complete path
       *i.e.: -Dewa.path=/my-path-name/resources*
       

 * **log4j.configurationFile**, set the Apache Log4j2 xml complete path
       *i.e.: -Dlog4j.configurationFile=/my-path-name/resources/log4j2.xml*



**Database connection configuration**

This an example of the jdni resource configuration for a in memory database using Apache Derby.

*<Resource driverClassName="org.apache.derby.iapi.jdbc.AutoloadedDriver"*

 *maxIdle="2" maxTotal="10" maxWaitMillis="5"*
  
 *name="jdbc/evodb"*
  
 *type="javax.sql.DataSource"*
  
 *url="jdbc:derby:memory:evodb;create=true"*
  
 *username="" password=""*

*/>*




API
-----

**Tasks**

* Add a DEBUG task
  
  *curl -X POST -d '{"status": 1, "type": 1, "priority": 1, "details": "nothing", "requester": "generic", "owner": "foo"}' http://localhost:8080/EliusVirtualOperator/api/evo/task/add  --header "Content-Type:application/json"*
  
* Add a SHELL task

  *curl -X POST -d '{"status": 1, "type": 2, "priority": 1, "details": "test.sh", "requester": "generic", "owner": "foo"}' http://localhost:8080/EliusVirtualOperator/api/evo/task/add  --header "Content-Type:application/json"*
  
  *test.sh* must be present in the directory specified in the *evo-shell.txt* property file under label *task.shell.scripts.path*

* Get all tasks

  *curl http://localhost:8080/EliusVirtualOperator/api/evo/task/get*
  
**Management**
  
* Get all locks
  
  *http://localhost:8080/EliusVirtualOperator/api/evo/lock/get*


**Authentications**

To enable the authentication modify the following line in the WEB-INF/web.xml

From
  */api/evo-DISABLED/*


To
  */api/evo/*



Create your own task
-----
To create your own task please follow this steps:

 * Add your task type to *TaskType.java*
 
   *GAME(3, "Game");*
   
 * Create your package
 
   *elius.virtualoperator.task.game*
   
 * Implements the *TaskProcess* class in the previous package
 
   *public class GameProcess implements TaskProcess*
   
 * Modify *run* method inside *TaskExecutor* class
 
   *case SHELL:*

   *dbTask.updateStatus(task.getUuid(), TaskStatus.RUNNING);*

   *GameProcess gameProcess = new GameProcess();*

   *gameProcess.execute(task);*

   *dbTask.updateResult(task.getUuid(), gameProcess.getTaskStatusResult(), gameProcess.getProcessResult());

   *break;*


Road Map
-----
These are more or less the steps I want to implement in the near future

 * GUI
 
 * Create a virtual operator option to force the node to work only on specify a task type 
 
 * Evaluate a System Exit method for task extension



### License

Apache 2.0 - <https://www.apache.org/licenses/LICENSE-2.0>



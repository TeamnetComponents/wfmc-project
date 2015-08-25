#**WFMC Project**

##1. Overview

The **WFMC Project** aimes to provide a unified interface for interoperability with different workflow management systems.


The vision of this project is to:
    1. cover all technical components (front-end and backend) for delivering an application 
    2. that is embedable in the TDI platform
    3. with support on different workflow engines.
  
The target architecture of the project consist of the following components:
    - app-wfmc module - application modules (including frontend) to offer the following screens (most commons screens used in a workflow system):
            - list/search active process instances 
            - list of processes eligible for claiming for an user and claim a process instance
            - list/search process history
    - **wfmc-api** module - this module provides a Java binding containg a base domain model and operations as defined in the WfMC standard.        
    - **wfmc-audit** module - audit wfmc API calls into database using spring AOP.  
    - **wfmc-commons** and **wfmc-commons-impl** - contain convenient methods (Apis) that are indended to be used when implementing support on different wfmc engined (different providers)
    - **wfmc-impl-elo** - this is module that is implementing the WfMC interfaces for ELO workflow engine. This module provide mediation logic between WFMC model+operations and ELO model+operations
    - wfmc-server - this module is a **ready-to-use WAR application** that can be deployed on yoour favourite server and will expose the WFMC interface. 
    - wfmc-client - this module is a **client library** that is implementing the wfmc interface and it is used to call remotely a wfmc implementation()
    - **wfmc-test** - this is a module containing basic tests and usage examples when calling the wfmc interface
    
Currently the modules in "bold" are implemented in the project.

Links:

* [WfMC Wiki] (https://en.wikipedia.org/wiki/Workflow_Management_Coalition)
* [WfMC(Workflow Management Coalition)](http://www.wfmc.org/)

##2. Download
You can download the release files from the <a href="http://tni-hq-artifactory/simple/tn-components/org/wfmc/" target="_blank">artifactory repository</a>.

##4 Releases

There are 3 releases on this project. For detailed information please consult each release version material.

* [Release v1.0] (http://git-components.teamnet.ro/blob/components%2Fjava%2Fwfmc-project.git/master/README%20v1.0.md)
* [Release v2.0] (http://git-components.teamnet.ro/blob/components%2Fjava%2Fwfmc-project.git/master/README%20v2.0.md)
* [Release v3.0] (http://git-components.teamnet.ro/blob/components%2Fjava%2Fwfmc-project.git/master/README%20v%203.0.md)
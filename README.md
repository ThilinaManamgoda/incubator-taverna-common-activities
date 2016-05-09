<!--
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
-->

# Apache Taverna Common Activities

Common Activities to be invoked as part of 
[Apache Taverna](http://taverna.incubator.apache.org/) workflows.

Note that this module relies on other
[Apache Taverna modules](http://taverna.incubator.apache.org/code) for
the actual workflow execution.


## License

(c) 2007-2014 University of Manchester

(c) 2014-2016 Apache Software Foundation

This product includes software developed at The
[Apache Software Foundation](http://www.apache.org/).

Licensed under the
[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0), see the file
[LICENSE](LICENSE) for details.

The file [NOTICE](NOTICE) contains any additional attributions and
details about embedded third-party libraries and source code.


# Contribute

Please subscribe to and contact the
[dev@taverna](http://taverna.incubator.apache.org/community/lists#dev mailing list)
mailing list for any questions, suggestions and discussions about
Apache Taverna.

Bugs and planned features are tracked in the Jira
[Issue tracker](https://issues.apache.org/jira/browse/TAVERNA/component/12326811)
under the `TAVERNA` component _Taverna Common Activities._ Feel free
to add an issue!

To suggest changes to this source code, feel free to raise a
[GitHub pull request](https://github.com/apache/incubator-taverna-common-activities/pulls).
Any contributions received are assumed to be covered by the [Apache License
2.0](https://www.apache.org/licenses/LICENSE-2.0). We might ask you
to sign a [Contributor License Agreement](https://www.apache.org/licenses/#clas)
before accepting a larger contribution.

## Disclaimer

Apache Taverna is an effort undergoing incubation at the
[Apache Software Foundation (ASF)](http://www.apache.org/),
sponsored by the [Apache Incubator PMC](http://incubator.apache.org/).

[Incubation](http://incubator.apache.org/incubation/Process_Description.html)
is required of all newly accepted projects until a further review
indicates that the infrastructure, communications, and decision-making process
have stabilized in a manner consistent with other successful ASF projects.

While incubation status is not necessarily a reflection of the completeness
or stability of the code, it does indicate that the project has yet to be
fully endorsed by the ASF.



## Prerequisites

* Java 1.8 or newer (tested with OpenJDK 1.8)
* [Apache Maven](https://maven.apache.org/download.html) 3.2.5 or newer (older
  versions probably also work)


# Building

To build, use

    mvn clean install

This will build each module and run their tests.


## Skipping tests

To skip the tests (these can be time-consuming), use:

    mvn clean install -DskipTests


If you are modifying this source code independent of the
Apache Taverna project, you may not want to run the
[Rat Maven plugin](https://creadur.apache.org/rat/apache-rat-plugin/)
that enforces Apache headers in every source file - to disable it, try:

    mvn clean install -Drat.skip=true


# Export restrictions

This distribution includes cryptographic software.
The country in which you currently reside may have restrictions
on the import, possession, use, and/or re-export to another country,
of encryption software. BEFORE using any encryption software,
please check your country's laws, regulations and policies
concerning the import, possession, or use, and re-export of
encryption software, to see if this is permitted.
See <http://www.wassenaar.org/> for more information.

The U.S. Government Department of Commerce, Bureau of Industry and Security (BIS),
has classified this software as Export Commodity Control Number (ECCN) 5D002.C.1,
which includes information security software using or performing
cryptographic functions with asymmetric algorithms.
The form and manner of this Apache Software Foundation distribution makes
it eligible for export under the License Exception
ENC Technology Software Unrestricted (TSU) exception
(see the BIS Export Administration Regulations, Section 740.13)
for both object code and source code.

The following provides more details on the included cryptographic software:

* [taverna-rest-activity](taverna-rest-activity)
  depends on
  [Apache HttpComponents](https://hc.apache.org/) Client,
  and can be configured to initiate
  `https://` connections.
* [taverna-wsdl-generic](taverna-wsdl-generic)
  and [taverna-wsdl-activity](taverna-wsdl-activity) use
  [Java Secure Socket Extension](https://docs.oracle.com/javase/8/docs/technotes/guides/security/jsse/JSSERefGuide.html)
  (JSSE) and depend on
  [Apache WSS4J](https://ws.apache.org/wss4j/),
  [Apache XML Security for Java](https://santuario.apache.org/javaindex.html)
  for accessing secure SOAP Web Services.
* Apache Taverna Common Activities depends on the
  [Apache Taverna Engine](http://taverna.incubator.apache.org/download/engine/)
  Credential Manager API for
  management of username/password and client/server SSL certificates.
* [taverna-interaction-activity](taverna-interaction-activity) depends on
  [Jetty](http://www.eclipse.org/jetty/),
  which includes UnixCrypt.java for one way cryptography, and can be
  configured for SSL encryption using
  [Java Secure Socket Extension](https://docs.oracle.com/javase/8/docs/technotes/guides/security/jsse/JSSERefGuide.html).


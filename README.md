# Conference Management System

This project uses Spring MVC, Spring Security, Hibernate for back end and Bootstrap 4 and jQuery for front end.

In the "Eclipse settings" folder you will find settings for eclipse so that all programmers use the same code formatting.

### Diagrame, Documentatie

https://drive.google.com/drive/folders/0B2q8KbrEbYT0Tm5OdjZzemFJZHM?usp=sharing

### How to install

- download Eclipse for Java EE (https://www.eclipse.org/downloads/, get the installer and then choose "Eclipse for Java EE")
- download Tomcat 8.5 zip variant (http://tomcat.apache.org/download-80.cgi#8.5.14) and extract it into a permanent place
- import the project into eclipse
- make sure to use the Java EE perspective in eclipse
- add the tomcat as a server to eclipse by using the Servers view at the bottom, click the link "No servers are..." 
- under apache choose Tomcat 8.5, click next, and browse for the directory where you extracted Tomcat
- (optional) make sure that the Installed JRE is pointing to the Java JDK location not JRE
- click next and add the project from the Available column to the Configured one then click finish
- click on the project then on the top menu on Project - Clean
- right click on the project, Maven - Update Project and check Force Update of Snapshots/Releases
- right click on project, Run As - 5 Maven Build... (the option without any shortcuts)
- in the goals write "clean compile" and run the configuration
- on the servers tab you should se the Tomcat server, right click it and Start
- you can now access the site at http://localhost:8080/CMSWeb
- after making changes to the code, most of the time Tomcat will restart automatically (if you do not see your changes you can manualy restart the Tomcat server from the servers tab)


Set up the config file by copying the WebContent/WEB-INF/application.properties.sample file to the file WebContent/WEB-INF/application.properties and change your database information.

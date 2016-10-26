First of all, I'm using ActiveMQ messaging queue. To run the application, please install archive folder from the following link : http://activemq.apache.org/download.html.
I'm using the version 5.14.1 to avoid the conflicts in maven project.
After downloading the following archive, go to the bin folder and run the following command to start the server.
> ./activemq start

go to the following link to check whether the activemq is up or not --> http://localhost:8161/.

I am built a maven project.
Run the following command to compile the project :
> mvn clean install

This will create a jar file in the target folder.
Run the jar file or you can also run the Main class "App.java" from code editor. 


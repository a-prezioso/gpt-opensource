# Use the official Tomcat image as the base image
FROM tomcat:9-jdk11

# Remove the default Tomcat applications
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy your WAR file into the Tomcat webapps directory
COPY target/gpt-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Expose the Tomcat server port
EXPOSE 8080

# Start the Tomcat server
CMD ["catalina.sh", "run"]

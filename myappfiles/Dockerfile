# Use a base image with a Java runtime and Tomcat server
FROM tomcat:9.0-jre11-openjdk

# Remove the default Tomcat web application to avoid conflicts
RUN rm -rf /usr/local/tomcat/webapps/ROOT*

# Copy your WAR file into the Tomcat webapps directory.
COPY PurchaseOrder.war /usr/local/tomcat/webapps/PurchaseOrder.war

# Set environment variables for the database connection
ENV DB_HOST=db
ENV DB_USER=postgres
ENV DB_PASSWORD=qualian

# Expose the default Tomcat port
EXPOSE 8080
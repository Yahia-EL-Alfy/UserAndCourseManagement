# Use the WildFly base image
FROM quay.io/wildfly/wildfly:latest

# Set the environment variables
ENV JBOSS_HOME /opt/jboss/wildfly
ENV DEPLOYMENT_DIR ${JBOSS_HOME}/standalone/deployments

# Copy the WAR file to the deployments directory
COPY target/UserAndCourseManagement-1.0-SNAPSHOT.war ${DEPLOYMENT_DIR}/

# Expose the WildFly default port
EXPOSE 8080

# Start WildFly in standalone mode
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]

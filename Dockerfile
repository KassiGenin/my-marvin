FROM jenkins/jenkins:lts

USER root

RUN apt-get update && \
    apt-get install -y git && \
    rm -rf /var/lib/apt/lists/*

# Disable the initial setup wizard via system property
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

# Install plugins from the list
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt

# Copy the Groovy init script to skip the wizard
COPY init.groovy.d/disable-setup.groovy /usr/share/jenkins/ref/init.groovy.d/disable-setup.groovy
COPY init.groovy.d/autoApproveAll.groovy /usr/share/jenkins/ref/init.groovy.d/

# Copy Jenkins Configuration as Code files
COPY casc_configs /var/jenkins_home/casc_configs

# Copy the Job DSL script
COPY job_dsl.groovy /var/jenkins_home/job_dsl.groovy

# Point JCasc at the configuration and set install state
ENV CASC_JENKINS_CONFIG=/var/jenkins_home/casc_configs/my_marvin.yml
ENV CASC_JENKINS_INSTALL_STATE=RUNNING

# Seed the last execution version before volume mount
RUN echo "2.492.3" > /usr/share/jenkins/ref/jenkins.install.InstallUtil.lastExecVersion

# juste avant USER jenkins
ENV HOME=/var/jenkins_home

# Revert to the Jenkins user
USER jenkins

# Expose the default Jenkins HTTP port
EXPOSE 8080

FROM jenkins/jenkins:lts

USER root

# Désactive le wizard initial
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

# Plugins à installer
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt

# Désactivation du setup wizard via init script Groovy
COPY init.groovy.d/disable-setup.groovy /usr/share/jenkins/ref/init.groovy.d/disable-setup.groovy

# Configuration as Code
COPY casc_configs /var/jenkins_home/casc_configs

# Script statique (création du dossier Tools + jobs clone-repository & SEED)
COPY static_jobs.groovy /var/jenkins_home/static_jobs.groovy

# Script dynamique pour le SEED job
COPY job_dsl.groovy /var/jenkins_home/job_dsl.groovy

# Indique à JCasc où trouver le YAML de configuration
ENV CASC_JENKINS_CONFIG=/var/jenkins_home/casc_configs/my_marvin.yml
ENV CASC_JENKINS_INSTALL_STATE=RUNNING

# Simule l’installation déjà faite (version LTS)
RUN echo "2.492.3" > /usr/share/jenkins/ref/jenkins.install.InstallUtil.lastExecVersion

USER jenkins

EXPOSE 8080

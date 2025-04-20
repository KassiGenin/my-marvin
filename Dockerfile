FROM jenkins/jenkins:lts

# Passer en root pour installer des plugins
USER root

# Copier la liste des plugins et les installer
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt

# Copier la configuration JCasc
COPY casc_configs /var/jenkins_home/casc_configs

COPY job_dsl.groovy /var/jenkins_home/job_dsl.groovy

# Indiquer à Configuration as Code où trouver le fichier YAML
ENV CASC_JENKINS_CONFIG=/var/jenkins_home/casc_configs/my_marvin.yml

# Revenir à l'utilisateur Jenkins
USER jenkins

EXPOSE 8080

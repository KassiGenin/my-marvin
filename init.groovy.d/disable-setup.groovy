#!groovy
import jenkins.model.Jenkins
import jenkins.install.InstallState

// Force Jenkins to consider le setup initial déjà terminé
Jenkins.instance.setInstallState(InstallState.INITIAL_SETUP_COMPLETED)

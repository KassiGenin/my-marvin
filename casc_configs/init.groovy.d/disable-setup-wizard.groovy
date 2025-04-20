import jenkins.install.InstallState
import jenkins.model.Jenkins

// Marquer Jenkins comme déjà configuré
Jenkins.instance.setInstallState(InstallState.INITIAL_SETUP_COMPLETED)
Jenkins.instance.save()

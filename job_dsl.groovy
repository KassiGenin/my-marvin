// job_dsl.groovy
// Génère un job à partir des paramètres GITHUB_NAME et DISPLAY_NAME

def gitRepo     = GITHUB_NAME
def jobName     = DISPLAY_NAME

job(jobName) {
  displayName(jobName)
  description("Generated job for ${gitRepo}")

  // GitHub project linking (affiche le lien “Repository” dans Jenkins)
  properties {
    githubProjectUrl("https://github.com/${gitRepo}")
  }

  scm {
    git {
      remote { url("https://github.com/${gitRepo}.git") }
      branches("*/main")
    }
  }

  // Poll SCM every minute
  triggers {
    scm('H/1 * * * *')
  }

  // Pre-build cleanup
  wrappers {
    preBuildCleanup()
  }

  // Build steps
  steps {
    shell('make fclean')
    shell('make')
    shell('make tests_run')
    shell('make clean')
  }

  // Permet aussi un déclenchement manuel par l’utilisateur
  disabled(false)
}

// job_dsl.groovy

// Récupération des paramètres passés au job SEED
def repo        = GITHUB_NAME    // ex: "EpitechIT31000/chocolatine"
def displayName = DISPLAY_NAME   // ex: "my‑project‑ci"

// On génère un FreeStyleJob au niveau racine, nommé par DISPLAY_NAME
freeStyleJob(displayName) {
  description("CI job for ${displayName}, generated by SEED")

  // Propriété GitHub Project URL
  properties {
    githubProjectUrl("https://github.com/${repo}")
  }

  // SCM Git (auto‑checkout du repo GitHub)
  scm {
    git {
      remote {
        url("https://github.com/${repo}.git")
      }
      branches("*/main")
      extensions {
        // nettoyage avant build
        wipeOutWorkspace()
      }
    }
  }

  // Trigger SCM Poll every minute
  triggers {
    scm('* * * * *')
  }

  // Build steps
  steps {
    shell('make fclean')
    shell('make')
    shell('make tests_run')
    shell('make clean')
  }

  // Run manuellement ou via SCM poll only
  disabled(false)
}

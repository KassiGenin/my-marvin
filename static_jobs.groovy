// static_jobs.groovy
// Crée le dossier et les deux jobs de seed

folder('Tools') {
  description('Folder for miscellaneous tools.')
}

job('Tools/clone-repository') {
  description('Clone a Git repository passed as parameter')
  parameters {
    stringParam('GIT_REPOSITORY_URL', '', 'Git URL of the repository to clone')
  }
  wrappers {
    preBuildCleanup()
  }
  steps {
    shell('git clone "$GIT_REPOSITORY_URL" .')
  }
  disabled(false)
}

job('Tools/SEED') {
  description('Seed job for creating dynamic jobs via Job DSL')
  parameters {
    stringParam('GITHUB_NAME', '', 'GitHub repo owner/repo_name')
    stringParam('DISPLAY_NAME', '', 'Display name for the generated job')
  }
  scm {
    git {
      remote { url 'https://github.com/KassiGenin/my-marvin.git' }
      branches '*/main'
    }
  }
  wrappers {
    preBuildCleanup()
  }
  steps {
    jobDsl {
      targets('job_dsl.groovy')   // votre script d’origine, il sera appelé ici
      sandbox(false)
    }
  }
  disabled(false)
}

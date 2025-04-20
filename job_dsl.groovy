// Récupération des paramètres passés par le job SEED
def githubName   = System.getenv('GITHUB_NAME')
def displayName  = System.getenv('DISPLAY_NAME')

// Génération du job racine
job(displayName) {
    description("Job for ${displayName} from GitHub repo ${githubName}")
    
    // Propriété GitHub Project
    properties {
        githubProjectUrl("https://github.com/${githubName}")
    }
    
    // Pré-nettoyage du workspace
    wrappers {
        preBuildCleanup()
    }
    
    // Configuration SCM Git
    scm {
        git("https://github.com/${githubName}.git")
    }
    
    // Déclencheur SCM Poll chaque minute
    triggers {
        scm('H/1 * * * *')
    }
    
    // Étapes de build
    steps {
        shell('make fclean')
        shell('make')
        shell('make tests_run')
        shell('make clean')
    }
}

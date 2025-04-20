```md
# My Marvin

> **Chocolatine‑Powered Marvin Jenkins Instance**  
> Automatisation complète de Jenkins avec Configuration as Code (JCasC) et Job DSL pour déployer vos pipelines CI/CD.

## 📖 Description

Ce projet met en place une instance Jenkins préconfigurée :

- **Configuration as Code** via un unique fichier YAML (`my_marvin.yml`)
- **Job DSL** dans un script centralisé (`job_dsl.groovy`)  
- Création automatique :
  - D’un dossier racine **Tools**
  - D’un job **clone-repository** (clone un repo Git fourni en paramètre)
  - D’un job **SEED** (génère dynamiquement vos jobs à partir de Job DSL)
- Stratégie d’authentification locale et stratégie d’autorisation **role‑based**  
- Aucune configuration manuelle après build Docker : tout est défini dans le code.

## ⚙️ Fonctionnalités

- Désactivation du wizard d’installation Jenkins
- Gestion des utilisateurs Hugo, Garance, Jeremy, Nassim avec mots de passe provenant d’**env. vars.**
- Rôles globaux :
  - **admin** : droits `Overall/Administer`
  - **ape** : droits `Job/Build`, `Job/Workspace`
  - **gorilla** : rights de création/configuration/suppression/migration de jobs + annulation de builds
  - **assist** : droits lecture seul des jobs
- Job **clone-repository** :
  - Paramètre `GIT_REPOSITORY_URL`
  - Nettoyage workspace avant build
  - Clone du repo en un seul shell
- Job **SEED** :
  - Paramètres `GITHUB_NAME`, `DISPLAY_NAME`
  - Poll SCM chaque minute + trigger manuel
  - Exécute `job_dsl.groovy` pour générer vos jobs « root »

## 🚀 Prérequis

- Docker & Docker Compose
- Git (pour cloner ce repo)
- Variables d’environnement dans un fichier `.env` à la racine :

  ```ini
  USER_CHOCOLATEEN_PASSWORD=<mot_de_passe_hugo>
  USER_VAUGIE_G_PASSWORD=<mot_de_passe_garance>
  USER_I_DONT_KNOW_PASSWORD=<mot_de_passe_jeremy>
  USER_NASSO_PASSWORD=<mot_de_passe_nassim>
  ```

## 📁 Structure du projet

```
my-marvin/
├── Dockerfile
├── docker-compose.yml
├── plugins.txt
├── init.groovy.d/
│   └── disable-setup.groovy
├── casc_configs/
│   └── my_marvin.yml
├── job_dsl.groovy
└── .env
```

- **Dockerfile** : construction de l’image Jenkins
- **docker-compose.yml** : orchestration du container + volume
- **plugins.txt** : liste des plugins Jenkins à installer
- **init.groovy.d/** : désactivation du wizard
- **casc_configs/my_marvin.yml** : JCasC (configuration Jenkins)
- **job_dsl.groovy** : script centralisé Job DSL
- **.env** : mots de passe users

## 🛠️ Installation & Lancement

1. **Cloner** ce dépôt  
   ```bash
   git clone https://github.com/KassiGenin/my-marvin.git
   cd my-marvin
   ```

2. **Créer** un fichier `.env` à la racine avec vos mots de passe (cf. Prérequis).

3. **Lancer** Jenkins  
   ```bash
   docker compose up -d
   ```

4. **Accéder** à l’interface  
   Rendez-vous sur `http://localhost:8080`.  
   Connectez‑vous avec l’utilisateur `chocolateen` + mot de passe défini.

## 🔧 Utilisation des jobs

### clone-repository

- **Onglet** : Tools / clone-repository  
- **Paramètre** : `GIT_REPOSITORY_URL`  
- **Exécution** : manuelle  
- **Action** : clone le dépôt spécifié

### SEED

- **Onglet** : Tools / SEED  
- **Paramètres** :
  - `GITHUB_NAME` (ex. `MonOrg/MonRepo`)
  - `DISPLAY_NAME` (nom du job généré)  
- **Exécution** : manuelle ou poll SCM  
- **Action** : génère un ou plusieurs jobs root basés sur votre `job_dsl.groovy`

## 🛡️ Sécurité & Auth

- **Realm local** : inscription désactivée
- **Users** : chocolateen, vaugie_g, i_dont_know, nasso
- **Rôles** gérés via le plugin **role-strategy**

## 📜 Licence

Ce projet est sous licence **MIT** — voir le fichier [LICENSE](LICENSE) pour plus de détails.

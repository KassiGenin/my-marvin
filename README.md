
---

# my-marvin

**my-marvin** c’est un projet Jenkins préconfiguré avec Docker, pensé pour déployer automatiquement des jobs d’étudiants (ou autres scripts) dans un environnement contrôlé.

---

## ce que ça fait

- installe Jenkins dans un conteneur Docker
- configure automatiquement :
  - des utilisateurs avec des rôles
  - des jobs Jenkins via un script Groovy
  - un message d’accueil custom
- permet de builder depuis un dépôt GitHub
- structure simple et réutilisable pour d’autres cas

---

## prérequis

- Docker
- Docker Compose
- Git
- un compte GitHub (si tu veux builder depuis un repo)
- `.env` avec les mots de passe utilisateurs

---

## structure des fichiers

```
my-marvin/
├── docker-compose.yml       # pour lancer Jenkins avec tout ce qu’il faut
├── Dockerfile               # pour builder l’image Jenkins custom
├── casc/
│   └── my_marvin.yml        # configuration Jenkins-as-Code
├── jobs/
│   └── static_jobs.groovy   # script DSL qui crée les jobs
├── .env                     # mots de passe et secrets
```

---

## comment l’utiliser

1. clone le repo :
   ```bash
   git clone https://github.com/KassiGenin/my-marvin.git
   cd my-marvin
   ```

2. crée un fichier `.env` à la racine :
   ```
   USER_CHOCOLATEEN_PASSWORD=motdepasse1
   USER_VAUGIE_G_PASSWORD=motdepasse2
   USER_I_DONT_KNOW_PASSWORD=motdepasse3
   USER_NASSO_PASSWORD=motdepasse4
   ```

3. lance Jenkins :
   ```bash
   docker compose up
   ```

4. va sur [http://localhost:8080](http://localhost:8080)

---

## les utilisateurs par défaut

| utilisateur   | mot de passe depuis `.env` | rôle       |
|---------------|-----------------------------|------------|
| chocolateen   | USER_CHOCOLATEEN_PASSWORD   | admin      |
| vaugie_g      | USER_VAUGIE_G_PASSWORD      | gorilla    |
| i_dont_know   | USER_I_DONT_KNOW_PASSWORD   | ape        |
| nasso         | USER_NASSO_PASSWORD         | assist     |

---

## comment modifier le job par défaut

regarde dans `jobs/static_jobs.groovy`. tu peux modifier le nom du job, le repo, la branche ou les étapes à exécuter.  
exemple : pull un repo GitHub et lancer un `make test` ou autre script bash.

---

## comment reset jenkins

si tu veux repartir de zéro :

```bash
docker compose down -v
```

ça supprime les volumes (et donc les données Jenkins, plugins, jobs, users, etc)

---

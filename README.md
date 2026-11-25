# tasks-api - Projet DevSecOps (Projet 1)

Application Java / Spring Boot tres simple : une liste de taches en memoire.
Le travail a realiser est la partie **DevSecOps** : suivre le guide
"Guide DevSecOps - Projet 1 tasks-api" etape par etape.

## Endpoints
| Methode | URL | Role |
|---|---|---|
| GET | `/` | page d'accueil |
| GET | `/api/tasks` | liste des taches |
| POST | `/api/tasks` | creer `{"title":"..."}` |
| PUT | `/api/tasks/{id}/done` | marquer terminee |

## Lancer
```bash
mvn clean package
java -jar target/tasks-api.jar     # http://localhost:8080
```

## Fichiers DevSecOps a completer (guide)
`.github/workflows/ci.yml`, `Dockerfile`, `.dockerignore`, `.trivyignore`,
`monitoring/docker-compose.yml`, `monitoring/prometheus.yml`
+ ajouts dans `pom.xml` (JaCoCo, Actuator) et `application.properties` (Actuator).

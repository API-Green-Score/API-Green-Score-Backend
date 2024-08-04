# Besoins

## Definition besoin

- Github général + règles : https://github.com/API-Green-Score/APIGreenScore
- Ticket "init projet" : https://github.com/orgs/API-Green-Score/projects/1?pane=issue&itemId=38066639
- Ticket "CR pt lancement" : https://github.com/orgs/API-Green-Score/projects/1?pane=issue&itemId=38077343

### Besoin fonctionnel

- Afin de créer une IHM pour calculer la note d'une API, je souhaite créer une IHM qui permet de remplacer la feuille de
  calcul.
- Cette IHM doit permettre de configurer les différentes règles à utiliser, ainsi que de positionner la pondération
- Cette IHM doit stocker les résultats et les horodater afin de pouvoir suivre l'évolution dans le temps.
- Cette IHM s'appuyera sur une API qui aura un service backend pour gérer la partie CRUD
- Cette Application reste simple mais doit suivre les bonnes pratiques d'éco-conception.
- Nous devons avoir une application bootstrap qui permet à un débutant de rapidement avoir un produit utilisable.
- Un.e developpeur.euse plus expert pourra décliner cette application sur sa propre infrastructure afin de sécuriser
  l'accès et le stockage des données.

Traduction DDC :

- IHM pour intéractions utilisateur pour appels à l'API
- API CRUD pour gestion des données
    - configuration des règles et de leurs pondérations
    - stockage des données (config générale, règles, pondérations, résultats)
    - suivi dans le temps des données d'évaluation (à horodater)
- Simplicité de mise en place du produit pour lancement rapide
- Possibilité configuration de l'appli pour
    - stockage externe
    - déploiement sur infrastructure custom

### Besoin technique

- API CRUD pour la gestion du back :
    - gestion des règles
    - gestion de configuration globale ("note globale", pondération des sections, paliers des notes)
- Possibilité configuration BD externe (mongo)
- Process pour initialisation environnement (docker)
- Github :
    - mise en place workflow github
    - mise en place de hook sur github sur commit (validation message commit)
    - mise en place de workflow (build, ...)
- SonarQube Cloud pour Open Source
    - respect bonnes pratiques de dev
    - respect bonnes pratiques d'éco-conception
- Swagger : Open API Spec
- DOC - Contributing : ajouter des pointeurs sur les bonnes pratiques de git, les commandes de base Docker, Json,….

### Stack technique

==> au plus simple et plus rapide

- Docker
- Java
- MongoDB
- Swagger (OAS)
- SonarQube
- Github

## Roadmap

### Version 0.0.2

OBJECTIF : CRUD complet

- [x] PAS d'IHM pour le moment
- [x] API CRUD simpliste
    - [x] Fonctionnel : pas de calcul de scoring pour le moment
    - [x] Fonctionnel : pas de gestion de suvi de scoring pour le moment
    - [x] Technique : pas de BD externe pour le moment
    - [ ] CRUD sur les Rules et GlobalConfig
    - [ ] SWAGGER : continuité
    - [ ] TU / TI : continuité
    - [ ] Coverage : continuité
- [ ] SONARQUBE CLOUD : continuité

### Version 0.0.1

OBJECTIF : init du projet + init API minimaliste

- [x] PAS d'IHM pour le moment
- [x] API CRUD simpliste
    - [x] Fonctionnel : pas de calcul de scoring pour le moment
    - [x] Fonctionnel : pas de gestion de suivi de scoring pour le moment
    - [x] Technique : pas de BD externe pour le moment
    - [x] QUE du GET pour le moment (règles, conf global)
    - [x] insertion données en BD via un dump initialisé avec les données du fichier Excel
    - [x] SWAGGER :
        - [x] définition contrat d'interface SWAGGER
        - [x] intéraction avec API via SWAGGER
        - [x] génération du code à partir du contrat SWAGGER
    - [x] TU / TI
    - [x] Coverage maximal
- [x] DOCKER : mise en place système de lancement dockerisé
- [x] GITHUB : mise en place workflow github "build"
- [x] SONARQUBE CLOUD : mise en place sonarcloud pour le projet

## CRs

### 19/01/2024 : point explication avec Yannick

- mettre de côté les règles métiers pour Yannick suivant l'avancement de l'implémentation
- regle métier = bien vérifier que la somme totale est bien égale au 6000 (si un user désactive une règle)
- "item analyzed" = titre
- une évaluation par API (ou groupe d'API) sans détails au verbes ... + commentaire sur le choix de l'évaluation (ex :
  on peut mettre VRAI même si 95% de travail fait)
- évaluation faite par API ==> prévoir un champ pour identifier la ressource évaluée (données d'évaluation)

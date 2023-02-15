DOCUMENTATION API GROUPE 9
-
Bienvenu sur le projet du **groupe 9**.
Vous trouverez dans le [dossier doc](./doc/Jeux%20de%20cartes%20clean%20code.postman_collection.json) une collection d'API que vous pouvez 
importer dans votre postman ou insomnia afin de tester les API
***

**NB: n'oubliez pas de creer votre base de donnée *jeudecartes***
**Si vous avez postgres sur votre pc, vous pouvez commenter la config docker dans application properties et decommenter l'autre.**
***

API
-
* GET */api/hero* : permet d'avoir la liste des heros disponible
* POST */api/hero* : permet de creer un hero
* POST */api/player* : permet de creer un compte à un joueur ou de se connecter
* POST */api/desk* : permet à un joueur d'approvisionner son deck
* GET */api/player* : permet de rechercher un joueur
* POST */api/combat* : permet d'engager un combat
* GET */api/hero/combat/{id_hero}* : permet d'avoir l'historique des combats d'un hero

Fonctionnalités attendues
-
***
1. Créations des héros en base de données
2. Rechercher les héros disponibles
3. Création d’un compte joueur avec solde de jetons et deck en base de données
4. Ouverture de packs et ajout des cartes au deck du joueur
5. Rechercher des joueurs et visualiser leurs decks
6. Engager un combat entre un héros et celui d’un autre joueur
7. Pouvoir retrouver tous les combats d’un héros (héros adverse, résultat du combat)

# Choix de Technologie

## a. UDP/TCP

### UDP pour la Découverte des Contacts :
- **Légèreté et Vitesse** : L'en-tête UDP est plus léger que celui de TCP, permettant des transmissions plus rapides.
- **Non Connexion** : UDP ne nécessite pas d'établir une connexion, ce qui est idéal pour la découverte de contacts. Dans notre projet, en utilisant un identifiant spécifique en début de message (tels que `DECOUVERTE_` et `REPONSE_`), le système peut reconnaître et traiter différents types de messages efficacement.

### TCP pour l'Envoi des Messages :
- **Fiabilité** : TCP assure l'intégrité et le séquencement des données transmises.
- **Connexion Orientée** : La nature orientée connexion de TCP implique qu'une session doit être établie entre émetteur et destinataire avant l'échange de données, assurant ainsi la fiabilité de la transmission.

## b. SQLite
- Au lieu d'utiliser MySQL, nous avons opté pour SQLite en raison de sa simplicité et de sa capacité à stocker les données localement sans nécessiter de serveur dédié. Cette approche minimise la maintenance et s'adapte bien à la nature de notre projet en binôme.

## c. Swing
- Pour l'interface utilisateur, nous avons choisi Swing pour son riche ensemble de composants et sa capacité à créer des interfaces graphiques interactives en Java. Swing facilite la création d'écrans de connexion, de fenêtres de chat et d'autres composants essentiels à une expérience utilisateur agréable.

# Processus de Test

Nous avons adopté une approche pratique pour tester notre application, simulant des scénarios d'utilisateur réels pour vérifier toutes les fonctionnalités clés, de la gestion des amis à la mise à jour des pseudonymes, ainsi que les processus de connexion/déconnexion. Les tests concernant l'envoi et la réception de messages ont été minutieusement effectués pour s'assurer de l'intégrité et de la fiabilité de la base de données des messages.

# Souligner

## Structure du Code :
- Le modèle MVC a été scrupuleusement suivi dans toutes les classes, en particulier dans le module `Protocole` dédié à la transmission réseau. `MessageListener` a été utilisé pour permettre à la vue de se mettre à jour en temps réel lors de la réception des messages par `TCPreceiver`.

## Synchronisation :
- La synchronisation a été méticuleusement gérée dans plusieurs composants, notamment `MessageListener`, `BaseDeDonnee`, `TCPreceiver` et `Messagerie`. L'utilisation judicieuse de `ReentrantLock` et `synchronized` garantit la cohérence et la fiabilité des données, assurant une synchronisation efficace entre la base de données et la vue.

# TP Java --- Catch me if you can !

*(Version Markdown générée automatiquement)*

## 1. Introduction

Bienvenue dans ces séances de TP. Voici quelques règles pour le bon
déroulement de ces séances :

-   À Bac+5, nous ne corrigeons plus les erreurs de « ; » manquants et
    autres trivialités.\
-   Le travail se fait sur vos propres PC. Merci de venir en séance avec
    les logiciels installés et testés.\
-   La sauvegarde régulière est **votre** problème.\
-   Nous suggérons fortement l'usage d'un logiciel de gestion de version
    comme Git.

## 2. Présentation générale du projet

Il s'agit d'un utilitaire permettant d'afficher des données liées à
l'aviation de tourisme.\
On utilisera :

-   Une source statique : la liste mondiale des aéroports (.csv)\
-   Une source dynamique : l'API du site *aviationstack.com*

La structure UML du projet comporte les classes suivantes :

-   **Aeroport** : nom, latitude, longitude, code IATA\
-   **World** : liste des aéroports, recherchés par code ou par
    position\
-   **Interface** : gère l'IHM, l'interactivité et les affichages\
-   **Flight** : modélise un vol entre deux aéroports\
-   **JsonFlightFillerOracle** : récupère les vols via API\
-   **Earth** : planète 3D héritant de `Group`

## 3. Première session : Classes de base et lecture CSV

Créer la classe `Aeroport`, puis la classe `World` chargée de lire le
fichier CSV.

### Distance approximative entre deux aéroports

\[ ext{norme} = (`\Theta`{=tex}\_2 − `\Theta`{=tex}\_1)\^2 +
`\left`{=tex}((`\Phi`{=tex}\_2 −
`\Phi`{=tex}\_1)`\cos`{=tex}`\left`{=tex}(rac{`\Theta`{=tex}\_2+`\Theta`{=tex}\_1}{2}ight)ight)\^2
\]

Implémenter :

-   `calculDistance(Aeroport a)`\
-   `findNearest`\
-   `findByCode`

Un test Java est fourni dans le TP.

## 4. Travail à la maison

Vous devez mettre en place une interface JavaFX en créant une classe
`Interface` héritant de `Application`.

Exemple minimal fourni dans le TP.

Instructions :

-   Télécharger JavaFX
-   Configurer IntelliJ (Project Structure + VM Options)
-   Exécuter un premier « Hello World »

## 5. Deuxième partie : IHM

Objectifs :

-   Afficher une Terre en 3D (`Sphere`)
-   Ajouter une caméra (`PerspectiveCamera`)
-   Gérer zoom / drag avec `MouseEvent`
-   Ajouter une texture (image fournie)
-   Faire tourner la Terre avec `AnimationTimer`
-   Détecter un clic droit → récupérer latitude/longitude → aéroport le
    plus proche
-   Ajouter une sphère rouge sur l'aéroport cliqué

### Conversion texture → coordonnées

\[ `\Theta `{=tex}= 180(0.5 - Y), `\quad `{=tex}`\Phi `{=tex}= 360(X -
0.5) \]

### Sphère affichée sur la Terre

\[ X =
R`\cos`{=tex}(`\Theta`{=tex})`\sin`{=tex}(`\Phi`{=tex}),`\quad `{=tex}Y=-R`\sin`{=tex}(`\Theta`{=tex}),`\quad `{=tex}Z=-R`\cos`{=tex}(`\Theta`{=tex})`\sin`{=tex}(`\Phi`{=tex})
\]

## 6. Troisième partie : Données « live »

Utilisation de l'API AviationStack :

-   Lire la doc

-   Obtenir une clé API gratuite

-   Test via navigateur :

        http://api.aviationstack.com/v1/flights?access_key=VOTRE_CLE&arr_iata=CDG

Une classe `JsonFlightFiller` est à compléter selon les exemples
fournis.

## 7. Threading : éviter le freeze de l'IHM

Pour ne pas bloquer l'animation lors des requêtes HTTP :

-   Exécuter la requête API dans un `Runnable`
-   Mettre à jour l'IHM depuis le thread JavaFX

## 8. Conclusion

Ce TP fait découvrir :

-   Programmation objet avancée\
-   JavaFX 3D\
-   Requêtes web et parsing JSON\
-   Interactions graphiques temps réel

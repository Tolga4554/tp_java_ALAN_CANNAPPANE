Tolga ALAN / Ratik CANNAPPANE  / TP1

Ce projet est une application Java/JavaFX pour visualiser les aéroports les plus proches sur un globe 3D.

Le projet est divisé en trois parties logiques.

PARTIE 1 : CLASSES DE BASE ET LECTURE CSV (Monde)

J'ai créé les deux premières classes pour gérer les données :

1.  Aeroport.java
   C'est la brique de base : elle stocke l'IATA, le nom, et les coordonnées GPS (latitude/longitude) de chaque aéroport.
   Elle contient la méthode **calculDistance()** pour trouver la "proximité" entre deux points GPS, ce qui est nécessaire pour trouver l'aéroport le plus proche.

2.  World.java
   Cette classe charge tous les aéroports depuis le fichier **airport-codes_no_comma.csv**.
   Attention : J'ai dû modifier la ligne de lecture du fichier (`s.split(",")`) en `s.split(";")` car le fichier CSV utilise un point-virgule comme séparateur de colonne principal, et non une virgule, pour que la lecture fonctionne.
   Elle stocke uniquement les aéroports de type "large_airport".
   Elle contient les méthodes de recherche :
        findByCode(): trouve un aéroport par son code IATA (ex: "CDG").
        findNearestAirport(): utilise calculDistance() pour trouver l'aéroport le plus proche des coordonnées données.

Tests : Je voulais vérifier le bon fonctionnement de ces classes avec `TestAeroport.java` et `TestWorld.java` (qui confirme que l'aéroport le plus proche de Paris est bien ORY). La vérification de la classe Aéroports fonctionne correctement mais pas celle de la classe World. Nous n'avons pas réussis à trouver le problème.

public class TestWorld {

    public static void main(String[] args) {

        System.out.println("Démarrage du Test de la classe World");

        World w = new World("data\\airport-codes_no_comma.csv");

        int nombreAeroports = w.getList().size();
        System.out.println("\n1. Test de Chargement du Fichier :");
        System.out.println("Nombre d'aéroports chargés (large_airport) : " + nombreAeroports);


        if (nombreAeroports == 0) {
            System.err.println("ERREUR");
            return ;
        }


        String iataCode = "CDG";
        Aeroport cdg = w.findByCode(iataCode);

        System.out.println("\n2. Test findByCode (" + iataCode + ") :");
        if (cdg != null) {
            System.out.println("Trouvé : " + cdg.getName() + " (" + cdg.getLatitude() + ", " + cdg.getLongitude() + ")");
        } else {
            System.err.println("ERREUR : L'aéroport " + iataCode + " n'a pas été trouvé.");
        }


        double latParis = 48.866;
        double lonParis = 2.316;

        System.out.println("\n3. Test findNearestAirport (Paris) :");
        System.out.println("Recherche du plus proche de : Lat=" + latParis + ", Lon=" + lonParis);


        Aeroport nearestAirport = w.findNearestAirport(lonParis, latParis);

        if (nearestAirport != null) {
            System.out.println("RÉSULTAT ATTENDU : ORY");
            System.out.println("Aéroport trouvé : " + nearestAirport.getIATA() + " - " + nearestAirport.getName());


            if (nearestAirport.getIATA().equals("ORY")) {
                System.out.println("VÉRIFICATION : OK. La méthode de proximité fonctionne.");
            } else {
                System.out.println("VÉRIFICATION");
            }
        } else {
            System.err.println("ERREUR");
            return ;
        }

        System.out.println("\nFin du Test World");
    }
}

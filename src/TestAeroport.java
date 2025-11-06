public class TestAeroport {

    // Le point de départ du test
    public static void main(String[] args) {

        System.out.println("--- Démarrage du Test de la classe Aeroport ---");

        // 1. Création du premier objet (Paris CDG)
        // Ceci teste le CONSTRUCTEUR de la classe Aeroport
        // IATA, Nom, Pays, Latitude, Longitude
        Aeroport paris = new Aeroport("CDG", "Paris-Charles de Gaulle Airport", "France", 49.0097, 2.5479);

        System.out.println("\n1. Test du Constructeur et de toString() :");
        // 2. Test de toString() et getName() (maintenant inclus dans Aeroport)
        System.out.println("Objet Paris (toString) : " + paris);
        System.out.println("Nom via Getter : " + paris.getName());

        // 3. Création du deuxième objet (New York JFK)
        Aeroport newYork = new Aeroport("JFK", "John F. Kennedy International Airport", "United States", 40.6413, -73.7781);

        // 4. Test de la méthode calculDistance()
        double distanceNorme = paris.calculDistance(newYork);

        System.out.println("\n2. Test de calculDistance() :");
        System.out.println("Distance simplifiée (Norme) entre CDG et JFK : " + distanceNorme);

        System.out.println("--- Fin du Test Aeroport ---");
    }
}
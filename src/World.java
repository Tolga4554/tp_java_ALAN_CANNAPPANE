import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

public class World {

    // Attribut : la liste de tous les grands aéroports chargés
    private ArrayList<Aeroport> list;

    // --- CONSTRUCTEUR World(String fileName) ---
    // Lit le fichier CSV pour remplir la liste
    public World(String fileName) {
        list = new ArrayList<>();
        try {
            // Lecture du fichier spécifié par 'fileName'
            BufferedReader buf = new BufferedReader(new FileReader(fileName));
            String s = buf.readLine(); // Ignore la ligne d'en-tête

            while ((s = buf.readLine()) != null) {
                s = s.replaceAll("\"", ""); // Nettoyage
                String[] fields = s.split(","); // Séparation par la virgule

                // Filtre : s'assurer que c'est bien un "large_airport"
                if (fields.length > 1 && fields[1].equals("large_airport")) {
                    try {
                        // Extraction des champs
                        String IATA = fields[8];
                        String Name = fields[2];
                        String country = fields[5];

                        // Parsing des coordonnées (vérifiez vos indices CSV !)
                        String[] coords = fields[fields.length - 2].split(";");
                        double longitude = Double.parseDouble(coords[0]);
                        double latitude = Double.parseDouble(coords[1]);

                        // Création de l'objet et ajout à la liste
                        Aeroport a = new Aeroport(IATA, Name, country, latitude, longitude);
                        list.add(a);
                    } catch (NumberFormatException e) {
                        // Ignore la ligne si la conversion d'un nombre échoue
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur: Le fichier n'est pas trouvé ou un problème de lecture.");
            e.printStackTrace();
        }
    }

    // --- MÉTHODES DE RECHERCHE ---

    // Recherche un aéroport par son code IATA
    public Aeroport findByCode(String code) {
        for (Aeroport a : list) {
            if (a.getIATA().equalsIgnoreCase(code)) {
                return a;
            }
        }
        return null;
    }

    // Recherche l'aéroport le plus proche des coordonnées de référence
    public Aeroport findNearestAirport(double refLongitude, double refLatitude) {
        if (list.isEmpty()) {
            return null;
        }

        // Crée un point de référence Aeroport pour utiliser la méthode calculDistance()
        Aeroport reference = new Aeroport("REF", "Reference Point", "", refLatitude, refLongitude);

        Aeroport nearest = list.get(0);
        double minDistance = reference.calculDistance(nearest);

        for (Aeroport current : list) {
            double currentDistance = reference.calculDistance(current);
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                nearest = current;
            }
        }
        return nearest;
    }

    // Getter pour obtenir la liste complète
    public ArrayList<Aeroport> getList() {
        return list;
    }
}
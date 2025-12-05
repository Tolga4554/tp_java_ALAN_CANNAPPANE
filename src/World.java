import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Objects;

public class World {

    // Attribut : la liste de tous les grands aéroports chargés
    private ArrayList<Aeroport> list;

    // --- CONSTRUCTEUR World(String fileName) ---
    // Lit le fichier CSV pour remplir la liste
    public World(String fileName) {
        list = new ArrayList<>();
        try (BufferedReader buf = new BufferedReader(new FileReader(fileName))) {
            // Ignore l'en-tête
            buf.readLine();

            String line;
            while ((line = buf.readLine()) != null) {
                // Les champs sont séparés par des virgules, les coordonnées par un ';'
                line = line.replace("\"", "");
                String[] fields = line.split(",");

                // Champs attendus : ident,type,name,...,gps_code(8),iata_code(9),local_code(10),coordinates(11)
                if (fields.length < 12 || !Objects.equals(fields[1], "large_airport")) {
                    continue;
                }

                String iata = fields[9];
                if (iata == null || iata.isEmpty()) {
                    continue; // on ignore les entrées sans code IATA
                }

                String name = fields[2];
                String country = fields[5];
                String[] coords = fields[11].split(";");

                if (coords.length != 2) {
                    continue;
                }

                try {
                    double longitude = Double.parseDouble(coords[0]);
                    double latitude = Double.parseDouble(coords[1]);
                    list.add(new Aeroport(iata, name, country, latitude, longitude));
                } catch (NumberFormatException ignored) {
                    // on saute la ligne si le parsing échoue
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
    public Aeroport findNearest(double refLatitude, double refLongitude) {
        if (list.isEmpty()) {
            return null;
        }

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

    // Compatibilité avec l'ancien nom
    public Aeroport findNearestAirport(double refLongitude, double refLatitude) {
        return findNearest(refLatitude, refLongitude);
    }

    // Getter pour obtenir la liste complète
    public ArrayList<Aeroport> getList() {
        return list;
    }
}
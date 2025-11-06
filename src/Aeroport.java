public class Aeroport {

    // Attributs privés
    private String IATA;
    private String Name;
    private String country;
    private double latitude;
    private double longitude;

    // --- CONSTRUCTEUR ---
    public Aeroport(String IATA, String Name, String country, double latitude, double longitude) {
        this.IATA = IATA;
        this.Name = Name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // --- GETTERS (Accesseurs) ---
    public String getIATA() {
        return IATA;
    }

    // NOUVEAU GETTER AJOUTÉ pour corriger l'erreur 'cannot find symbol: getName()'
    public String getName() {
        return Name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    // --- MÉTHODE toString() ---
    @Override
    public String toString() {
        return "Aeroport [IATA=" + IATA + ", Name=" + Name + ", Country=" + country +
                ", Latitude=" + latitude + ", Longitude=" + longitude + "]";
    }

    // --- MÉTHODE calculDistance() ---
    public double calculDistance(Aeroport other) {
        // Conversion en radians
        double lat1Rad = Math.toRadians(this.latitude);
        double lat2Rad = Math.toRadians(other.latitude);
        double lon1Rad = Math.toRadians(this.longitude);
        double lon2Rad = Math.toRadians(other.longitude);

        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;
        double meanLat = (lat2Rad + lat1Rad) / 2.0;

        // Formule de la norme simplifiée
        double terme1 = deltaLat * deltaLat;
        double terme2 = (deltaLon * Math.cos(meanLat));
        double norme = terme1 + (terme2 * terme2);

        return norme;
    }
}
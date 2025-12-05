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

    public String getCountry() {
        return country;
    }

    // --- MÉTHODE toString() ---
    @Override
    public String toString() {
        return "Aeroport [IATA=" + IATA + ", Name=" + Name + ", Country=" + country +
                ", Latitude=" + latitude + ", Longitude=" + longitude + "]";
    }

    // --- MÉTHODE calculDistance() ---
    public double calculDistance(Aeroport other) {
        // Conversion degrés -> radians
        double theta1 = Math.toRadians(this.latitude);
        double theta2 = Math.toRadians(other.latitude);
        double phi1 = Math.toRadians(this.longitude);
        double phi2 = Math.toRadians(other.longitude);

        double deltaTheta = theta2 - theta1;
        double deltaPhi = phi2 - phi1;
        double meanTheta = (theta2 + theta1) / 2.0;

        // Formule fournie dans l'énoncé (approximation sur sphère)
        double norme = (deltaTheta * deltaTheta) + Math.pow(deltaPhi * Math.cos(meanTheta), 2);

        // Conversion en distance réelle sur Terre (km)
        final double earthRadiusKm = 6371.0;
        return Math.sqrt(norme) * earthRadiusKm;
    }
}
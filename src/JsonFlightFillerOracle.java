import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Récupère des données de vols via l'API aviationstack.
 * Cette classe reste volontairement minimale : elle renvoie la réponse brute JSON sous forme de String.
 * Ajoutez un parseur JSON (Jackson, Gson...) si vous souhaitez créer des objets Flight.
 */
public class JsonFlightFillerOracle {

    private final String apiKey;

    public JsonFlightFillerOracle(String apiKey) {
        this.apiKey = apiKey;
    }

    public Optional<String> fetchArrivals(String arrivalIata) {
        try {
            String url = "http://api.aviationstack.com/v1/flights?access_key="
                    + apiKey + "&arr_iata=" + arrivalIata;
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int status = conn.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    status >= 200 && status < 300 ? conn.getInputStream() : conn.getErrorStream(),
                    StandardCharsets.UTF_8));

            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            conn.disconnect();

            if (status >= 200 && status < 300) {
                return Optional.of(response.toString());
            }
            System.err.println("Requête API en erreur (" + status + "): " + response);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'appel API AviationStack : " + e.getMessage());
        }
        return Optional.empty();
    }
}



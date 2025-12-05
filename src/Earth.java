import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

/**
 * Représente la Terre en 3D. Une texture peut être chargée si elle est
 * disponible dans le classpath, sinon une couleur de secours est appliquée.
 */
public class Earth extends Group {

    private final Sphere globe;
    private final double radius;

    public Earth(double radius) {
        this.radius = radius;
        this.globe = new Sphere(radius);
        this.globe.setMaterial(buildMaterial());
        this.getChildren().add(globe);
    }

    public double getRadius() {
        return radius;
    }

    private PhongMaterial buildMaterial() {
        PhongMaterial material = new PhongMaterial();
        try {
            Image texture = new Image(getClass().getResourceAsStream("/earth.jpg"));
            material.setDiffuseMap(texture);
        } catch (Exception e) {
            material.setDiffuseColor(Color.DARKBLUE);
        }
        material.setSpecularColor(Color.LIGHTGRAY);
        return material;
    }

    public Sphere getGlobe() {
        return globe;
    }
}



import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.PointLight;
import javafx.scene.LightBase;


/**
 * IHM minimale JavaFX pour visualiser la Terre en 3D, zoomer/faire tourner,
 * et récupérer l'aéroport le plus proche d'un clic droit.
 */
public class Interface extends Application {

    private static final double EARTH_RADIUS = 200;

    private Earth earth;
    private Group markers;
    private World world;
    private double anchorX;
    private double anchorY;
    private double angleX = 0;
    private double angleY = 0;
    private final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);

    @Override
    public void start(Stage stage) {
        world = new World("data/airport-codes_no_comma.csv");
        earth = new Earth(EARTH_RADIUS);
        markers = new Group();

        Group root = new Group();
        earth.getTransforms().addAll(rotateX, rotateY);
        root.getChildren().addAll(earth, markers, buildLight());

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-600);
        camera.setNearClip(0.1);
        camera.setFarClip(2000);

        SubScene subScene = new SubScene(root, 1000, 700, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);
        subScene.setFill(Color.BLACK);
        attachInteractions(subScene, camera);

        Scene scene = new Scene(new Group(subScene));
        stage.setTitle("TP Java - Terre & Aéroports");
        stage.setScene(scene);
        stage.show();

        startAutoRotation();
    }

    private void attachInteractions(SubScene subScene, Camera camera) {
        subScene.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                anchorX = event.getSceneX();
                anchorY = event.getSceneY();
            }
        });

        subScene.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                double deltaX = event.getSceneX() - anchorX;
                double deltaY = event.getSceneY() - anchorY;
                angleY += deltaX * 0.1;
                angleX -= deltaY * 0.1;
                rotateX.setAngle(angleX);
                rotateY.setAngle(angleY);
                anchorX = event.getSceneX();
                anchorY = event.getSceneY();
            }
        });

        subScene.setOnScroll(event -> camera.setTranslateZ(camera.getTranslateZ() + event.getDeltaY()));

        subScene.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY && event.isStillSincePress()) {
                handleRightClick(event);
            }
        });
    }

    private void handleRightClick(MouseEvent event) {
        PickResult pick = event.getPickResult();
        if (pick == null || pick.getIntersectedNode() == null) {
            return;
        }

        Point3D p = pick.getIntersectedPoint();
        if (p == null) {
            return;
        }

        // Projection inverse : coordonnées cartésiennes -> latitude/longitude
        double r = EARTH_RADIUS;
        double latRad = Math.asin(-p.getY() / r);
        double lonRad = Math.atan2(-p.getZ(), p.getX());
        double latDeg = Math.toDegrees(latRad);
        double lonDeg = Math.toDegrees(lonRad);

        Aeroport nearest = world.findNearest(latDeg, lonDeg);
        if (nearest != null) {
            addMarker(nearest);
            System.out.println("Clic à lat=" + latDeg + ", lon=" + lonDeg
                    + " → aéroport le plus proche : " + nearest.getIATA() + " - " + nearest.getName());
        }
    }

    private void addMarker(Aeroport aeroport) {
        Sphere marker = new Sphere(4);
        marker.setMaterial(new javafx.scene.paint.PhongMaterial(Color.RED));

        double theta = Math.toRadians(aeroport.getLatitude());
        double phi = Math.toRadians(aeroport.getLongitude());

        double x = EARTH_RADIUS * Math.cos(theta) * Math.sin(phi);
        double y = -EARTH_RADIUS * Math.sin(theta);
        double z = -EARTH_RADIUS * Math.cos(theta) * Math.sin(phi);

        marker.setTranslateX(x);
        marker.setTranslateY(y);
        marker.setTranslateZ(z);

        markers.getChildren().add(marker);
    }

    private LightBase buildLight() {
        PointLight light = new PointLight(Color.WHITE);
        light.setTranslateZ(-400);
        return light;
    }

    private void startAutoRotation() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                angleY += 0.02;
                rotateY.setAngle(angleY);
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


package jabr1181768;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Calculation {
	public static void findThePathStage(String sCity, String tCity, Graph pathGraph) {
		String thePath = "";
		String prev = "";

		List<Edge> path = pathGraph.getThePath(sCity, tCity);

		for (int i = 0; i < path.size(); i++) {
			if (path.get(i).sourceCity.name.equals(prev)) {
				thePath += "Source City : " + path.get(i).sourceCity.name + " , Target City : "
						+ path.get(i).targetCity.name + " , the distance : " + path.get(i).distance + "\n";
				prev = path.get(i).targetCity.name;
			} else
				thePath += "Source City : " + path.get(i).targetCity.name + " , Target City : "
						+ path.get(i).sourceCity.name + " , the distance : " + path.get(i).distance + "\n";
			prev = path.get(i).sourceCity.name;
		}

		if (sCity.equals(tCity))
			AlertBoxForPath.display("", "The Path");
		else if (thePath.equals(""))
			AlertBoxForPath.display("There is no path!!", "The Path");
		else
			AlertBoxForPath.display(thePath, "The Path Stage");
	}

	public static void CalculateDistanceStage(String sCity, String tCity, Graph distanceGraph) {
		double distance = 0;
		List<Edge> path = distanceGraph.getThePath(sCity, tCity);

		for (int i = 0; i < path.size(); i++) {
			distance += path.get(i).distance;
		}

		int d = (int) distance;
		if (d < 2 && !sCity.equals(tCity))
			AlertBoxForDistance.display("", "The Distance");
		else
			AlertBoxForDistance.display("The total distance is : " + d + " KM", "The Distance Stage");
	}

	public static void mapStage(String sCity, String tCity, Graph mapGraph) {

		Group pane1 = new Group();
		ScrollBar scrollbar = new ScrollBar();
		Image palestineMapImage = new Image("file:images/palestineMap.jpg");
		ImageView imageView = new ImageView(palestineMapImage);
		Group pane = new Group(imageView);

		List<Edge> path = mapGraph.getThePath(sCity, tCity);
		Line[] lines = new Line[path.size()];

		for (int i = 0; i < path.size(); i++) {
			lines[i] = new Line(path.get(i).sourceCity.x, path.get(i).sourceCity.y, path.get(i).targetCity.x,
					path.get(i).targetCity.y);
			lines[i].setStyle("-fx-stroke : #003300; -fx-stroke-width: 5px;");
		}

		for (int i = 0; i < lines.length; i++) {
			pane.getChildren().add(lines[i]);
		}

		Scene scene = new Scene(pane1, 685, 600);

		scrollbar.setLayoutX(scene.getWidth() - scrollbar.getWidth());
		scrollbar.setOrientation(Orientation.VERTICAL);
		scrollbar.setPrefHeight(800);
		scrollbar.setMin(0);
		scrollbar.setMax(1500);
		scrollbar.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				pane.setLayoutY(-new_val.doubleValue());
			}
		});

		pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println(" X : " + event.getX() + " , Y : " + event.getY());
			}
		});

		pane1.getChildren().addAll(pane, scrollbar);
		Stage mapStage = new Stage();
		mapStage.setScene(scene);
		mapStage.setTitle("Map Stage");
		mapStage.setResizable(false);
		mapStage.getIcons().add(new Image("file:images/iconbzu.png"));
		mapStage.show();
	}
}

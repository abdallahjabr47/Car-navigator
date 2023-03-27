package jabr1181768;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
	final Font font1 = Font.font("Vernada", FontWeight.BOLD, 11);
	final Font font2 = Font.font("Vernada", FontWeight.BOLD, 12);
	final Font font3 = Font.font("Vernada", FontWeight.BOLD, 13);

	ArrayList<Vertex> theCities;
	String theSourceCity;
	String theTargetCity;
	Graph graph;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Group pane = new Group();
		Image icon = new Image("file:images/iconbzu.png");

		Button loadBtn = new Button("Load");
		Button pathBtn = new Button("Path");
		Button distanceBtn = new Button("Distance");
		Button mapBtn = new Button("Map");
		Button closeBtn = new Button("Close");

		Rectangle r1 = new Rectangle(40, 40, 420, 520);
		Rectangle r2 = new Rectangle(80, 280, 340, 0);

		String n = " PROJECT No.1";
		Label topText = new Label(n);
		Label l1 = new Label("From");
		Label l2 = new Label("To");

		ComboBox<String> sourceCity = new ComboBox<String>();
		ComboBox<String> targetCity = new ComboBox<String>();

		styleOfGUI(pane, loadBtn, pathBtn, distanceBtn, mapBtn, closeBtn, r1, r2, topText, l1, l2, sourceCity,
				targetCity);

		loadBtn.setOnAction(e -> {
			pathBtn.setDisable(false);
			distanceBtn.setDisable(false);
			mapBtn.setDisable(false);

			theCities = new ArrayList<Vertex>();
			graph = new Graph();
			String line;

			BufferedReader vertexBR = null;
			try {
				vertexBR = new BufferedReader(new FileReader("ExcelFiles/towns.csv"));
			} catch (FileNotFoundException e8) {
				e8.printStackTrace();
			}

			ArrayList<String> arrayListOfCities = new ArrayList<>();

			try {
				while ((line = vertexBR.readLine()) != null) {
					String[] townsInfo = line.split("-");
					if (townsInfo.length != 3) {
						vertexBR.close();
						throw new IOException("invalid line in the file, please try again! " + line);
					}
					String nameOfCity = townsInfo[0];
					int x = Integer.valueOf(townsInfo[1]);
					int y = Integer.valueOf(townsInfo[2]);
					arrayListOfCities.add(nameOfCity);
					Vertex vertex = new Vertex(nameOfCity, x, y);
					graph.addVertex(vertex);
					theCities.add(vertex);
				}
			} catch (NumberFormatException | IOException e8) {
				e8.printStackTrace();
			}

			ObservableList<String> citiesList = FXCollections.observableArrayList();

			for (String input : arrayListOfCities) {
				citiesList.add(input);
			}

			try {
				vertexBR.close();
			} catch (IOException e7) {
				e7.printStackTrace();
			}

			sourceCity.setItems(citiesList);
			targetCity.setItems(citiesList);

			BufferedReader edgeBR = null;
			try {
				edgeBR = new BufferedReader(new FileReader("ExcelFiles/roads.csv"));
			} catch (FileNotFoundException e6) {
				e6.printStackTrace();
			}
			try {
				while ((line = edgeBR.readLine()) != null) {
					String[] roadsInfo = line.split("-");
					if (roadsInfo.length != 3) {
						try {
							edgeBR.close();
						} catch (IOException e4) {
							e4.printStackTrace();
						}
						throw new IOException("invalid line in file, please try again! " + line);
					}
					graph.addUndirectedEdge(roadsInfo[0], roadsInfo[1], Double.parseDouble(roadsInfo[2]));
				}
			} catch (NumberFormatException | IOException e5) {
				e5.printStackTrace();
			}
			try {
				edgeBR.close();
			} catch (IOException e4) {
				e4.printStackTrace();
			}
			sourceCity.setValue(arrayListOfCities.get(0));
			targetCity.setValue(arrayListOfCities.get(1));
		});

		pathBtn.setOnAction(e1 -> {
			Calculation.findThePathStage(sourceCity.getValue(), targetCity.getValue(), graph);
		});

		distanceBtn.setOnAction(e2 -> {
			Calculation.CalculateDistanceStage(sourceCity.getValue(), targetCity.getValue(), graph);
		});

		mapBtn.setOnAction(e3 -> {
			Calculation.mapStage(sourceCity.getValue(), targetCity.getValue(), graph);
		});

		closeBtn.setOnAction(e -> Platform.exit());

		Scene scene = new Scene(pane, 500, 600, Color.CADETBLUE);
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(icon);
		primaryStage.setResizable(false);
		primaryStage.setTitle("AI Project 1");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void styleOfGUI(Group pane, Button loadBtn, Button pathBtn, Button distanceBtn, Button mapBtn,
			Button closeBtn, Rectangle r1, Rectangle r2, Label topText, Label l1, Label l2, ComboBox<String> sourceCity,
			ComboBox<String> targetCity) {
		r1.setStroke(Color.WHITE);
		r1.setFill(null);
		r1.setStrokeWidth(2);

		r2.setStroke(Color.WHITE);
		r2.setFill(null);
		r2.setStrokeWidth(2);

		topText.setLayoutX(165);
		topText.setLayoutY(75);
		topText.setFont(new Font("Segoe UI Black", 24));
		topText.setStyle("-fx-text-fill: #ffffff");

		loadBtn.setLayoutX(190.0);
		loadBtn.setLayoutY(140.0);
		loadBtn.setPrefHeight(30);
		loadBtn.setPrefWidth(120);
		loadBtn.setFont(font1);

		l1.setLayoutX(150.0);
		l1.setLayoutY(180.0);
		l1.setPrefHeight(35);
		l1.setPrefWidth(141.0);
		l1.setTextFill(Color.web("#fff", 1));

		l2.setLayoutX(330.0);
		l2.setLayoutY(180.0);
		l2.setPrefHeight(35);
		l2.setPrefWidth(141);
		l2.setTextFill(Color.web("#fff", 1));

		sourceCity.setLayoutX(100.0);
		sourceCity.setLayoutY(210.0);
		sourceCity.setPrefHeight(30.0);
		sourceCity.setPrefWidth(120.0);

		targetCity.setLayoutX(280.0);
		targetCity.setLayoutY(210.0);
		targetCity.setPrefHeight(30.0);
		targetCity.setPrefWidth(120.0);

		pathBtn.setLayoutX(190.0);
		pathBtn.setLayoutY(320.0);
		pathBtn.setPrefHeight(30.0);
		pathBtn.setPrefWidth(120.0);
		pathBtn.setFont(font1);
		pathBtn.setDisable(true);

		distanceBtn.setLayoutX(190.0);
		distanceBtn.setLayoutY(380.0);
		distanceBtn.setPrefHeight(30);
		distanceBtn.setPrefWidth(120);
		distanceBtn.setFont(font1);
		distanceBtn.setDisable(true);

		mapBtn.setLayoutX(190.0);
		mapBtn.setLayoutY(440.0);
		mapBtn.setPrefHeight(30.0);
		mapBtn.setPrefWidth(120.0);
		mapBtn.setFont(font1);
		mapBtn.setDisable(true);

		closeBtn.setLayoutX(190.0);
		closeBtn.setLayoutY(500.0);
		closeBtn.setPrefHeight(30.0);
		closeBtn.setPrefWidth(120.0);
		closeBtn.setFont(font1);

		pane.getChildren().add(r1);
		pane.getChildren().add(r2);
		pane.getChildren().add(topText);
		pane.getChildren().add(sourceCity);
		pane.getChildren().add(targetCity);
		pane.getChildren().add(pathBtn);
		pane.getChildren().add(distanceBtn);
		pane.getChildren().add(mapBtn);
		pane.getChildren().add(loadBtn);
		pane.getChildren().add(closeBtn);
		pane.getChildren().add(l1);
		pane.getChildren().add(l2);
	}
}

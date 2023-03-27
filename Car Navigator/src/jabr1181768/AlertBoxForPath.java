package jabr1181768;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.*;
import javafx.scene.image.*;

public class AlertBoxForPath {
	final static Font font1 = Font.font("Vernada", FontWeight.BOLD, 11);

	public static void display(String message, String str) {
		Stage primaryStage = new Stage();

		Label label = new Label();
		label.setText(message);
		label.setMinWidth(50);
		label.setMinHeight(50);
		label.setLayoutX(50);
		label.setLayoutY(50);
		label.setFont(new Font("Segoe UI Black", 12));
		label.setStyle("-fx-text-fill: #263f40");

		Button cancelBtn = new Button("cancel");
		cancelBtn.setLayoutX(200.0);
		cancelBtn.setLayoutY(470.0);
		cancelBtn.setPrefHeight(30.0);
		cancelBtn.setPrefWidth(120.0);
		cancelBtn.setFont(font1);
		cancelBtn.setOnAction(e -> primaryStage.close());

		Group pane = new Group();
		pane.getChildren().add(label);
		pane.getChildren().add(cancelBtn);
		Scene scene = new Scene(pane, 500, 550, Color.rgb(204, 255, 204));
		primaryStage.getIcons().add(new Image("file:images/iconbzu.png"));
		primaryStage.setTitle(str);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.showAndWait();
	}
}
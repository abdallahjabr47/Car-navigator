package jabr1181768;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.*;
import javafx.scene.image.*;

public class AlertBoxForDistance {

	public static void display(String message, String str) {
		Stage primaryStage = new Stage();

		Label label = new Label();
		label.setText(message);
		label.setMinWidth(50);
		label.setMinHeight(50);
		label.setLayoutX(125);
		label.setLayoutY(50);
		label.setFont(new Font("Segoe UI Black", 12));
		label.setStyle("-fx-text-fill: #263f40");

		Button closeButton = new Button("cancel");
		closeButton.setLayoutX(185);
		closeButton.setLayoutY(130);
		closeButton.setOnAction(e -> primaryStage.close());

		Group pane = new Group();
		pane.getChildren().add(label);
		pane.getChildren().add(closeButton);
		Scene scene = new Scene(pane, 400, 200, Color.rgb(255, 204, 153));
		primaryStage.getIcons().add(new Image("file:images/iconbzu.png"));
		primaryStage.setTitle(str);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.showAndWait();
	}
}
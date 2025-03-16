package org.emp.shelterhub.feature.about;

import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import org.emp.shelterhub.feature.init.WelcomeScreen;

public class AboutScreen {
    private static final String ABOUT_TITLE = "O aplikacji ShelterHub";
    private static final String ABOUT_DESCRIPTION = "ShelterHub to system zarządzania Schroniskiem Górskim Wilcza Turnia";

    public static void show(StackPane root, String username) {
        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));

        Label titleLabel = new Label(ABOUT_TITLE);
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label descriptionLabel = new Label(ABOUT_DESCRIPTION);
        descriptionLabel.setStyle("-fx-font-size: 16px;");

        // Display user data
        Label userLabel = new Label("Zalogowany jako: " + username);
        userLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2196F3;");

        Button backButton = new Button("Powrót");
        backButton.setOnAction(e -> {
            root.getChildren().clear();
            WelcomeScreen.show(root);
        });

        content.getChildren().addAll(titleLabel, descriptionLabel, userLabel, backButton);
        root.getChildren().add(content);
    }

    // Overload the original method to maintain compatibility
    public static void show(StackPane root) {
        show(root, "Gość");
    }
}
package org.emp.shelterhub.feature.about;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class AboutScreen {
    private static final String ABOUT_TITLE = "O aplikacji ShelterHub";
    private static final String ABOUT_DESCRIPTION =
            "ShelterHub to system zarządzania Schroniskiem Górskim Wilcza Turnia";

    private StackPane rootPane = new StackPane();
    private AboutScreenViewModel viewModel = new AboutScreenViewModel(rootPane);

    public void show( String username) {
//        viewModel.setRoot(root);

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
        backButton.setOnAction(
                e -> {
                    viewModel.navigateToWelcomeScreen();
                });

        content.getChildren().addAll(titleLabel, descriptionLabel, userLabel, backButton);
        root.getChildren().add(content);
    }

    public void show(StackPane root) {
        show(root, "Gość");
    }
}

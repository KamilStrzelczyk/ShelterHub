package org.emp.shelterhub.feature.init;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import org.emp.shelterhub.feature.about.AboutScreen;

public class WelcomeScreen {
    private static final String WELCOME_MESSAGE = "Witaj w systemie ShelterHub!";
    private static final String SUBTITLE_MESSAGE = "System zarządzania Schroniskiem Górskim Wilcza Turnia";

    public static void show(StackPane root) {
        // Main container with white background and shadow
        VBox loginContainer = new VBox(20);
        loginContainer.setStyle("-fx-background-color: white; -fx-background-radius: 15;");
        loginContainer.setPadding(new Insets(40));
        loginContainer.setMaxWidth(700);
        loginContainer.setAlignment(Pos.CENTER);

        // Add shadow effect
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.2));
        shadow.setRadius(20);
        loginContainer.setEffect(shadow);

        // Welcome messages
        Label welcomeLabel = new Label(WELCOME_MESSAGE);
        welcomeLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2196F3;");

        Label subtitleLabel = new Label(SUBTITLE_MESSAGE);
        subtitleLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #757575; -fx-font-weight: normal;");

        // Input fields container
        VBox inputFields = new VBox(15);
        inputFields.setAlignment(Pos.CENTER);
        inputFields.setPadding(new Insets(30, 0, 30, 0));
        inputFields.setMaxWidth(350);

        // Username field
        VBox usernameBox = new VBox(5);
        Label usernameLabel = new Label("Nazwa użytkownika");
        usernameLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #424242;");
        TextField usernameField = new TextField();
        usernameField.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 5; " +
                              "-fx-border-color: #e0e0e0; -fx-border-radius: 5; " +
                              "-fx-padding: 10; -fx-font-size: 14px;");
        usernameField.setPromptText("Wprowadź nazwę użytkownika");
        usernameBox.getChildren().addAll(usernameLabel, usernameField);

        // Password field
        VBox passwordBox = new VBox(5);
        Label passwordLabel = new Label("Hasło");
        passwordLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #424242;");
        PasswordField passwordField = new PasswordField();
        passwordField.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 5; " +
                             "-fx-border-color: #e0e0e0; -fx-border-radius: 5; " +
                             "-fx-padding: 10; -fx-font-size: 14px;");
        passwordField.setPromptText("Wprowadź hasło");
        passwordBox.getChildren().addAll(passwordLabel, passwordField);

        // Login button
        Button submitButton = new Button("Zaloguj się");
        submitButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; " +
                            "-fx-font-size: 14px; -fx-font-weight: bold; " +
                            "-fx-padding: 12 30; -fx-background-radius: 5;");
        submitButton.setMaxWidth(Double.MAX_VALUE);
        
        // Hover effect for button
        submitButton.setOnMouseEntered(e -> 
            submitButton.setStyle("-fx-background-color: #1976D2; -fx-text-fill: white; " +
                                "-fx-font-size: 14px; -fx-font-weight: bold; " +
                                "-fx-padding: 12 30; -fx-background-radius: 5;")
        );
        submitButton.setOnMouseExited(e -> 
            submitButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; " +
                                "-fx-font-size: 14px; -fx-font-weight: bold; " +
                                "-fx-padding: 12 30; -fx-background-radius: 5;")
        );

        submitButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            root.getChildren().clear();
            AboutScreen.show(root, username);
        });

        // Add all elements to the input fields container
        inputFields.getChildren().addAll(usernameBox, passwordBox, submitButton);

        // Add all elements to the main container
        loginContainer.getChildren().addAll(welcomeLabel, subtitleLabel, inputFields);

        // Set the background color of the root container
        root.setStyle("-fx-background-color: #f5f5f5;");
        
        // Center the login container in the root
        root.getChildren().add(loginContainer);
        StackPane.setAlignment(loginContainer, Pos.CENTER);
    }
}

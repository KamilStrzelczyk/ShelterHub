package org.emp.shelterhub.feature.help;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.emp.shelterhub.lib.infrastructure.utils.AppTheme;
import org.emp.shelterhub.lib.infrastructure.utils.Dimensions;

public class Help extends VBox {

    private Label helpTextLabel;
    private Label emailLabel;
    private Label phoneLabel;

    public Help() {
        initializeComponents();
        setupLayout();
        styleComponents();
    }

    private void initializeComponents() {
        helpTextLabel = new Label("W razie problemów skontaktuj się z zespołem EMP:");
        emailLabel = new Label("Email: shelterhub@emp.pl");
        phoneLabel = new Label("Telefon: +48 123 456 789");
    }

    private void setupLayout() {
        this.getChildren().addAll(helpTextLabel, emailLabel, phoneLabel);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(Dimensions.SPACING_MEDIUM);
    }

    private void styleComponents() {
        setStyle("-fx-background-color: " + AppTheme.BACKGROUND_COLOR + ";");
    }
}
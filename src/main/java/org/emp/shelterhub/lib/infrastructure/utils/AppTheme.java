package org.emp.shelterhub.lib.infrastructure.utils;

public class AppTheme {
  public static final String PRIMARY_COLOR = "#6A3A56";
  public static final String PRIMARY_COLOR_HOVER = "#5c324b";
  public static final String BACKGROUND_COLOR = "#F7E0D6";
  public static final String BACKGROUND_COLOR_LIGHT = "#f5f5f5";
  public static final String BORDER_COLOR = "#e0e0e0";
  public static final String TEXT_COLOR_PRIMARY = "#424242";
  public static final String TEXT_COLOR_SECONDARY = "#757575";
  public static final String ERROR_COLOR = "#FF0000"; // Czerwony
  public static final String SUCCESS_COLOR = "#00FF00"; // Zielony
  public static final String CARD_BACKGROUND_COLOR = "#FFFFFF"; // Biały
  public static final String SECONDARY_COLOR = "#3A6A56"; // Przykładowy kolor
  public static final String TEXT_COLOR_LIGHT = "#FFFFFF"; // Biały

  public static String getButtonStyle(String bgColor) {
    return "-fx-background-color: "
        + bgColor
        + "; "
        + "-fx-text-fill: white; "
        + "-fx-font-size: "
        + Dimensions.FONT_SIZE_BODY
        + "px; "
        + "-fx-font-weight: bold; "
        + "-fx-padding: 12 30; "
        + "-fx-background-radius: "
        + Dimensions.BORDER_RADIUS_SMALL
        + ";";
  }

  public static String getTextFieldStyle() {
    return "-fx-background-color: "
        + BACKGROUND_COLOR_LIGHT
        + "; "
        + "-fx-background-radius: "
        + Dimensions.BORDER_RADIUS_SMALL
        + "; "
        + "-fx-border-color: "
        + BORDER_COLOR
        + "; "
        + "-fx-border-radius: "
        + Dimensions.BORDER_RADIUS_SMALL
        + "; "
        + "-fx-padding: "
        + Dimensions.INPUT_FIELD_PADDING
        + "; "
        + "-fx-font-size: "
        + Dimensions.FONT_SIZE_BODY
        + "px;";
  }

  public static String getLabelStyle(int fontSize, boolean isBold, String color) {
    return "-fx-font-size: "
        + fontSize
        + "px"
        + (isBold ? "; -fx-font-weight: bold" : "")
        + "; -fx-text-fill: "
        + color
        + ";";
  }

  public static String getContainerStyle() {
    return "-fx-background-color: #CCCCCC; "
        + "-fx-padding: "
        + Dimensions.SPACING_MEDIUM
        + "; "
        + "-fx-spacing: "
        + Dimensions.SPACING_LARGE
        + ";";
  }
}

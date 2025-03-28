package org.emp.shelterhub.lib.infrastructure.utils;

/**
 * Central class for managing application theme properties including colors and fonts.
 */
public class AppTheme {
    // Color scheme
    public static final String PRIMARY_COLOR = "#6A3A56";
    public static final String PRIMARY_COLOR_HOVER = "#5c324b";
    public static final String BACKGROUND_COLOR = "#F7E0D6";
    public static final String BACKGROUND_COLOR_LIGHT = "#f5f5f5";
    public static final String BORDER_COLOR = "#e0e0e0";
    public static final String TEXT_COLOR_PRIMARY = "#424242";
    public static final String TEXT_COLOR_SECONDARY = "#757575";
    
    // Font sizes
    public static final int FONT_SIZE_TITLE = 28;
    public static final int FONT_SIZE_SUBTITLE = 16;
    public static final int FONT_SIZE_BODY = 14;
    
    // Spacing and padding
    public static final int SPACING_SMALL = 5;
    public static final int SPACING_MEDIUM = 10;
    public static final int SPACING_LARGE = 20;
    public static final int SPACING_XLARGE = 25;
    
    // Border radius
    public static final int BORDER_RADIUS_SMALL = 5;
    public static final int BORDER_RADIUS_MEDIUM = 10;
    public static final int BORDER_RADIUS_LARGE = 15;
    
    // Shadow properties
    public static final int SHADOW_RADIUS = 20;
    public static final double SHADOW_OPACITY = 0.2;
    
    // Common style strings
    public static String getButtonStyle(String bgColor) {
        return "-fx-background-color: " + bgColor + "; " +
               "-fx-text-fill: white; " +
               "-fx-font-size: " + FONT_SIZE_BODY + "px; " +
               "-fx-font-weight: bold; " +
               "-fx-padding: 12 30; " +
               "-fx-background-radius: " + BORDER_RADIUS_SMALL + ";";
    }
    
    public static String getTextFieldStyle() {
        return "-fx-background-color: " + BACKGROUND_COLOR_LIGHT + "; " +
               "-fx-background-radius: " + BORDER_RADIUS_SMALL + "; " +
               "-fx-border-color: " + BORDER_COLOR + "; " + 
               "-fx-border-radius: " + BORDER_RADIUS_SMALL + "; " +
               "-fx-padding: 10; " +
               "-fx-font-size: " + FONT_SIZE_BODY + "px;";
    }
    
    public static String getLabelStyle(int fontSize, boolean isBold, String color) {
        return "-fx-font-size: " + fontSize + "px" +
               (isBold ? "; -fx-font-weight: bold" : "") +
               "; -fx-text-fill: " + color + ";";
    }
} 
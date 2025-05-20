package org.emp.shelterhub.feature.navigation;

public enum NavigationBarButton {

    ROOM("Pokoje"),
    EMPLOYEE("Pracownicy"),
    SCHEDULER("Grafik"),
    REPORT("Raport"),
    SETTINGS("Ustawienia"),
    HELP("Pomoc");

    private final String name;

    NavigationBarButton(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
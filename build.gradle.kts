plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("checkstyle")
    id("com.diffplug.spotless") version "6.25.0"
    id("org.beryx.jlink") version "3.1.1"
}

group = "org.emp.shelterhub"
version = "0.1.0"

application {
    mainClass.set("org.emp.shelterhub.app.Main")
}

checkstyle {
    toolVersion = "10.21.4"
    configFile = file("${rootProject.projectDir}/checkstyle.xml")
}

spotless {
    java {
        googleJavaFormat()
    }
}

tasks.register("fixAndCheck") {
    group = "verification"
    description = "Uruchamia checkstyle, spotlessCheck, a na końcu poprawia kod przez spotlessApply"

    dependsOn("checkstyleMain", "checkstyleTest", "spotlessCheck")
    finalizedBy("spotlessApply")
}

tasks.withType<Checkstyle>().configureEach {
    config = resources.text.fromFile("${rootProject.projectDir}/checkstyle.xml");
    configDirectory.set(file("${rootProject.projectDir}/checkstyle.xml"));
    ignoreFailures = true;
    reports {
        xml.required.set(true);
        html.required.set(false);
    }
    exclude("**/module-info.java");
}

repositories {
    mavenCentral()
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    implementation("org.openjfx:javafx-controls:21")
    implementation("org.openjfx:javafx-fxml:21")
    implementation("org.openjfx:javafx-graphics:21.0.2")
    implementation("org.openjfx:javafx-base:21")

    implementation("org.xerial:sqlite-jdbc:3.49.1.0")
    implementation("io.reactivex.rxjava3:rxjava:3.1.8")
    implementation("org.apache.poi:poi-ooxml:5.4.0")
    implementation("org.apache.poi:poi:5.2.5")
    implementation("org.apache.commons:commons-math3:3.6.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}


tasks.test {
    useJUnitPlatform()
}


jlink {
    options = listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages")
    launcher {
        name = "shelterhub"
    }
    jpackage {
        imageName = "ShelterHubApp"
        installerName = "ShelterHubInstaller"
        installerType = "exe"
    }
    forceMerge("javafx.*", "org.xerial.sqlite-jdbc")
}

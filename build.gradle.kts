plugins {
    id("java")
    id("application")
    id("checkstyle")
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("com.diffplug.spotless") version "6.25.0"
}

group = "org.emp.shelterhub"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("org.emp.shelterhub.app.Main")
    mainModule.set("org.emp.shelterhub")
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

tasks.named("spotlessApply") {
    mustRunAfter("checkstyleMain")
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
    implementation("org.xerial:sqlite-jdbc:3.49.1.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

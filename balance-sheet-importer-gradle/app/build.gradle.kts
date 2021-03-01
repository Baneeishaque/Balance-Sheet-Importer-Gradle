plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    mavenCentral() 
}

dependencies {
    // Use JUnit Jupiter API for testing.
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")

    // Use JUnit Jupiter Engine for testing.
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:29.0-jre")
}

application {
    // Define the main class for the application.
    mainClass.set("ndk.balance_sheet_importer_gradle.App")
}

tasks.test {
    // Use junit platform for unit tests.
    useJUnitPlatform()
}
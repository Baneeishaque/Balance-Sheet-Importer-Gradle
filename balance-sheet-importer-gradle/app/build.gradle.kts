plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    mavenCentral() 
}

dependencies {

    implementation("com.github.miachm.sods:SODS:1.3.0")
    
    // Use JUnit Jupiter API for testing.
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.3")

    // Use JUnit Jupiter Engine for testing.
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:30.1.1-jre")
}

application {
    // Define the main class for the application.
    mainClass.set("ndk.balance_sheet_importer_gradle.App")
}

tasks.test {
    // Use junit platform for unit tests.
    useJUnitPlatform()
}

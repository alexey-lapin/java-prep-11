plugins {
    java
    id("com.github.ben-manes.versions")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("com.h2database:h2:1.4.200")

    testImplementation("org.assertj:assertj-core:3.21.0")
    testImplementation("org.openjdk.jol:jol-core:0.16")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = JavaVersion.VERSION_11.majorVersion
        targetCompatibility = JavaVersion.VERSION_11.majorVersion
    }

    withType<Test> {
        useJUnitPlatform()
        systemProperty("jdk.attach.allowAttachSelf", "")
        systemProperty("gradle.build.dir", project.buildDir.toString())
        systemProperty("gradle.src.test.java.dir", sourceSets.test.get().java.srcDirs.first().toString())
    }

    dependencyUpdates {
        checkConstraints = true
        resolutionStrategy {
            componentSelection {
                all {
                    val rejected = listOf("alpha", "beta", "rc", "cr", "m", "preview", "b", "ea")
                        .map { qualifier -> Regex("(?i).*[.-]$qualifier[.\\d-+]*") }
                        .any { it.matches(candidate.version) }
                    if (rejected) {
                        reject("Release candidate")
                    }
                }
            }
        }
    }
}

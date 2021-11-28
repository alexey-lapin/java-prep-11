plugins {
    java
    id("com.github.ben-manes.versions")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("com.h2database:h2:1.4.200")
    testImplementation("com.google.jimfs:jimfs:1.2")

    testImplementation("org.assertj:assertj-core:3.21.0")
    testImplementation("org.openjdk.jol:jol-core:0.16")

    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("org.awaitility:awaitility:4.2.0")
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

    register<JavaExec>("runTestMain") {
        val packagePrefix = "com.github.alexeylapin.ocp"
        var testMainClass = project.findProperty("mainClass") as String
        testMainClass = if (testMainClass.startsWith(".")) packagePrefix + testMainClass else testMainClass
        mainClass.set(testMainClass)
        standardInput = System.`in`
        standardOutput = System.out
        classpath += sourceSets.test.get().runtimeClasspath
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

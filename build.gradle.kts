plugins {
    java
    `java-library`
    `kotlin-dsl`
    `maven-publish`
    id("com.diffplug.spotless") version "5.8.2"
}

allprojects {
    group = "net.fabricmc"
    version = "1.0-SNAPSHOT"

    apply {
        plugin("java")
        //plugin("checkstyle")
        //plugin("net.cadixdev.licenser")
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("java")
        plugin("maven-publish")
        plugin("com.diffplug.spotless")
    }

    dependencies {
        implementation("org.jetbrains:annotations:19.0.0")
    }

    tasks.javadoc {
        isEnabled = true

        standardOptions {
            source = "8"
            encoding = "UTF-8"
            charset("UTF-8")
            memberLevel = JavadocMemberLevel.PACKAGE

            classpath = sourceSets["main"].compileClasspath.distinct()
            isFailOnError = false

            links?.addAll(mutableListOf(
                    "https://docs.oracle.com/javase/8/docs/api/"
            ))

            // Disable the crazy super-strict doclint tool in Java 8
            addStringOption("Xdoclint:none", "-quiet")
        }
    }

    val javadocJar by tasks.registering(Jar::class) {
        group = "build"
        dependsOn(tasks.javadoc.get())
        from(tasks.javadoc)
    }

    tasks.build {
        dependsOn(javadocJar.get())
    }

    artifacts {
        archives(javadocJar)
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])

                // TODO: Artifacts
                //artifact("javadocJar") {
                //    builtBy(javadocJar)
                //}

                pom {
                    artifactId = project.base.archivesBaseName
                    name.set(project.name)
                    description.set(project.description)

                    licenses {
                        name.set("Apache-2.0")
                        url.set("https://opensource.org/licenses/Apache-2.0")
                    }

                    scm {
                        url.set("github.com/FabricMC/fabric-mapping-library")
                        connection.set("scm:git:git://github.com/FabricMC/fabric-mapping-library.git")
                        developerConnection.set("scm:git:ssh://github.com/FabricMC/fabric-mapping-library.git")
                    }

                    issueManagement {
                        system.set("GitHub")
                        url.set("github.com/FabricMC/fabric-mapping-library/issues")
                    }
                }
            }
        }

        repositories {
            mavenLocal() // uncomment for testing

            if (project.hasProperty("mavenPass")) {
                maven(url = "http://mavenupload.modmuss50.me/") {
                    credentials {
                        username = "buildslave"
                        password = project.properties["mavenPass"] as String?
                    }
                }
            }
        }
    }
}

extra("asm") {
    val asmVersion = "9.0"

    dependencies {
        implementation("org.ow2.asm:asm:$asmVersion")
        implementation("org.ow2.asm:asm-commons:$asmVersion")
    }

    tasks.javadoc {
        standardOptions {
            links?.let { it += "https://asm.ow2.io/javadoc/" }
        }
    }
}

extra("lorenz") {
    dependencies {
        implementation(project(":tree"))
        implementation("org.cadixdev:lorenz:0.5.3")
    }

    tasks.javadoc {
        standardOptions {
            links?.addAll(mutableListOf(
                    // TOOD: Add lorenz javadoc link
            ))
        }
    }
}

extra("tree")
extra("util")

// Formats

format("csrg")
//format("enigma")
format("proguard")
format("srg")
format("tiny")

fun Project.extra(module: String, action: (Project).() -> Unit = { Unit }) = project(module) {
    base.archivesBaseName = rootProject.name + "-extra-$module"

    dependencies {
        implementation(rootProject.project(":core"))
    }

    action.invoke(this)
}

fun Project.format(module: String, action: (Project).() -> Unit = { Unit }) = project(module) {
    base.archivesBaseName = rootProject.name + "-format-$module"

    dependencies {
        implementation(rootProject.project(":core"))
    }

    action.invoke(this)
}

fun Javadoc.standardOptions(action: (StandardJavadocDocletOptions).() -> Unit) {
    options {
        (this as? StandardJavadocDocletOptions)?.apply {
            action.invoke(this)
        }
    }
}

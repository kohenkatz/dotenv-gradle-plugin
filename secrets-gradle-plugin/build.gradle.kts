// Copyright 2022 Moshe Katz
// Based on work Copyright 2021 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

plugins {
    `java-gradle-plugin`
    `maven-publish`
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    compileOnly("com.android.tools.build:gradle:7.0.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.10")
    testImplementation("com.android.tools.build:gradle:7.0.0-beta04")
    testImplementation("junit:junit:4.13.1")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
}

gradlePlugin {
    plugins {
        create(PluginInfo.name) {
            id = PluginInfo.group
            implementationClass = PluginInfo.implementationClass
            displayName = PluginInfo.displayName
            description = PluginInfo.description
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenPub") {
            group = PluginInfo.group
            artifactId = PluginInfo.artifactId
            version = PluginInfo.version

            pom {
                name.set(PluginInfo.artifactId)
                description.set(PluginInfo.description)
                url.set(PluginInfo.url)

                scm {
                    connection.set("scm:git@github.com:kohenkatz/dotenv-gradle-plugin.git")
                    developerConnection.set("scm:git@github.com:kohenkatz/dotenv-gradle-plugin.git")
                    url.set(PluginInfo.url)
                }

                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("repo")
                    }
                }

                developers {
                    developer {
                        name.set("Moshe Katz")
                    }
                }
            }
        }
    }
    repositories {
        maven {
            name = "localPluginRepository"
            url = uri("build/repository")
        }
        mavenLocal()
    }
}

project(":dotenv-gradle-plugin") {
    version = PluginInfo.version
}

object PluginInfo {
    const val artifactId =
        "net.ymkatz.dotenv-gradle-plugin.gradle.plugin"
    const val description = "A Gradle plugin for providing Environment Variables (including `.env`) to an Android project."
    const val displayName = "DotEnv Gradle Plugin for Android"
    const val group = "net.ymkatz.dotenv-gradle-plugin"
    const val implementationClass =
        "net.ymkatz.dotenv_gradle_plugin.DotenvPlugin"
    const val name = "dotenvGradlePlugin"
    const val url = "https://github.com/kohenkatz/dotenv-gradle-plugin"
    const val version = "0.1.0"
}
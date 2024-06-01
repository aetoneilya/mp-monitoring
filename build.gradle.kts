import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension

plugins {
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.openapi.generator") version "7.1.0"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.23"
}

group = "com.machrist"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.swagger.core.v3:swagger-core:2.2.19")
    implementation("org.jetbrains.kotlin.plugin.allopen:org.jetbrains.kotlin.plugin.allopen.gradle.plugin:1.9.23")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.clickhouse:clickhouse-http-client:0.6.0")
    implementation("org.apache.httpcomponents.client5:httpclient5:5.3.1")
    implementation("org.slf4j:slf4j-api:2.0.11")
    implementation("org.apache.commons:commons-math3:3.6.1")
    implementation("org.postgresql:postgresql:42.7.2")
    implementation("org.lz4:lz4-java:1.8.0")

    implementation("org.reactivestreams:reactive-streams:1.0.4")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("io.projectreactor:reactor-test")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("io.projectreactor:reactor-core:3.6.6")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0-RC")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.9.0-RC")


    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:0.12.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")



}

allOpen {
    annotations("javax.persistence.Entity", "javax.persistence.MappedSuperclass", "javax.persistence.Embedabble")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
    dependsOn("openApiGenerate")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

sourceSets {
    getByName("main") {
        java {
            srcDir("${layout.buildDirectory.get()}/generated/src/main/kotlin")
        }
    }
}

configure<OpenApiGeneratorGenerateExtension> {
    generatorName.set("kotlin-spring")
    inputSpec.set("$rootDir/src/main/resources/open-api/api.yaml")
    outputDir.set("${layout.buildDirectory.dir("generated").get()}")
    apiPackage = "com.machrist.mpmonitoring.openapi"
    invokerPackage = "com.machrist.mpmonitoring"
    modelPackage = "com.machrist.mpmonitoring.openapi.dto"
    configOptions.set(
        mapOf(
            "dateLibrary" to "java8",
            "interfaceOnly" to "true",
            "useSpringBoot3" to "true",
            "useTags" to "true",
            "reactive" to "true",
        ),
    )
}

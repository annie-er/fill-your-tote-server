plugins {
    java
    id("org.springframework.boot") version "3.5.4"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.hibernate.orm") version "6.6.22.Final"
    id("org.graalvm.buildtools.native") version "0.10.6"
}

group = "com.fillyourtote"
version = "0.0.1-SNAPSHOT"
description = "fill-your-tote-server"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-security")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.testcontainers:postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.springframework.security:spring-security-test")
}

hibernate {
    enhancement {
        enableAssociationManagement = true
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.jar {
    enabled = false
    archiveClassifier.set("")
}

tasks.bootJar {
    enabled = true
    archiveClassifier.set("")
}
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    id("java")
    id("org.springframework.boot") version "3.0.0-RC2"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.graalvm.buildtools.native") version "0.9.17"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = org.gradle.api.JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

springBoot {
    buildInfo()
}

tasks {
    named<Test>("test") {
        useJUnitPlatform()
    }

    named<BootBuildImage>("bootBuildImage") {
        environment.set(hashMapOf(
            "BP_NATIVE_IMAGE" to "true",
            // compression reduces the binary from ~100mb to about 40mb which improves the boot time (down to 0.03 from 0.08)
            "BP_BINARY_COMPRESSION_METHOD" to "upx"
        ))
    }
}

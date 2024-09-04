plugins {
    java
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "se.systementor"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(20)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

/*dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.github.javafaker:javafaker:1.0.2") { exclude ("org.yaml") }
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.thymeleaf:thymeleaf-spring6")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
    //implementation("jakarta.mail:jakarta.mail-api:2.1.2")
    //implementation("com.sun.mail:javax.mail:1.6.2")
    implementation("org.springframework.boot:spring-boot-starter-mail:1.2.0.RELEASE")
    implementation("com.sun.mail:jakarta.mail:2.0.1")
    implementation("jakarta.activation:jakarta.activation-api:2.0.1")

    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.mysql:mysql-connector-j")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}*/

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.github.javafaker:javafaker:1.0.2") { exclude ("org.yaml") }
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.thymeleaf:thymeleaf-spring6")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")

    // Replace outdated mail dependency with updated ones
    implementation("org.springframework.boot:spring-boot-starter-mail")
    // implementation("com.sun.mail:jakarta.mail:2.0.1")
    // implementation("jakarta.activation:jakarta.activation-api:2.0.1")
    //implementation("com.sun.mail:jakarta.mail")
    // https://mvnrepository.com/artifact/org.eclipse.angus/jakarta.mail
    implementation ("org.eclipse.angus:jakarta.mail:2.0.3")

    // https://mvnrepository.com/artifact/jakarta.mail/jakarta.mail-api
    implementation ("jakarta.mail:jakarta.mail-api:2.1.3")

    implementation("jakarta.activation:jakarta.activation-api")

    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.mysql:mysql-connector-j")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

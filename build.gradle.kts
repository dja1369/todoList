import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.6"
	id("io.spring.dependency-management") version "1.1.5"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
	kotlin("plugin.jpa") version "1.9.24"
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.Embeddable")
	annotation("jakarta.persistence.MappedSuperclass")
}

group = "com"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}
private val jwtVersion = "0.11.5"
private val swaggerVersion = "2.5.0"

dependencies {
	// web
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	// swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$swaggerVersion")
	//security
	implementation("org.springframework.boot:spring-boot-starter-security")
	// jwt
	implementation("io.jsonwebtoken:jjwt-api:$jwtVersion")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:$jwtVersion")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jwtVersion")
	// serialization
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	// logger
	implementation ("io.github.oshai:kotlin-logging-jvm:5.1.0")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	// devtool
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	// docker
	testAndDevelopmentOnly("org.springframework.boot:spring-boot-docker-compose")
	// database
	runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
	// test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "21"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}


plugins {
    application
}

dependencies {
    // https://mvnrepository.com/artifact/com.hashicorp/cdktf-provider-aws
    implementation("com.hashicorp:cdktf-provider-aws:17.0.6")
    // https://mvnrepository.com/artifact/software.constructs/constructs
    implementation("software.constructs:constructs:10.2.70")
}

application {
    mainClass.set("nl.vintik.sample.infra.InfrastructureAppKt")
}

tasks.named("run") {
    dependsOn(":infra-aws:packageDistribution")
}
repositories {
    mavenCentral()
}

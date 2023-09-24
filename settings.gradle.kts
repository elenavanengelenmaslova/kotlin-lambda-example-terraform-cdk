pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    
}
rootProject.name = "kotlin-lambda-example-terraform-cdk"
include(":infra-aws")
project(":infra-aws").projectDir = file("software/infra/aws")
//include(":products-on-crac")
//project(":products-on-crac").projectDir = file("software/products-on-crac")
include(":cdk")
project(":cdk").projectDir = file("cdk")
pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    
}
rootProject.name = "kotlin-lambda-example-terraform-cdk"
include(":product")
project(":product").projectDir = file("software/product")
//include(":products-on-crac")
//project(":products-on-crac").projectDir = file("software/products-on-crac")
include(":cdk")
project(":cdk").projectDir = file("cdk")
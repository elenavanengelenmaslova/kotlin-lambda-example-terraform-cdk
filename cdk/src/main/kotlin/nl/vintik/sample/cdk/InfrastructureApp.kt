package nl.vintik.sample.cdk

import com.hashicorp.cdktf.App

fun main() {
    val app = App()
    InfrastructureJvmArm64Stack(app, "Terraform-Cdk-Kotlin-Lambda-JVM-Arm64-Example")
    app.synth()
}

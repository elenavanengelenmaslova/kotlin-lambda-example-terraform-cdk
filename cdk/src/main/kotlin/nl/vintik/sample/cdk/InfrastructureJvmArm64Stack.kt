package nl.vintik.sample.cdk

import com.hashicorp.cdktf.*
import com.hashicorp.cdktf.providers.aws.lambda_function.LambdaFunction
import com.hashicorp.cdktf.providers.aws.lambda_function.LambdaFunctionConfig
import com.hashicorp.cdktf.providers.aws.provider.AwsProvider
import com.hashicorp.cdktf.providers.aws.provider.AwsProviderConfig
import software.constructs.Construct


class InfrastructureJvmArm64Stack(scope: Construct, id: String) : TerraformStack(scope, id) {

    init {
        // Get the region from environment variables
        val region = System.getenv("DEPLOY_TARGET_REGION")

        // Configure the AWS Provider
        AwsProvider(this, "Aws", AwsProviderConfig.builder().region(region).build())

        val function = LambdaFunction(this, "LambdaFunction", LambdaFunctionConfig.builder()
            .functionName("Terraform-Cdk-Kotlin-Lambda-JVM-Arm64-Fun")
            .handler("nl.vintik.sample.KotlinLambda::handleRequest")
            .runtime("java17")
            .filename("../build/dist/function.zip")
            .architectures(listOf("arm64"))
            .memorySize(512)
            .timeout(120)
            .build())
    }
}

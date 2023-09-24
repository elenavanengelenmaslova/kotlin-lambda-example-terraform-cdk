package nl.vintik.sample.cdk

import com.hashicorp.cdktf.TerraformStack
import com.hashicorp.cdktf.providers.aws.dynamodb_table.DynamodbTable
import com.hashicorp.cdktf.providers.aws.dynamodb_table.DynamodbTableAttribute
import com.hashicorp.cdktf.providers.aws.dynamodb_table.DynamodbTableConfig
import com.hashicorp.cdktf.providers.aws.iam_policy.IamPolicy
import com.hashicorp.cdktf.providers.aws.iam_policy.IamPolicyConfig
import com.hashicorp.cdktf.providers.aws.iam_role.IamRole
import com.hashicorp.cdktf.providers.aws.iam_role.IamRoleConfig
import com.hashicorp.cdktf.providers.aws.iam_role_policy.IamRolePolicy
import com.hashicorp.cdktf.providers.aws.iam_role_policy.IamRolePolicyConfig
import com.hashicorp.cdktf.providers.aws.lambda_function.LambdaFunction
import com.hashicorp.cdktf.providers.aws.lambda_function.LambdaFunctionConfig
import com.hashicorp.cdktf.providers.aws.provider.AwsProvider
import com.hashicorp.cdktf.providers.aws.provider.AwsProviderConfig
import software.constructs.Construct


class InfrastructureJvmArm64Stack(
    scope: Construct,
    id: String,
) : TerraformStack(scope, id) {

    init {
        // Get the region from environment variables
        val region =
            System.getenv("DEPLOY_TARGET_REGION")

        // Configure the AWS Provider
        AwsProvider(
            this,
            "Aws",
            AwsProviderConfig.builder().region(region)
                .build()
        )

        val tableName = "Products-Terraform-Cdk-Example"
        val productsTable = DynamodbTable(
            this, tableName,
            DynamodbTableConfig.builder()
                .name(tableName)
                .hashKey("id")
                .attribute(
                    listOf(
                        DynamodbTableAttribute.builder()
                            .name("id")
                            .type("S")
                            .build()
                    )
                )
                .billingMode("PAY_PER_REQUEST")
                .build()
        )


        val lambdaRole = IamRole(
            this, "LambdaExecutionRole",
            IamRoleConfig.builder().assumeRolePolicy(
                """{
                    "Version": "2012-10-17",
                    "Statement": [
                        {
                            "Action": "sts:AssumeRole",
                            "Principal": {"Service": "lambda.amazonaws.com"},
                            "Effect": "Allow"
                        }
                    ]
                }"""
            ).build()
        )

        val policy = IamPolicy(
            this, "LambdaPolicy",
            IamPolicyConfig.builder()
                .policy(
                    """
            {
                "Version": "2012-10-17",
                "Statement": [
                    {
                        "Effect": "Allow",
                        "Action": [
                            "logs:CreateLogGroup",
                            "logs:CreateLogStream",
                            "logs:PutLogEvents"
                        ],
                        "Resource": "arn:aws:logs:*:*:*"
                    },
                     {
            "Effect": "Allow",
            "Action": [
                "dynamodb:GetItem",
                "dynamodb:Query",
                "dynamodb:Scan"
            ],
            "Resource": "${productsTable.arn}"
        }
                ]
            }
        """
                ).build()
        )

        IamRolePolicy(
            this, "LambdaPolicy",
            IamRolePolicyConfig.builder()
                .policy(policy.arn)
                .role(lambdaRole.arn).build()
        )


        val function = LambdaFunction(
            this,
            "LambdaFunction",
            LambdaFunctionConfig.builder()
                .functionName("Terraform-Cdk-Kotlin-Lambda-JVM-Arm64-Fun")
                .handler("nl.vintik.sample.KotlinLambda::handleRequest")
                .runtime("java17")
                .filename("../build/dist/function.zip")
                .architectures(listOf("arm64"))
                .role(lambdaRole.arn)
                .memorySize(512)
                .timeout(120)
                .build()
        )
    }
}

```
kotlin-lambda-example-terraform-cdk/
│
├── build.gradle.kts  // Root build file
├── settings.gradle.kts  // Contains include statements for subprojects
│
├── software/  // Holds all the business logic and application code
│   ├── domain/
│   │   ├── src/
│   │   └── build.gradle.kts
│   ├── application/
│   │   ├── src/
│   │   └── build.gradle.kts
│   └── infra/
│       └── aws/  // AWS-specific code, including AWS Lambda
│           ├── src/
│           └── build.gradle.kts
│
└── cdk/  // Terraform CDK Kotlin code
    ├── build.gradle.kts  // Build file for cdk
    └── aws/
        └── src/
```
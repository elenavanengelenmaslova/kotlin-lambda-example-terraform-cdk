package nl.vintik.sample

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import nl.vintik.sample.model.Product
import nl.vintik.sample.model.ProductRequest

@Suppress("UNUSED")
class KotlinLambda : RequestHandler<ProductRequest, String> {
    //private val productsController = ProductsController(ProductsService(Product.productTable))

    override fun handleRequest(event: ProductRequest, context: Context): String {//Product? {
        return "Hello world!"
    }
}
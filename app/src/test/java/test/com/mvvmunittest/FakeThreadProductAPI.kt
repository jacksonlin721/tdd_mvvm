package test.com.mvvmunittest

import test.com.mvvmunittest.api.IProductAPI
import test.com.mvvmunittest.api.ProductResponse

class FakeThreadProductAPI : IProductAPI {

    override fun getProduct(productId: String, loadAPICallBack: IProductAPI.LoadAPICallBack) {
        Thread {
            val productResponse = ProductResponse()
            productResponse.id = "pixel3"
            productResponse.name = "Google Pixel 3"
            productResponse.desc = "5.5吋螢幕"
            productResponse.price = 27000
            loadAPICallBack.onGetResult(productResponse)
        }.start()
    }
}
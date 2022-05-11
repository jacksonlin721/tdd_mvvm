package test.com.mvvmunittest.api

import android.os.Handler
import java.lang.Exception

interface IProductAPI {
    interface LoadAPICallBack {
        fun onGetResult(productResponse: ProductResponse)
    }

    fun getProduct(productId:String, loadAPICallBack: LoadAPICallBack)
}

open class ProductAPI: IProductAPI {

    override fun getProduct(productId:String, loadAPICallBack: IProductAPI.LoadAPICallBack) {
        System.out.println("[getProduct] in api")
        //模擬從API取得資料
        try {
            Thread.sleep(1000)
        } catch (e: Exception) {}

        val productResponse = ProductResponse()
        productResponse.id = "pixel3"
        productResponse.name = "Google Pixel 3"
        productResponse.desc = "5.5吋螢幕"
        productResponse.price = 27000
        loadAPICallBack.onGetResult(productResponse)
    }
}
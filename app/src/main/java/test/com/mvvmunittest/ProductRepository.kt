package test.com.mvvmunittest

import test.com.mvvmunittest.api.IProductAPI
import test.com.mvvmunittest.api.ProductResponse

interface IProductRepository {
    fun getProduct(productId: String, loadProductCallback: LoadProductCallback)

    fun buy(id: String, items: Int, callback: BuyProductCallback)

    interface LoadProductCallback {

        fun onProductResult(productResponse: ProductResponse)
    }

    interface BuyProductCallback {

        fun onBuyResult(isSuccess: Boolean)
    }

}

class ProductRepository(private val productAPI: IProductAPI) : IProductRepository {

    override fun buy(id: String, items: Int, callback: IProductRepository.BuyProductCallback) {
        //假設買超過10份就會失敗
        if (items <= 10) {
            //模擬購買成功
            callback.onBuyResult(true)
        }else{
            callback.onBuyResult(false)
        }
    }

    override fun getProduct(productId: String, loadProductCallback: IProductRepository.LoadProductCallback) {
        System.out.println("[getProduct] in repo")
        productAPI.getProduct(productId, object : IProductAPI.LoadAPICallBack {
            override fun onGetResult(productResponse: ProductResponse) {
                System.out.println("[onGetResult] in repo")
                loadProductCallback.onProductResult(productResponse)
            }
        })
    }
}
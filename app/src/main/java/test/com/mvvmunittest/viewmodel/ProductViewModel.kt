package test.com.mvvmunittest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import test.com.mvvmunittest.view.Event
import test.com.mvvmunittest.IProductRepository
import test.com.mvvmunittest.api.ProductResponse
import test.com.mvvmunittest.instrumentedtest.SharedPreferenceManager
import test.com.mvvmunittest.view.ProductActivity

open class ProductViewModel (private val productRepository: IProductRepository) : ViewModel(){
    var productId: MutableLiveData<String> = MutableLiveData()
    var productName: MutableLiveData<String> = MutableLiveData()
    var productDesc: MutableLiveData<String> = MutableLiveData()
    var productPrice: MutableLiveData<Int> = MutableLiveData()
    var productItems: MutableLiveData<String> = MutableLiveData()
    var totalPrice: MutableLiveData<String> = MutableLiveData()

    var alertText: MutableLiveData<Event<String>> = MutableLiveData()
    var buySuccessText: MutableLiveData<Event<String>> = MutableLiveData()

    fun getProduct(productId: String) {
        this.productId.value = productId
        System.out.println("[getProduct] id= $productId")
        productRepository.getProduct(productId, object : IProductRepository.LoadProductCallback {
            override fun onProductResult(productResponse: ProductResponse) {
                productName.value = productResponse.name
                productDesc.value = productResponse.desc
                productPrice.value = productResponse.price
                totalPrice.value = "0"
            }
        })
    }

    fun buy() {
        val productId = productId.value ?: ""
        val numbers = (productItems.value ?: "0").toInt()

        productRepository.buy(productId, numbers, object : IProductRepository.BuyProductCallback {
            override fun onBuyResult(isSuccess: Boolean) {
                if (isSuccess) {
                    buySuccessText.value = Event("Buy success")
                    totalPrice.value = (productPrice.value!!.toInt() * numbers).toString()
                } else {
                    alertText.value = Event("Buy fail")
                }
            }
        })
    }

    fun saveProductOrder(productActivity: ProductActivity, quenty: String) {
        productRepository.setPreferenceManager(
            sharedPreferenceManager = SharedPreferenceManager(productActivity)
        )
        productRepository.saveProductQuenty(quenty)
    }
}
package test.com.mvvmunittest

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import test.com.mvvmunittest.api.ProductAPI
import test.com.mvvmunittest.databinding.ActivityProductBinding

class ProductActivity : AppCompatActivity() {

    private val productId = "pixel3"

    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val dataBinding = DataBindingUtil.setContentView<ActivityProductBinding>(this, R.layout.activity_product)

//        改用LiveData後，註解這段
//        val productAPI = ProductAPI()
//        val productRepository = ProductRepository(productAPI)
//
//        val productViewModel = ProductViewModel(productRepository)

        val productAPI = ProductAPI()
        val productRepository = ProductRepository(productAPI)
        productViewModel =
            ViewModelProvider(this, ProductViewModelFactory(productRepository)).get(ProductViewModel::class.java)

        dataBinding.productViewModel = productViewModel

        //加這一段就可以讓model有變就更新回UI
        dataBinding.lifecycleOwner = this

        productViewModel.getProduct(productId)

        productViewModel.alertText.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                val builder = AlertDialog.Builder(this)
                builder.setMessage(it).setTitle("錯誤")
                builder.show()
            }
        })

        productViewModel.buySuccessText.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
    }

}
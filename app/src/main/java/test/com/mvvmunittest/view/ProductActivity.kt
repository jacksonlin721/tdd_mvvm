package test.com.mvvmunittest.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import test.com.mvvmunittest.ProductRepository
import test.com.mvvmunittest.R
import test.com.mvvmunittest.api.ProductAPI
import test.com.mvvmunittest.databinding.ActivityProductBinding
import test.com.mvvmunittest.viewmodel.ProductViewModel
import test.com.mvvmunittest.viewmodel.ProductViewModelFactory

class ProductActivity : AppCompatActivity() {

    private val productId = "pixel3"

    private lateinit var productViewModel: ProductViewModel

    lateinit var dataBinding: ActivityProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        dataBinding = DataBindingUtil.setContentView<ActivityProductBinding>(this,
            R.layout.activity_product
        )

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
                showDialog(it, "Error")
            }
        })

        productViewModel.buySuccessText.observe(this, Observer { event ->
            event?.getContentIfNotHandled()?.let {
                productViewModel.saveProductOrder(
                    this,
                    productViewModel.productItems.value.toString()
                )
                showDialog(it, "Success")
            }
        })
    }

    fun showDialog(msg: String, title: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(msg).setTitle(title)
        builder.show()
    }

}
package test.com.mvvmunittest

import android.os.Handler
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import test.com.mvvmunittest.api.IProductAPI
import test.com.mvvmunittest.api.ProductResponse

class ProductRepositoryTest {
//    @Mock
//    lateinit var productAPI: IProductAPI
    @Mock
    lateinit var productRepository: IProductRepository
    lateinit var mMockHandler: Handler

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun test_getProduct_handler() {
        val id = "pixel3"
        val productAPI = FakeHandlerProductAPI()
        productAPI.getProduct(id, object : IProductAPI.LoadAPICallBack {
            override fun onGetResult(productResponse: ProductResponse) {
                assertEquals(id, productResponse.id)
            }
        })
    }

    @Test
    fun test_getProduct_thread() {
        val id = "pixel3"
        val productAPI = FakeThreadProductAPI()
        productAPI.getProduct(id, object : IProductAPI.LoadAPICallBack {
            override fun onGetResult(productResponse: ProductResponse) {
                assertEquals(id, productResponse.id)
            }
        })
    }
}
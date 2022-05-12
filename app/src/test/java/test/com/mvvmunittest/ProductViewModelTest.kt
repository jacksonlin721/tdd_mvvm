package test.com.mvvmunittest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import test.com.mvvmunittest.api.IProductAPI
import test.com.mvvmunittest.api.ProductAPI
import test.com.mvvmunittest.api.ProductResponse
import test.com.mvvmunittest.viewmodel.ProductViewModel
import test.com.mvvmunittest.IProductRepository as ProductRepository

class ProductViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: ProductRepository
    private var expectProductResponse = ProductResponse()

    private lateinit var viewModel: ProductViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        expectProductResponse.id = "pixel3"
        expectProductResponse.name = "Google Pixel 3"
        expectProductResponse.price = 27000
        expectProductResponse.desc = "Desc"

        viewModel = ProductViewModel(repository)
    }

    @Test
    fun test_getProduct() {
        val productID = "pixel3"
        viewModel.getProduct(productID)

        val loadProductCallbackCaptor = argumentCaptor<ProductRepository.LoadProductCallback>()

        //驗證是否有呼叫IProductRepository.getProduct
        verify(repository).getProduct(eq(productID), capture(loadProductCallbackCaptor))

        //將callback攔截下載並指定productResponse的值。
        loadProductCallbackCaptor.value.onProductResult(expectProductResponse)

        assertEquals(expectProductResponse.name, viewModel.productName.value)
        assertEquals(expectProductResponse.id, viewModel.productId.value)
        assertEquals(expectProductResponse.price, viewModel.productPrice.value)
        assertEquals(expectProductResponse.desc, viewModel.productDesc.value)
    }

    @Test
    fun test_buySuccess() {
        val productID = "pixel3"
        val amount = 1
        val buyProductCallback =
            argumentCaptor<ProductRepository.BuyProductCallback>()
        val viewModel = ProductViewModel(repository)
        viewModel.productId.value = productID
        viewModel.productItems.value = amount.toString()
        viewModel.productPrice.value = amount * 27000

        viewModel.buy()
        verify(repository).buy(eq(productID), Mockito.eq(amount), capture(buyProductCallback))
        buyProductCallback.value.onBuyResult(true)
        assertEquals(27000, viewModel.productPrice.value)
        assertTrue(viewModel.buySuccessText.value != null)
    }

    @Test
    fun test_api() {
        val productID = "pixel"

        val expectResponse = ProductResponse()
        expectResponse.id = "pixel"
        expectResponse.name = "Google Pixel"
        expectResponse.price = 17000

        val productAPI = ProductAPI()
        productAPI.getProduct(productID, object : IProductAPI.LoadAPICallBack {
            override fun onGetResult(productResponse: ProductResponse) {
                println("[test_api] name= $productResponse.name")
                println("[test_api] name= $productResponse.id")
                println("[test_api] name= ${productResponse.price}")
                assertEquals(expectResponse.name, productResponse.name)
                assertEquals(expectResponse.id, productResponse.id)
                assertEquals(expectResponse.price, productResponse.price)
            }
        })

    }

//    @Test
    fun test_spy() {
        val productID = "pixel"
        viewModel.getProduct(productID)
        val mRepository = spy(ProductRepository::class.java)
        val mLoadProductCallback = spy(test.com.mvvmunittest.IProductRepository.LoadProductCallback::class.java)
        mRepository.getProduct(productID, mLoadProductCallback)

        assertEquals("pixel", viewModel.productName.value)
    }
}
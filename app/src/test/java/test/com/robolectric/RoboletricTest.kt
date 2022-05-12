package test.com.robolectric

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.android.synthetic.main.activity_product.*
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowAlertDialog
import test.com.mvvmunittest.IProductRepository
import test.com.mvvmunittest.ProductRepository
import test.com.mvvmunittest.api.ProductAPI
import test.com.mvvmunittest.argumentCaptor
import test.com.mvvmunittest.capture
import test.com.mvvmunittest.view.ProductActivity
import test.com.mvvmunittest.viewmodel.ProductViewModel

@RunWith(RobolectricTestRunner::class)
class RoboletricTest {
    lateinit var activity: ProductActivity
    @Mock
    lateinit var productRepository: IProductRepository
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        activity = Robolectric.buildActivity(ProductActivity::class.java).setup().get()
    }

    @Test
    fun test_buy() {
        activity.productItems.setText("1")
        activity.buy.performClick()
        activity.productPrice.text = "27000"
        val productViewModel = ProductViewModel(productRepository)

        val dialog = ShadowAlertDialog.getLatestDialog()
        val expectPrice = "27000"

        assertTrue(dialog.isShowing)
//        assertEquals(expectPrice, productViewModel.totalPrice.value)
    }
}
package test.com.mvvmunittest.instrumentedtest


import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SharedPreferenceManagerTest {
    @Test
    fun test_product_order() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val key = SharedPreferenceManager.PRODUCT_KEY
        val value = SharedPreferenceManager.DEFAULT_PRODUCT_VALUE

        val sharedPreferenceManager: ISharePreferenceManager = SharedPreferenceManager(appContext)
        sharedPreferenceManager.saveProductQuenty(key, value)

        val productValue = sharedPreferenceManager.getProductQuenty(key)

        Assert.assertEquals(value, productValue)
    }
}
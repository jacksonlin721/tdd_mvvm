package test.com.mvvmunittest

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import test.com.mvvmunittest.api.IProductAPI

class ProductRepositoryTest {
    @Mock
    lateinit var productAPI: IProductAPI

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun test_getProduct() {

    }
}
package test.com.mvvmunittest

import android.os.Handler
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.stubbing.Answer
import test.com.mvvmunittest.api.IProductAPI
import test.com.mvvmunittest.api.ProductResponse

class FakeHandlerProductAPI : IProductAPI {
    lateinit var mMockHandler: Handler

    override fun getProduct(productId: String, loadAPICallBack: IProductAPI.LoadAPICallBack) {
        mockHandler()
        mMockHandler.postDelayed({
            val productResponse = ProductResponse()
            productResponse.id = "pixel3"
            productResponse.name = "Google Pixel 3"
            productResponse.desc = "5.5吋螢幕"
            productResponse.price = 27000
            loadAPICallBack.onGetResult(productResponse)
        }, 1000)
    }

    fun mockHandler() {
        mMockHandler = Mockito.mock(Handler::class.java)
        `when`(
            mMockHandler.postDelayed(
                Mockito.any(Runnable::class.java),
                eq(1000)
            )
        )
            .thenAnswer(Answer {
                it.getArgument(0, Runnable::class.java).run()
                true
            })
    }
}
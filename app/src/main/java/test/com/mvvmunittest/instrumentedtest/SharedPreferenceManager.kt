package test.com.mvvmunittest.instrumentedtest

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager(override val context: Context) : ISharePreferenceManager {

    private val sharedPreferenceKey = "USER_DATA"
    companion object {
        val PRODUCT_KEY = "PIXEL3"
        val DEFAULT_PRODUCT_VALUE = "1"
    }

    var sharedPreference: SharedPreferences

    init {
        sharedPreference = context.getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE)
    }

    override fun saveProductQuenty(key: String, value: String){
        sharedPreference.edit().putString(key, value).apply()
    }

    override fun getProductQuenty(key: String): String {
        return sharedPreference.getString(key, "")!!
    }
}
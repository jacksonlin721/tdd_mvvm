package test.com.mvvmunittest.instrumentedtest

import android.content.Context

interface ISharePreferenceManager {
    val context: Context
    fun saveProductQuenty(key: String, value: String)
    fun getProductQuenty(key: String): String
}
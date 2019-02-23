package com.ezcode.mp.Presenter

import android.util.Log
import com.ezcode.mp.API.APIClient
import com.ezcode.mp.Model.Category
import com.ezcode.mp.Model.Product
import com.ezcode.mp.Model.Promotion
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

/*
    DEV: Eduardo Herrera
    LinkIn: https://www.linkedin.com/in/eduardoherreradeveloper/
    Portfolio: https://soulfade.wixsite.com/eduardoherrera
    Instagram: @edtrip_

    This is the Interface for implement the ApiClient and each EndPoint Call
 */

class MPInteractor {

    private val mainAPIClient: APIClient = APIClient()

    interface OnFinishedListener {

        fun <T:Any> onResultSuccess(dataIn: ArrayList<T>)
        fun onResultFail(msgError: String)
    }

    fun getProductsData(onFinishedListener: OnFinishedListener) {

        val call: Call<ArrayList<Product>>  = mainAPIClient.clientAPI.getProducts()
        call.enqueue(object: Callback<ArrayList<Product>> {

            override fun onResponse(call: Call<ArrayList<Product>>, response: Response<ArrayList<Product>>) {
                response.body() ?.let {
                    onFinishedListener.onResultSuccess(it)
                } ?: run {
                    onFinishedListener.onResultFail("Not Data to Parse")
                }
            }

            override fun onFailure(call: Call<ArrayList<Product>>, t: Throwable) {
                Log.d("MP ERROR", "Error with API: $t")
                onFinishedListener.onResultFail(t.toString())
            }
        })
    }

    fun getCategoriesData(onFinishedListener: OnFinishedListener) {

        val call: Call<ArrayList<Category>>  = mainAPIClient.clientAPI.getCategories()
        call.enqueue(object: Callback<ArrayList<Category>> {

            override fun onResponse(call: Call<ArrayList<Category>>, response: Response<ArrayList<Category>>) {
                response.body() ?.let {
                    onFinishedListener.onResultSuccess(it)
                } ?: run {
                    onFinishedListener.onResultFail("Not Data to Parse")
                }
            }

            override fun onFailure(call: Call<ArrayList<Category>>, t: Throwable) {
                onFinishedListener.onResultFail(t.toString())
            }
        })
    }

    fun getPromotionsData(onFinishedListener: OnFinishedListener) {

       val call: Call<ArrayList<Promotion>>  = mainAPIClient.clientAPI.getPromotions()
        call.enqueue(object: Callback<ArrayList<Promotion>> {

            override fun onResponse(call: Call<ArrayList<Promotion>>, response: Response<ArrayList<Promotion>>) {
                response.body() ?.let {
                    onFinishedListener.onResultSuccess(it)
                } ?: run {
                    onFinishedListener.onResultFail("Not Data to Parse")
                }
            }

            override fun onFailure(call: Call<ArrayList<Promotion>>, t: Throwable) {
                onFinishedListener.onResultFail(t.toString())
            }
        })
    }
}


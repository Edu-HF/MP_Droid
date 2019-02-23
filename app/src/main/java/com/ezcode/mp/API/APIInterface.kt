package com.ezcode.mp.API

import com.ezcode.mp.Model.Category
import com.ezcode.mp.Model.Product
import com.ezcode.mp.Model.Promotion
import com.ezcode.mp.Util.MPUtils
import retrofit2.Call
import retrofit2.http.GET

/*
    DEV: Eduardo Herrera
    LinkIn: https://www.linkedin.com/in/eduardoherreradeveloper/
    Portfolio: https://soulfade.wixsite.com/eduardoherrera
    Instagram: @edtrip_

    This is the Interface for define al EndPoints
 */

interface APIInterface {

    @GET(MPUtils.MP_PRODUCTS_URL)
    fun getProducts() : Call<ArrayList<Product>>

    @GET(MPUtils.MP_PROMOTIONS_URL)
    fun getPromotions() : Call<ArrayList<Promotion>>

    @GET(MPUtils.MP_CATEGORIES_URL)
    fun getCategories() : Call<ArrayList<Category>>
}
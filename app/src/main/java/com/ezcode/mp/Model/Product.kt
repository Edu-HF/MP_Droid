package com.ezcode.mp.Model

import com.google.gson.annotations.SerializedName

/*
    DEV: Eduardo Herrera
    LinkIn: https://www.linkedin.com/in/eduardoherreradeveloper/
    Portfolio: https://soulfade.wixsite.com/eduardoherrera
    Instagram: @edtrip_

    This is the MODEL Data of each PRODUCT
 */
data class Product(

    @SerializedName("id")
    val prodID: Int? = null,

    @SerializedName("name")
    val prodName: String? = null,

    @SerializedName("description")
    val prodDesc: String? = null,

    @SerializedName("photo")
    val prodPhoto: String? = null,

    @SerializedName("price")
    val prodPrice: Float? = null,

    @SerializedName("category_id")
    val prodCatID: Int? = null,

    var prodUnit: Int = 0,

    var prodTotalCostUnit: Float = 0f,

    var prodPromotion: Promotion? = null
)
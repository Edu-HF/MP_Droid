package com.ezcode.mp.Model

import com.google.gson.annotations.SerializedName

/*
    DEV: Eduardo Herrera
    LinkIn: https://www.linkedin.com/in/eduardoherreradeveloper/
    Portfolio: https://soulfade.wixsite.com/eduardoherrera
    Instagram: @edtrip_

    This is the MODEL Data of each PROMOTIONS and POLICIES
 */

data class Promotion(

    @SerializedName("name")
    val promoName: String? = null,

    @SerializedName("category_id")
    val promoCatID: Int? = null,

    @SerializedName("policies")
    val promoPolicies: ArrayList<Policies>? = null

)

data class Policies (

    @SerializedName("min")
    val poMin: Int? = null,

    @SerializedName("discount")
    val poDiscount: Int? = null
)
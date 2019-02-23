package com.ezcode.mp.Model

import com.google.gson.annotations.SerializedName

/*
    DEV: Eduardo Herrera
    LinkIn: https://www.linkedin.com/in/eduardoherreradeveloper/
    Portfolio: https://soulfade.wixsite.com/eduardoherrera
    Instagram: @edtrip_

    This is the MODEL Data of each CATEGORIES
 */

data class Category @JvmOverloads constructor(

    @SerializedName("id")
    val catID: Int? = null,

    @SerializedName("name")
    val catName: String? = null
)


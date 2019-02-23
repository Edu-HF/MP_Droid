package com.ezcode.mp.View

import com.ezcode.mp.Model.Category
import com.ezcode.mp.Model.Product

/*
    DEV: Eduardo Herrera
    LinkIn: https://www.linkedin.com/in/eduardoherreradeveloper/
    Portfolio: https://soulfade.wixsite.com/eduardoherrera
    Instagram: @edtrip_

    This is the Interface for Activity take all info of the Presenter
 */

interface IMPActivity {

    fun setProductData(productDataIn: ArrayList<Product>)
    fun setCategoryData(catDataIn: ArrayList<Category>)
    fun showSomeMsg(msgTitleIn: String, msgIn: String)

    fun addProductToCar(productIn: Product)
    fun removeProductToCar(productIn: Product)
    fun makeProductFilter(catIDIn: Int)
    fun notiDataIsChange()
}
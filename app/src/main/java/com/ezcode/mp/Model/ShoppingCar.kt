package com.ezcode.mp.Model

import java.io.Serializable

/*
    DEV: Eduardo Herrera
    LinkIn: https://www.linkedin.com/in/eduardoherreradeveloper/
    Portfolio: https://soulfade.wixsite.com/eduardoherrera
    Instagram: @edtrip_

    This class define de model of ShoopingCar
 */

class ShoppingCar: Serializable {

    var productData: ArrayList<Product> = ArrayList()
    var totalUN: Int = 0
    var totalCost: Float = 0f
}
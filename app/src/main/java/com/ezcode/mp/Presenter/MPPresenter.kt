package com.ezcode.mp.Presenter

import android.content.Context
import com.ezcode.mp.Model.Category
import com.ezcode.mp.Model.Product
import com.ezcode.mp.Model.Promotion
import com.ezcode.mp.Model.ShoppingCar
import com.ezcode.mp.Util.MPUtils
import com.ezcode.mp.View.IMPActivity
import kotlin.collections.ArrayList

/*
    DEV: Eduardo Herrera
    LinkIn: https://www.linkedin.com/in/eduardoherreradeveloper/
    Portfolio: https://soulfade.wixsite.com/eduardoherrera
    Instagram: @edtrip_

    This Class is the main brain of the app. This class handler all the business logic of the app
 */

class MPPresenter(private var mainView: IMPActivity?, private val mainI: MPInteractor): MPInteractor.OnFinishedListener {

    private var mainProductData: ArrayList<Product> = ArrayList()
    private var mainFilterProductData: ArrayList<Product> = ArrayList()
    private var mainCategoryData: ArrayList<Category> = ArrayList()
    private var mainPromotionData: ArrayList<Promotion> = ArrayList()
    private var mainShoppingCarData: ShoppingCar = ShoppingCar()

    fun getProductsData() {
        mainI.getProductsData(this)
    }

    fun getCategoriesData() {
        mainI.getCategoriesData(this)
    }

    private fun getPromotionsData() {
        mainI.getPromotionsData(this)
    }

    fun addProductToCar(contextIn: Context, productIn: Product) {

        if (mainShoppingCarData.productData.size != 0) {
            for (product in mainShoppingCarData.productData) {
                if (productIn.prodID == product.prodID) {
                    product.prodUnit += 1
                    updateProdUnitUI()
                    updateTotalCostShoppingCar()
                    MPUtils.saveShoppingCar(contextIn, mainShoppingCarData)
                    return
                }
            }
            productIn.prodUnit = 1
            mainShoppingCarData.productData.add(productIn)
            updateProdUnitUI()
            updateTotalCostShoppingCar()
            MPUtils.saveShoppingCar(contextIn, mainShoppingCarData)
        }else {
            productIn.prodUnit = 1
            mainShoppingCarData.productData.add(productIn)
            updateProdUnitUI()
            updateTotalCostShoppingCar()
            MPUtils.saveShoppingCar(contextIn, mainShoppingCarData)
        }
    }

    fun removeProductToCar(contextIn: Context, productIn: Product) {

        if (mainShoppingCarData.productData.size != 0) {
            for (product in mainShoppingCarData.productData) {
                if (productIn.prodID == product.prodID) {
                    if (product.prodUnit > 1) {
                        product.prodUnit -= 1
                        updateProdUnitUI()
                        updateTotalCostShoppingCar()
                        MPUtils.saveShoppingCar(contextIn, mainShoppingCarData)
                        return
                    }else {
                        product.prodUnit = 0
                        updateProdUnitUI()
                        updateTotalCostShoppingCar()
                        mainShoppingCarData.productData.remove(product)
                        MPUtils.saveShoppingCar(contextIn, mainShoppingCarData)
                        return
                    }
                }
            }
        }
    }

    private fun updateProdUnitUI() {

        for (product in mainProductData) {
            for (productCar in mainShoppingCarData.productData) {
                if (product.prodID == productCar.prodID) {
                    product.prodUnit = productCar.prodUnit
                }
            }
        }

        for (product in mainFilterProductData) {
            for (productCar in mainShoppingCarData.productData) {
                if (product.prodID == productCar.prodID) {
                    product.prodUnit = productCar.prodUnit
                }
            }
        }

        mainView?.notiDataIsChange()
    }

    fun iniShoppingCar(contextIn: Context) {
        val shoppingCar= MPUtils.getShoppingCar(contextIn)
        if (shoppingCar != null) {
            this.mainShoppingCarData = shoppingCar
        }
    }

    private fun addPromoToProducts() {

        if (mainProductData.isNotEmpty() && mainPromotionData.isNotEmpty()) {

            for (product in mainProductData) {
                for (promo in mainPromotionData) {
                    if (product.prodCatID == promo.promoCatID) product.prodPromotion = promo
                }
            }
        }

        mainView?.setProductData(mainProductData)
    }

    fun getTotalCarProducts() : Int {

        var totalProducts = 0
        for (product in mainShoppingCarData.productData) {
            totalProducts += product.prodUnit
        }

        mainShoppingCarData.totalUN = totalProducts

        return totalProducts
    }

    private fun updateTotalCostShoppingCar() {

        mainShoppingCarData.totalCost = 0f
        for (product in mainShoppingCarData.productData) {
            if (product.prodPrice != null) {
                var prodTotalCost = 0f
                var promoD = 0
                if (product.prodPromotion != null) {
                    if (product.prodPromotion!!.promoPolicies != null) {
                        for (poli in product.prodPromotion!!.promoPolicies!!) {
                            if (product.prodUnit >= poli.poMin!!) {
                                promoD = poli.poDiscount!!
                            }
                        }
                    }
                }
                prodTotalCost = product.prodPrice.times(product.prodUnit)
                if (promoD != 0) {
                    val promoDiscount = prodTotalCost * (promoD / 100)
                    prodTotalCost -= promoDiscount
                }
                product.prodTotalCostUnit = prodTotalCost
                mainShoppingCarData.totalCost += product.prodTotalCostUnit
            }
        }

    }

    fun makeProductFilter(catIDIn: Int) {

        mainFilterProductData = ArrayList()
        if (catIDIn != 0) {
            for (product in mainProductData) {
                if (product.prodCatID == catIDIn) {
                    mainFilterProductData.add(product)
                }
            }
            mainView?.setProductData(mainFilterProductData)
        }else {
            mainView?.setProductData(mainProductData)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> onResultSuccess(dataIn: ArrayList<T>) {

        when (dataIn.first()) {

            is Product -> {
                mainProductData = dataIn as ArrayList<Product>
                getPromotionsData()
                mainView?.setProductData(mainProductData)
                updateProdUnitUI()
            }
            is Category -> {
                mainCategoryData = dataIn as ArrayList<Category>
                val catAll = Category(0, "TODOS")
                mainCategoryData.add(0, catAll)
                mainView?.setCategoryData(mainCategoryData)

            }
            is Promotion -> {
                mainPromotionData = dataIn as ArrayList<Promotion>
                addPromoToProducts()
            }

        }
    }

    override fun onResultFail(msgError: String) {
        mainView?.showSomeMsg("Error API", msgError)
    }

    fun onDestroy() {
        mainView = null
    }
}
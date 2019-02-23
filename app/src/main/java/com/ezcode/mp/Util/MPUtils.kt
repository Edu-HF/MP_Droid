package com.ezcode.mp.Util

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import com.ezcode.mp.Model.Product
import com.ezcode.mp.Model.Promotion
import com.ezcode.mp.Model.ShoppingCar
import com.ezcode.mp.R
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.google.gson.Gson
import libs.mjn.prettydialog.PrettyDialog
import libs.mjn.prettydialog.PrettyDialogCallback

/*
    DEV: Eduardo Herrera
    LinkIn: https://www.linkedin.com/in/eduardoherreradeveloper/
    Portfolio: https://soulfade.wixsite.com/eduardoherrera
    Instagram: @edtrip_

    This class provides useful methods and constants of the app
 */

object MPUtils {

    const val MP_MP_PREFERENCE_ID = "243962506-0bb62e22-5c7b-425e-a0a6-c22d0f4758a9"
    const val MP_MP_PUBLIC_KEY = "TEST-c6d9b1f9-71ff-4e05-9327-3c62468a23ee"
    const val MP_MP_REQUEST_CODE = 1

    private const val MP_SHARED_PREFERENCES = "com.ezcode.mp.prefs"
    private const val MP_SHOPPING_CAR_NAME = "shoppingCarData"

    // API EndPoints
    const val MP_MAIN_SERVER_URL = "https://pastebin.com/raw/"
    const val MP_PRODUCTS_URL = "eVqp7pfX"
    const val MP_CATEGORIES_URL = "YNR2rsWe"
    const val MP_PROMOTIONS_URL = "R9cJFBtG"

    fun buildDialogAlert(contextIn: Context, titleIn: String, msgIn: String) {

        val mAlertView = PrettyDialog(contextIn)
        val mDoneAction = PrettyDialogCallback {
            mAlertView.dismiss()
        }

        mAlertView.setTitle(titleIn)
            .setMessage(msgIn)
            .setIcon(R.drawable.pdlg_icon_info)
            .setIconTint(R.color.colorPrimary)
            .addButton(
                "Aceitar",
                R.color.pdlg_color_white,
                R.color.colorPrimary,
                mDoneAction
            )

        mAlertView.show()
    }

    fun makePromoDesc(promoIn: Promotion) : String {

        var promoDesc = "Todos os produtos com esta promoção têm o seguinte desconto: \n\n"

        if (promoIn.promoPolicies != null) {
            for (policie in promoIn.promoPolicies) {
                promoDesc = promoDesc + "Com um mínimo de: " + policie.poMin.toString() + " UN\n"
                promoDesc = promoDesc + "Você recebe um desconto de: " + policie.poDiscount.toString() + "%\n\n"
            }
        }

        return promoDesc
    }

    fun makeProductDesc(productIn: Product) : String {

        var productDesc = "${productIn.prodName} \n\n"

        productDesc += "Preço do produto: ${productIn.prodPrice} \n\n"

        if (productIn.prodPromotion != null) {
            if (productIn.prodPromotion!!.promoPolicies != null) {

                productDesc += "Um desconto será aplicado à sua compra, conforme aplicável: \n\n"

                for (police in productIn.prodPromotion!!.promoPolicies!!.iterator()) {
                    productDesc += "Com um mínimo de: " + police.poMin.toString() + " UN \n"
                    productDesc += "Você recebe um desconto de: " + police.poDiscount.toString() + "% \n\n"
                }

                productDesc += "Número de unidades: ${productIn.prodUnit} \n"
                productDesc += "Preço com desconto aplicado: ${productIn.prodTotalCostUnit} \n"
            }
        }

        return productDesc
    }

    fun giveMeOffApply(productIn: Product) : String {

        var promoDesc = ""
        for (poli in productIn.prodPromotion!!.promoPolicies!!) {
            if (productIn.prodUnit >= poli.poMin!!) {
                promoDesc = "${poli.poDiscount!!} %"
            }
        }

        return promoDesc
    }

    fun makeLoading(viewIn: RecyclerView) : Skeleton {

        val sLoading = viewIn.applySkeleton(R.layout.prod_row, 10)
        sLoading.maskColor = Color.parseColor("#2a474b")
        sLoading.shimmerColor = Color.parseColor("#133337")
        sLoading.shimmerDurationInMillis = 1000
        sLoading.maskCornerRadius = 40f

        return sLoading

    }

    fun saveShoppingCar(contextIn: Context,shoppingCarIn: ShoppingCar) {

        val shoopingCarDataString = Gson().toJson(shoppingCarIn)
        val mainSP = contextIn.getSharedPreferences(MP_SHARED_PREFERENCES, 0)
        val mainSPEdit = mainSP.edit()
        mainSPEdit.putString(MP_SHOPPING_CAR_NAME, shoopingCarDataString)
        mainSPEdit.apply()
    }

    fun getShoppingCar(contextIn: Context) : ShoppingCar? {

        val mainSP = contextIn.getSharedPreferences(MP_SHARED_PREFERENCES, 0)
        val shoopingCarDataString = mainSP.getString(MP_SHOPPING_CAR_NAME, "")
        if (shoopingCarDataString != null) {
            return  Gson().fromJson(shoopingCarDataString, ShoppingCar::class.java)
        }
        return null
    }
}
package com.ezcode.mp.View

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.ezcode.mp.Model.ShoppingCar
import com.ezcode.mp.Presenter.MPPresenter
import com.ezcode.mp.R
import com.ezcode.mp.Util.MPUtils
import com.ezcode.mp.View.Adapter.ProdCarAdapter
import com.google.gson.Gson
import com.mercadopago.android.px.core.MercadoPagoCheckout
import kotlinx.android.synthetic.main.activity_car_mp.*
import kotlinx.android.synthetic.main.prod_car_row.*

/*
    DEV: Eduardo Herrera
    LinkIn: https://www.linkedin.com/in/eduardoherreradeveloper/
    Portfolio: https://soulfade.wixsite.com/eduardoherrera
    Instagram: @edtrip_

    This Class is the Activity of the Shooping Car App. This class used "activity_car_mp.xml" for Layout
 */

class MPCarActivity: AppCompatActivity() {

    private var mainShoppingCarData: ShoppingCar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_mp)

        prod_car_recycler_view.layoutManager = LinearLayoutManager(this)
        mainShoppingCarData = MPUtils.getShoppingCar(this)

        setupView()
    }

    @SuppressLint("SetTextI18n")
    private fun setupView() {

        if (mainShoppingCarData != null) {

            prod_car_recycler_view.adapter = ProdCarAdapter(mainShoppingCarData!!.productData)

            car_total_unit.text = "${mainShoppingCarData!!.totalUN} UN"
            car_total_label.text = "R$ ${String.format("%.2f", mainShoppingCarData!!.totalCost)}"

            car_pay_btn.setOnClickListener {

                val mpCheckCheckout = MercadoPagoCheckout.Builder(MPUtils.MP_MP_PUBLIC_KEY, MPUtils.MP_MP_PREFERENCE_ID)
                    .build()
                mpCheckCheckout.startPayment(this, MPUtils.MP_MP_REQUEST_CODE)
            }
        }
    }
}
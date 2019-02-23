package com.ezcode.mp.View.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ezcode.mp.Model.Product
import com.ezcode.mp.R
import com.ezcode.mp.Util.MPUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.prod_car_row.view.*

/*
    DEV: Eduardo Herrera
    LinkIn: https://www.linkedin.com/in/eduardoherreradeveloper/
    Portfolio: https://soulfade.wixsite.com/eduardoherrera
    Instagram: @edtrip_

    This is the ADAPTER for the RecyclerView of MPCarActivity.
    This class manipulate all the info to display for each Row
 */

class ProdCarAdapter(private var mainProductData: ArrayList<Product>): RecyclerView.Adapter<ProdViewHolder>() {

    override fun getItemCount(): Int {
        return mainProductData.size
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProdViewHolder {

        val layoutI = LayoutInflater.from(p0.context)
        val prodCarCell = layoutI.inflate(R.layout.prod_car_row, p0, false)

        return ProdViewHolder(prodCarCell)
    }

    override fun onBindViewHolder(p0: ProdViewHolder, p1: Int) {
        val itemRow = mainProductData[p1]

        Picasso.get().load(itemRow.prodPhoto).into(p0.view.prod_car_image_view)
        p0.view.prod_car_name_label.text = itemRow.prodName
        p0.view.prod_car_unit_label.text = "UN ${itemRow.prodUnit}"

        p0.view.prod_car_price_btn.text = "R$ ${String.format("%.2f", itemRow.prodTotalCostUnit)}"
        p0.view.prod_car_price_btn.setOnClickListener {
            val productDesc = MPUtils.makeProductDesc(itemRow)
            MPUtils.buildDialogAlert(p0.view.context, "Detalhe do produto", productDesc)
        }

        if (itemRow.prodPromotion != null) {
            if (MPUtils.giveMeOffApply(itemRow) != "") {
                p0.view.show_car_off_btn.text = MPUtils.giveMeOffApply(itemRow)
                p0.view.show_car_off_btn.setOnClickListener {
                    val promoDesc = MPUtils.makePromoDesc(itemRow.prodPromotion!!)
                    MPUtils.buildDialogAlert(p0.view.context, "Promoção", promoDesc)
                }
            }else {
                p0.view.show_car_off_btn.visibility = View.GONE
            }

        }else {
            p0.view.show_car_off_btn.visibility = View.GONE
        }
    }
}




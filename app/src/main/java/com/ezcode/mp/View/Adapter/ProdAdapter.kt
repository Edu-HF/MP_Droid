package com.ezcode.mp.View.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ezcode.mp.Model.Product
import com.ezcode.mp.R
import com.ezcode.mp.Util.MPUtils
import com.ezcode.mp.View.IMPActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.prod_row.view.*

/*
    DEV: Eduardo Herrera
    LinkIn: https://www.linkedin.com/in/eduardoherreradeveloper/
    Portfolio: https://soulfade.wixsite.com/eduardoherrera
    Instagram: @edtrip_

    This is the ADAPTER for the RecyclerView of MPActivity.
    This class manipulate all the info to display for each Row
 */

class ProdAdapter(var mainProductData: ArrayList<Product>, private val iMPActivity: IMPActivity): RecyclerView.Adapter<ProdViewHolder>() {

    override fun getItemCount(): Int {
        return mainProductData.count()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProdViewHolder {

        val layoutInflater = LayoutInflater.from(p0.context)
        val prodCellRow = layoutInflater.inflate(R.layout.prod_row, p0, false)

        return ProdViewHolder(prodCellRow)
    }

    override fun onBindViewHolder(p0: ProdViewHolder, p1: Int) {
        val itemRow = mainProductData[p1]

        Picasso.get().load(itemRow.prodPhoto).into(p0.view.prod_image_view)
        p0.view.prod_name_label.text = itemRow.prodName
        p0.view.prod_price_label.text = "R$ ${itemRow.prodPrice}"
        p0.view.prod_unit_label.text = "${itemRow.prodUnit} UN"

        p0.view.add_prod_btn.setOnClickListener {
            iMPActivity.addProductToCar(itemRow)
        }

        p0.view.remove_prod_btn.setOnClickListener {
            iMPActivity.removeProductToCar(itemRow)
        }

        if (itemRow.prodPromotion != null) {
            p0.view.show_off_btn.visibility = View.VISIBLE
            p0.view.show_off_btn.setOnClickListener {
                val promoDesc = MPUtils.makePromoDesc(itemRow.prodPromotion!!)
                MPUtils.buildDialogAlert(p0.view.context, "Promoção", promoDesc)
            }
        }else {
            p0.view.show_off_btn.visibility = View.INVISIBLE
        }
    }
}

class ProdViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    init {
        view.setOnClickListener {

        }
    }
}
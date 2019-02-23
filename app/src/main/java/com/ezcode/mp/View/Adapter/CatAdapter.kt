package com.ezcode.mp.View.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ezcode.mp.Model.Category
import com.ezcode.mp.R
import com.ezcode.mp.View.IMPActivity
import kotlinx.android.synthetic.main.cat_row.view.*
import java.util.*

class CatAdapter(private var mainCatDataIn: ArrayList<Category>, private var iMPActivity: IMPActivity): RecyclerView.Adapter<CatViewHolder>() {

    override fun getItemCount(): Int {
        return mainCatDataIn.count()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CatViewHolder {

        val layoutInflater = LayoutInflater.from(p0.context)
        val catCellRow = layoutInflater.inflate(R.layout.cat_row, p0, false)

        return CatViewHolder(catCellRow)
    }

    override fun onBindViewHolder(p0: CatViewHolder, p1: Int) {

        val cat = mainCatDataIn[p1]

        if (cat.catID != null) {

            val mainR = p0.view.context.resources
            val catImaID = mainR.getIdentifier("cat_ic_${cat.catID}" , "drawable", p0.view.context.packageName)
            p0.view.cat_row_ic.setImageDrawable(mainR.getDrawable(catImaID, null))

            p0.view.setOnClickListener {
                this.iMPActivity.makeProductFilter(cat.catID!!)
            }

        }

        p0.view.cat_row_name.text = cat.catName
    }

}

class CatViewHolder(val view: View): RecyclerView.ViewHolder(view)

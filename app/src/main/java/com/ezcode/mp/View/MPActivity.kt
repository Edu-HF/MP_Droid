package com.ezcode.mp.View

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import com.ezcode.mp.Model.Category
import com.ezcode.mp.Model.Product
import com.ezcode.mp.Presenter.MPInteractor
import com.ezcode.mp.Presenter.MPPresenter
import com.ezcode.mp.R
import com.ezcode.mp.Util.MPUtils
import com.ezcode.mp.View.Adapter.CatAdapter
import com.ezcode.mp.View.Adapter.ProdAdapter
import com.faltenreich.skeletonlayout.*
import kotlinx.android.synthetic.main.activity_mp.*


/*
    DEV: Eduardo Herrera
    LinkIn: https://www.linkedin.com/in/eduardoherreradeveloper/
    Portfolio: https://soulfade.wixsite.com/eduardoherrera
    Instagram: @edtrip_

    This Class is the Main Activity of the App. This class used "activity_mp.xml" for Layout
 */

class MPActivity : AppCompatActivity(), IMPActivity {

    private lateinit var mpPresenter: MPPresenter
    private lateinit var carFABtn: View
    private lateinit var carBadge: TextView
    private lateinit var productAdapter: ProdAdapter
    private lateinit var catAdapter: CatAdapter
    private lateinit var sLoading: Skeleton

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mp)

        mpPresenter = MPPresenter(this, MPInteractor())
        mpPresenter.getProductsData()
        mpPresenter.getCategoriesData()
        mpPresenter.iniShoppingCar(this)

        //Set Properties for the Recycler View
        prod_recycler_view.layoutManager = LinearLayoutManager(this)
        cat_grid_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        cat_grid_view.visibility = View.GONE

        sLoading = MPUtils.makeLoading(prod_recycler_view)
        sLoading.showSkeleton()

        carBadge = findViewById(R.id.car_badge)
        carBadge.text = mpPresenter.getTotalCarProducts().toString()
        carFABtn = findViewById(R.id.cat_fab)
        carFABtn.setOnClickListener {

            val intentCarView = Intent(this, MPCarActivity::class.java)
            startActivity(intentCarView)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.cat_menu, menu)
        menu?.setGroupVisible(0, true)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (cat_grid_view.visibility == View.GONE) {
            cat_grid_view.visibility = View.VISIBLE
        } else {
            cat_grid_view.visibility = View.GONE
        }

        return super.onOptionsItemSelected(item)
    }


    override fun setProductData(productDataIn: ArrayList<Product>) {

        runOnUiThread {
            sLoading.showOriginal()
            productAdapter = ProdAdapter(productDataIn, this)
            prod_recycler_view.adapter = productAdapter
        }
    }

    override fun notiDataIsChange() {

        runOnUiThread {
            productAdapter.notifyDataSetChanged()
            carBadge.text = mpPresenter.getTotalCarProducts().toString()
        }
    }

    override fun makeProductFilter(catIDIn: Int) {
        this.onOptionsItemSelected(null)
        mpPresenter.makeProductFilter(catIDIn)
    }

    override fun setCategoryData(catDataIn: ArrayList<Category>) {
        catAdapter = CatAdapter(catDataIn, this)
        cat_grid_view.adapter = catAdapter

    }

    override fun addProductToCar(productIn: Product) {
        mpPresenter.addProductToCar(this, productIn)
    }

    override fun removeProductToCar(productIn: Product) {
        mpPresenter.removeProductToCar(this, productIn)
    }

    override fun showSomeMsg(msgTitleIn: String, msgIn: String) {
        MPUtils.buildDialogAlert(this, msgTitleIn, msgIn)
    }

    override fun onDestroy() {
        mpPresenter.onDestroy()
        super.onDestroy()
    }
}



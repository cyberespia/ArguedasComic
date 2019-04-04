package com.mihost.ebooks

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import co.eggon.eggoid.extension.error
import com.android.billingclient.api.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), PurchasesUpdatedListener {

    //1.bottoms are in menu folder
    //2.design fragments
    //3.delete textview in main activity and it's layout and replace
    //4.write code below



    companion object {

        var billingClient: BillingClient? = null
        var skuMap = HashMap<String, List<String>>()
        var skuMap1 = HashMap<String, List<String>>()

        val lista: ArrayList<String> = ArrayList()
        val lista1: ArrayList<String> = ArrayList()
    }





    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        //code to change fragments
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        when (item.itemId) {
            R.id.navigation_catalogo -> {
                transaction.replace(R.id.container, Catalogo.newInstance()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_miscomic -> {
                transaction.replace(R.id.container, MisComics.newInstance()).commit()
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_publicar -> {
                transaction.replace(R.id.container, Publicar.newInstance()).commit()
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.container, Catalogo.newInstance()).commit()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


        lista.add("sabanero01mhe")
        lista.add("sabanero02mhe")
        lista.add("sabanero03mhe")
        lista.add("sabanero04mhe")
        lista.add("sabanero05mhe")
        lista.add("sabanero06mhe")
        lista.add("sabanero07mhe")
        lista.add("sabanero08mhe")
        lista.add("sabanero09mhe")
        lista.add("sabanero10mhe")
        lista.add("sabanero11mhe")
        lista.add("sabanero12mhe")
        lista.add("sabanero13mhe")


        lista1.add("asesino01mhe")
        lista1.add("asesino02mhe")
        lista1.add("asesino03mhe")
        lista1.add("asesino04mhe")


        initBilling()

    }




    /**
     * funciones in-app billing
     */

    override fun onDestroy() {
        super.onDestroy()
        billingClient?.endConnection()
    }

    override fun onPurchasesUpdated(responseCode: Int, purchases: MutableList<Purchase>?) {
        when (responseCode) {
            BillingClient.BillingResponse.OK -> {
                "purchase update OK".error()
            }
            BillingClient.BillingResponse.USER_CANCELED -> {
                "purchase update USER CANCELED".error()
            }
            else -> {
            }
        }
    }

    private fun initBilling() {
        billingClient = BillingClient.newBuilder(this).setListener(this).build()
        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(@BillingClient.BillingResponse billingResponseCode: Int) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    "start connection OK".error()
                    skuMap.put(BillingClient.SkuType.INAPP, lista)
                    skuMap1.put(BillingClient.SkuType.INAPP, lista1)
                    //skuMap.put(SkuType.SUBS, listOf(SUBS_1))
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                "disconnected billing client".error()
            }
        })
    }



}


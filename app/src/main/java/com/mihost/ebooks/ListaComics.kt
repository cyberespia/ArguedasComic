package com.mihost.ebooks

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import co.eggon.eggoid.extension.error
import com.android.billingclient.api.*
import com.mihost.ebooks.MainActivity.Companion.skuMap
import com.mihost.ebooks.MainActivity.Companion.skuMap1
import kotlinx.android.synthetic.main.activity_lista_comics.*


class ListaComics : AppCompatActivity() {

    val codigocomic:String? = null

    private val skuParams by lazy { SkuDetailsParams.newBuilder() }
    private val flowParams by lazy { BillingFlowParams.newBuilder() }

    private var adapter: AdaptadorListaComics? = null
    private var purchaseList = arrayListOf<CheckPurchaseItem>()
    private var purchaseList1 = arrayListOf<CheckPurchaseItem>()

    private var listaComprados = ArrayList<String>()

    var hashMap: HashMap<String, String> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_comics)

        val nombre = intent.getStringExtra("nombre")

        val codigocomic = intent.getStringExtra("codigo")

        /**
         * Action bar
         */
        val actionbar = supportActionBar
        actionbar?.title = nombre
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setDisplayShowHomeEnabled(true)

       val s1 = skuMap
        val s2 = skuMap1

        Log.e("skuMpa S1", s1.toString())

        Log.e("skuMpa S2", s2.toString())

   /*     if (codigocomic == "sabanero" ) {

            skuMap = MainActivity.skuMap
        } else {

            skuMap = MainActivity.skuMap1
        }*/

        Log.e("skuMpa", skuMap.toString())

       // val titulo = findViewById<TextView>(R.id.tituloselec)
       // titulo.text = nombre







        //inicializamos recyclerview


        //adapter = AdaptadorListaComics(this, purchaseList)
        //listaVentaRecycler.layoutManager = LinearLayoutManager(this)
        //listaVentaRecycler.adapter = adapter


        listaVentaRecycler.layoutManager = LinearLayoutManager(this)

        println(codigocomic)

        Log.e("codigo", codigocomic)

        if (codigocomic == "sabanero" ) {
            adapter = AdaptadorListaComics(this, purchaseList, listaComprados, hashMap)

            getAvailablePurchase(BillingClient.SkuType.INAPP, s1)

            checkMyPurchase(BillingClient.SkuType.INAPP)
        } else {

            adapter = AdaptadorListaComics(this, purchaseList, listaComprados, hashMap)

            getAvailablePurchase(BillingClient.SkuType.INAPP, s2)

            checkMyPurchase(BillingClient.SkuType.INAPP)
        }
        listaVentaRecycler.adapter = adapter







    }




    /**
     * funcion para obtener productos disposnibles a comprar
     */


    private fun getAvailablePurchase(skuType: String, listac: HashMap<String, List<String>>) {



        println("======= map ========================================")
        println(skuMap)

        skuParams.setSkusList(listac[skuType])
        skuParams.setType(skuType)
        "$skuType ${listac[skuType]?.size}".error()

        MainActivity.billingClient?.querySkuDetailsAsync(skuParams.build()) { responseCode, skuDetailsList ->
            when (responseCode) {
                BillingClient.BillingResponse.OK -> {
                    skuDetailsList?.let {
                        if (it.isNotEmpty()) {
                            it.forEach {
                                it.toString().error("purchase available")


                                    purchaseList.add(CheckPurchaseItem(it.sku, it.title, it.price))



                                println("====**==============**===")
                                println(it.sku)
                                println("==== list==============**===")
                                println(purchaseList)
                                println("==== detalles lista COMPLETA ==============**===")
                                println(skuDetailsList)
                                adapter?.updateData(purchaseList)
                            }





                        } else {
                            "no purchase".error()
                        }

                    }








                }
                BillingClient.BillingResponse.ERROR -> "purchase available error".error()
            }
        }
    }


    /**
     * funcion para obfener productos que ya fueron comprados
     */

    private fun checkMyPurchase(type: String?) {
/*        MainActivity.billingClient?.queryPurchaseHistoryAsync(type) { responseCode, purchasesList ->
            when (responseCode) {
                BillingClient.BillingResponse.OK -> {
                    purchasesList?.let {
                        if (it.isNotEmpty()) {
                            it.forEach {
                                it.toString().error("my purchase")
                                purchaseList.add(it.sku, it.orderId, it.purchaseToken)
                            }
                            adapter?.updateData(purchaseList)
                        } else {
                            "no purchase".error()
                        }
                    }
                }
            }
        }*/


        MainActivity.billingClient?.queryPurchaseHistoryAsync(type) { responseCode, skuDetailsList ->

            if (responseCode == BillingClient.BillingResponse.OK && skuDetailsList != null) {
                for (skuDetails in skuDetailsList) {



                    val sku = skuDetails.sku
                    val orden = skuDetails.purchaseToken

                    //listaComprados.add(sku)

                    println(sku)

                    listaComprados.add(sku)
                    //armamos el array hasmap <sku = price>  --> {sku1=USD 3.99, sku2=USD 1.99, sku3=USD 3.99}
                    hashMap[sku] = orden


                }
                adapter?.updateDataComprados(listaComprados)
            }


        }



    }


    /**
     * funcion para activar flecha de regreso en actionbar
     */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }







}

/*
 * *
 *  * Creado por  Mario Burga - MiHost.com el 13/08/18 04:22 PM
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Última modificación 13/08/18 04:22 PM
 *
 */

package com.mihost.ebooks

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.android.billingclient.api.*
import kotlinx.android.synthetic.main.celda_ventas.view.*
import com.android.billingclient.api.BillingClient
import com.mihost.ebooks.MainActivity.Companion.billingClient



class AdaptadorListaComics(private val context: Context, private var items: List<CheckPurchaseItem>?, private var itemsComprados: ArrayList<String>?, var hashMap: HashMap<String, String>) : RecyclerView.Adapter<AdaptadorListaComics.Holder>() {

        var onItemClickListener: ((String?, String?) -> Unit)? = null

        //override fun onCreateViewHolder(parent: ViewGroup, i: Int): Holder =
               // Holder(LayoutInflater.from(context).inflate(R.layout.celda_ventas, parent, false))



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val vista = LayoutInflater.from(parent.context)
        val cellForRow = vista.inflate(R.layout.celda_ventas, parent, false)

        return Holder(cellForRow)
    }





        override fun onBindViewHolder(holder: Holder, position: Int) {
/*            items?.get(position)?.let {
                holder.sku = it.sku
               holder.title.text = it.title
                holder.price.text = it.price
            }*/



            println("========================= c =====================")
            println(itemsComprados)
            //println("========================= cc =====================")
            //println(hashMap)


            val toFind = items?.get(position)?.sku
            var found = false

            println("Producto a buscar $toFind")
            for (n in this.itemsComprados!!) {

                println("===== prodyctos comprados ===")
                println("n = $n")

                if (n == toFind) {

                    found = true
                    println(found)
                    break
                }


            }



            holder.itemView.tituloLbl.text = items?.get(position)?.title?.replace("(MH eBooks)", "")
            holder.itemView.precioLbl.text = items?.get(position)?.price

            // si found = true el producto ya esta comprado, desactivamos boton comprar
            println("EL founf = $found")




            if (found){

                holder.botonComprar.text = context.getString(R.string.Leer)

                   holder.itemView.comprarBtn.setOnClickListener {

                       val intent = Intent(context, LeerPDF::class.java)
                       intent.putExtra("nombre", items?.get(position)?.sku)
                       intent.putExtra("titulo", items?.get(position)?.title)
                       context.startActivity(intent)

                   }

                } else {

                holder.itemView.comprarBtn.setOnClickListener {
                    Log.i("button", items?.get(position)?.sku?.toLowerCase())
                    //var skuid = hashMap2[identificador]


                    val flowParams = BillingFlowParams.newBuilder()
                            .setSku(items?.get(position)?.sku?.toLowerCase())
                            .setType(BillingClient.SkuType.INAPP) // SkuType.SUB for subscription

                            .build()
                    billingClient?.launchBillingFlow(context as Activity?, flowParams)


                }
            }





        }

        override fun getItemCount(): Int = items?.size ?: 0

        inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
/*            val title: TextView = view.findViewById(R.id.tituloLbl)
            val price: TextView = view.findViewById(R.id.precioLbl)
            var sku: String? = null

            init {
                //view.setOnClickListener { onItemClickListener?.invoke(sku, type.text.toString()) }
            }*/



            val botonComprar = view.findViewById(R.id.comprarBtn) as Button



        }

        fun updateData(newItems: List<CheckPurchaseItem>) {
            items = newItems
            notifyDataSetChanged()
        }

        fun updateDataComprados(nItems: ArrayList<String>) {
        itemsComprados = nItems
        notifyDataSetChanged()
        }


    }

data class CheckPurchaseItem(val sku: String? = null, val title: String? = null, val price: String? = null)

data class CompradosItem(val sku: String? = null)

/*

    lateinit var billingClient: BillingClient
    var hashMap: HashMap<String, String> = HashMap()
    var hashMapTitulos: HashMap<String, String> = HashMap()




    override fun onPurchasesUpdated(responseCode: Int, purchases: MutableList<Purchase>?) {
        if (responseCode == BillingClient.BillingResponse.OK && purchases != null) {
            for (purchase in purchases) {
                //handlePurchase(purchase)


                println("===code===")


                billingClient.consumeAsync(purchase.purchaseToken) { responseCode, purchaseToken -> Toast.makeText(context, "Consumed", Toast.LENGTH_LONG).show() }
            }
        } else if (responseCode == BillingClient.BillingResponse.USER_CANCELED) {
            Toast.makeText(context, "Cancelado", Toast.LENGTH_LONG).show()
        } else {

        }
    }








    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VentasViewHolder {



        val vista = LayoutInflater.from(parent.context)
        val cellForRow = vista.inflate(R.layout.celda_ventas, parent, false)


        return VentasViewHolder(cellForRow)
    }






    override fun getItemCount(): Int {
        return arrayDatos.size
    }




    override fun onBindViewHolder(holder: VentasViewHolder, position: Int) {


        queryskudetails()









        val identificador: String = arrayDatos[position]

        val tituloOriginal: String? =  hashMapTitulos[identificador]

        //val tituloOK = tituloOriginal!!.replace("MHeBook", "-")

        //println("=====titulo ========")
        //println(tituloOriginal)
        //println(hashMapTitulos)

        //holder.view.tituloLbl?.text = hashMapTitulos[identificador]
        holder.view.tituloLbl?.text = identificador

       // holder.view.precioLbl?.text = hashMap[identificador.toLowerCase()].toString()

        holder.view.precioLbl?.text = "USD 3.99"

        //Log.i("price", hashMap["sabanero1"].toString())



        holder.view.comprarBtn.setOnClickListener {
            Log.i("button", identificador.toLowerCase())
            //var skuid = hashMap2[identificador]


            val flowParams = BillingFlowParams.newBuilder()
                    .setSku(identificador.toLowerCase())
                    .setType(BillingClient.SkuType.INAPP) // SkuType.SUB for subscription

                    .build()
            billingClient.launchBillingFlow(context as Activity?, flowParams)

        }






        billingClient.queryPurchaseHistoryAsync(SkuType.INAPP) { responseCode, purchasesList ->
            if (responseCode == BillingResponse.OK && purchasesList != null) {
                for (purchase in purchasesList) {
                    // Process the result.

                    println("======== xxxx =======")
                    println(purchasesList)
                }
            }
        }


    }




    private fun queryskudetails() {

        billingClient = BillingClient.newBuilder(context).setListener(this).build()
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {
                Log.i("Disconnected", "billing client")
            }

            override fun onBillingSetupFinished(responseCode: Int) {

                billingClient.let { billingClient ->

                    val params = SkuDetailsParams.newBuilder()
                            //.setSkusList(arrayListOf("sabanero1", "sabanero2"))
                            .setSkusList(arrayDatos)
                            .setType(BillingClient.SkuType.INAPP)
                            .build()

                    billingClient.querySkuDetailsAsync(params) { responseCode, skuDetailsList ->

                        if (responseCode == BillingClient.BillingResponse.OK && skuDetailsList != null) {
                            for (skuDetails in skuDetailsList) {

                                //println("=====detailes =======")
                                //println(skuDetailsList)

                                val sku = skuDetails.sku
                                val price = skuDetails.price

                                val titulo = skuDetails.title

                                // Log.i("skudetails", sku)
                                // Log.i("skuprice", price)

                                //armamos el array hasmap <sku = price>  --> {sku1=USD 3.99, sku2=USD 1.99, sku3=USD 3.99}
                                hashMap[sku] = price

                                //armamos el array hasmap <sku = titulos>
                                hashMapTitulos[sku] = titulo


                            }
                        }


                    }
                }


            }

        })
    }




    fun updateData(newItems: ArrayList<String>) {
        items = newItems
        notifyDataSetChanged()
    }



}









class VentasViewHolder(val view: View, var datos: Ventas? = null) : RecyclerView.ViewHolder(view) {


    init {
        view.setOnClickListener {

            val intent = Intent(view.context, LeerPDF::class.java)
            intent.putExtra("nombre", datos?.nombreVolumen)
            view.context.startActivity(intent)
        }


    }
}*/

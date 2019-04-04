/*
 * *
 *  * Creado por  Mario Burga - MiHost.com el 13/08/18 12:54 PM
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Última modificación 13/08/18 12:53 PM
 *
 */

package com.mihost.ebooks

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.celda_catalogo.view.*





class AdaptadorCatalogo(val listaCatalogo: ArrayList<Comics>): RecyclerView.Adapter<CatalogoViewHolder>() {

    val listaComics = listOf("Sabanero 1", "Sabanero 2", "Sabaero 3", "Sabenero 4")


    // este método devuelve la vista de cada elemento de la lista

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogoViewHolder {

        val vista = LayoutInflater.from(parent.context)
        val cellForRow = vista.inflate(R.layout.celda_catalogo, parent, false)
        return CatalogoViewHolder(cellForRow)

    }

    // este método devuele el numero de elementos de la lista
    override fun getItemCount(): Int {

        return listaCatalogo.size
    }

    // este método vincula los datos de la lista
    override fun onBindViewHolder(holder: CatalogoViewHolder, position: Int) {

        var datos = listaCatalogo[position]


        //holder.view.titulo?.text =  tituloComic

        holder.view.titulo?.text =  datos.nombre
        holder.view.autor?.text =  listaCatalogo[position].autor
        holder.view.year?.text =  listaCatalogo[position].year.toString()
        holder.view.descripcion?.text =  listaCatalogo[position].description
        holder.view.cover?.setImageResource(listaCatalogo[position].cover)

        // pasamos datos al holder class

        holder.datos = datos

    }



}


class CatalogoViewHolder(val view: View, var datos: Comics? = null) : RecyclerView.ViewHolder(view) {


    init {
        view.setOnClickListener {

            val intent = Intent(view.context, ListaComics::class.java)
            intent.putExtra("nombre", datos?.nombre)
            intent.putExtra("autor", datos?.autor)
            intent.putExtra("codigo", datos?.codigo)
            view.context.startActivity(intent)
        }


    }
}
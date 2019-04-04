package com.mihost.ebooks

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.LinearLayout
import android.graphics.Typeface
import android.support.v7.app.ActionBar
import android.widget.TextView




class Catalogo : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, args: Bundle?): View? {

        val vista = inflater.inflate(R.layout.fragment_catalogo, container, false)


        /**
         * Set title en Action bar
         */
        activity?.title = "MiHost eBooks"



        /** Array de datos **/
        val catalogo: ArrayList<Comics> = ArrayList()
        catalogo.add(Comics("Juan Luna El Sabanero", "Oscar Arguedas Solis", 2018, "Juan Luna El Sabanero, es un comic costarricense creado por el artista gráfico Oscar Arguedas Solis en 1997.", R.drawable.sabanero_cover_3x, "sabanero"))
        catalogo.add(Comics("Asesino Indeleble", "Oscar Arguedas", 2017, "Las características gráficas de ASESINO INDELEBLE fueron creadas por Oscar Arguedas Solis - Hecho en Costa Rica.", R.drawable.asesino_cover_3x, "asesino"))
        //catalogo.add(Comics("Asesino Indeleble", "Pedro sanchez", 2000, "Ok ok Descripcion aqui sobre el comics", R.drawable.sabanero_cover_3x, "3comic"))


        val reciclador = vista.findViewById(R.id.reciclador) as RecyclerView
        reciclador.layoutManager = LinearLayoutManager(activity)
        reciclador.adapter = AdaptadorCatalogo(catalogo)

        return vista


    }




    companion object {
        fun newInstance(): Catalogo = Catalogo()

    }



}
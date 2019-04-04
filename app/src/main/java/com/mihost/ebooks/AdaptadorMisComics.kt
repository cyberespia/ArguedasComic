/*
 * *
 *  * Creado por  Mario Burga - MiHost.com el 03/09/18 12:20 PM
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Última modificación 03/09/18 12:20 PM
 *
 */

package com.mihost.ebooks

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView

import kotlin.collections.HashMap




/**
 * Created by reale on 21/11/2016.
 */

class AdaptadorMisComics(private val context: Context, private val listDataHeader: List<String>, private val listHashMap: Map<String, List<String>>, val maptitulos: HashMap<String, String>) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return listDataHeader.size
    }

    override fun getChildrenCount(i: Int): Int {
        return listHashMap[listDataHeader[i]]!!.size
    }

    override fun getGroup(i: Int): Any {
        return listDataHeader[i]
    }

    override fun getChild(i: Int, i1: Int): Any {

        var codigobook = listHashMap[listDataHeader[i]]!![i1]
        Log.e("codigobook", codigobook)


      // quitams extension .pdf ==> codigobook = codigobook.take(codigobook.lastIndexOf('.'))

        val titulo = maptitulos[codigobook]

        return titulo!!

        //return listHashMap[listDataHeader[i]]!![i1] // i = Group Item , i1 = ChildItem
    }

    override fun getGroupId(i: Int): Long {
        return i.toLong()
    }

    override fun getChildId(i: Int, i1: Int): Long {
        return i1.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(i: Int, b: Boolean, view: View?, viewGroup: ViewGroup): View {

        var view = view
        val headerTitle = getGroup(i) as String
        if (view == null) {
            val inflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.miscomics_list_seccion, null)
        }
        val lblListHeader = view!!.findViewById<View>(R.id.lblListHeader) as TextView
        lblListHeader.setTypeface(null, Typeface.BOLD)
        lblListHeader.text = headerTitle
        return view
    }

    override fun getChildView(i: Int, i1: Int, b: Boolean, view: View?, viewGroup: ViewGroup): View {
        var view = view
        val childText = getChild(i, i1) as String
        if (view == null) {
            val inflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.miscomics_list_item, null)
        }

        val txtListChild = view!!.findViewById<View>(R.id.lblListItem) as TextView
        txtListChild.text = childText

        // add para click
        txtListChild.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                Log.i("Click", "TextView clicked on row $i1")

                val codigobook = listHashMap[listDataHeader[i]]!![i1]
                val titulo = maptitulos[codigobook]

                Log.i("Click", "TextView clicked on row $codigobook")

                val intent = Intent(view.context, LeerPDF::class.java)

                intent.putExtra("nombre", codigobook)
                intent.putExtra("titulo", titulo)

                view.context.startActivity(intent)

            }
        })

        return view
    }

    override fun isChildSelectable(i: Int, i1: Int): Boolean {
        return true
    }



}
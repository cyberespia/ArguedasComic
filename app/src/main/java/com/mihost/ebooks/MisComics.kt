package com.mihost.ebooks

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import java.io.File
import android.support.v7.app.AppCompatActivity




class MisComics : Fragment() {



    private var listView: ExpandableListView? = null
    private var listAdapter: ExpandableListAdapter? = null


    var listHash: HashMap<String, List<String>> = HashMap()
    var listDataHeader: MutableList<String> = mutableListOf()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val vista = inflater.inflate(R.layout.fragment_mis_comics, container, false)

        /**
         * Set title en Action bar
         */
        activity?.title = "eBooks descargados"





       // var mydir = context?.getDir("files", Context.MODE_PRIVATE)
       // var lister = mydir?.absoluteFile



       // listDataHeader.add("ASESINO INDELEBLE")
        //listDataHeader.add("EL SABANERO")


        var sabaneroTitulos = arrayOf<String>("El Sabanero 1","El Sabanero 2","El Sabanero 3", "El Sabanero 4", "El Sabanero 5", "El Sabanero 6", "El Sabanero 7", "El Sabanero 8", "El Sabanero 9", "El Sabanero 10", "El Sabanero 11", "El Sabanero 12", "El Sabanero 13")

        var asesinoTitulos = arrayOf<String>("Asesino Indeleble 1","Asesino Indeleble 2","Asesino Indeleble 3", "Asesino Indeleble 4")


        //val tituCodes = mapOf("sabanero01mhe" to "El Sabanero 1", "sabanero02mhe" to "El Sabanero 2", "sabanero03mhe" to "El Sabanero 3")

        val maptitulos = hashMapOf(
                "sabanero01mhe" to "El Sabanero Vol 01",
                "sabanero02mhe" to "El Sabanero Vol 02",
                "sabanero03mhe" to "El Sabanero Vol 03",
                "sabanero04mhe" to "El Sabanero 4",
                "sabanero05mhe" to "El Sabanero 5",
                "sabanero06mhe" to "El Sabanero 6",
                "sabanero07mhe" to "El Sabanero 7",
                "sabanero08mhe" to "El Sabanero 8",
                "sabanero09mhe" to "El Sabanero 9",
                "sabanero10mhe" to "El Sabanero 10",
                "sabanero11mhe" to "El Sabanero 11",
                "sabanero12mhe" to "El Sabanero 12",
                "sabanero13mhe" to "El Sabanero 13",
                "asesino01mhe" to "El Asesino Indeleble 01",
                "asesino02mhe" to "El Asesino Indeleble 02",
                "asesino03mhe" to "El Asesino Indeleble 03",
                "asesino04mhe" to "El Asesino Indeleble 04"


        )

        Log.e("titulos hasmpa", maptitulos.toString())


        Log.e("secciones", listDataHeader.toString())


        val misComics: MutableList<String> = mutableListOf()

        val directorioFiles = context?.filesDir
        Log.e("direcorio", directorioFiles.toString())
        val files = directorioFiles?.listFiles()

        Log.e("files", files.toString())


        if (files != null) {
            for (aFile in files) {
                //println(aFile.name + " - " + aFile.length())
                misComics.add(aFile.name)
            }
        }


        Log.e("listax", misComics.toString())

val cc = misComics.sort()

        Log.e("orden", cc.toString())


        val result = misComics.map {it.substringBefore(".pdf")}
                .groupBy {it.contains("sabanero")}
                .map { it.value}
        println(result) // [[ssbook1, ssbook2, ssbook3], [aabook1, aabook2]]

        Log.e("mi matriz", result.toString())



        //val total = misComics.groupBy{ if (it.contains("sabanero")) "Sabanero" else  "Asesino" }
        val total = misComics.map {it.substringBefore(".pdf")}
                .groupBy{ if (it.contains("sabanero")) "El Sabanero" else  "Asesino Indeleble" }

        // se agrego parentesis  (t, u) por compatibildad con android 4.4
        total.forEach { (t, u ) ->


            listDataHeader.add(t)
        }



        Log.e("mi matriz total", total.toString())

        Log.e("headers", listDataHeader.toString())


        //val saba =  result[0]

        //Log.e("saba matriz", saba.toString())

       //val ase = result[1]



   /*     for (index in listDataHeader.indices) {
            println("=====detailes =======")
            println(listDataHeader[index])
            listHash[listDataHeader[index]] = result[index]


        }*/

        Log.e("===List Hasmap====", listHash.toString())
        // {EL SABANERO=[sabanero01mhe, sabanero02mhe], ASESINO INDELEBLE=[asesino01mhe]}


        /** borra un archivo **/
        val myActivity = activity
        val dir = myActivity?.filesDir?.absolutePath
        val f0 = File(dir, "sabanero1.pdf")
        val d0 = f0.delete()
        Log.e("Delete Check", "File deleted: $dir/sabanero1.pdf $d0")



        /*val reciclador = vista.findViewById(R.id.recyclerMisComics) as RecyclerView
        reciclador.layoutManager = LinearLayoutManager(activity)
        reciclador.adapter = AdaptadorMisComics(saba)*/


        listView = vista.findViewById(R.id.lvExp) as ExpandableListView

        listAdapter = AdaptadorMisComics(activity!!.applicationContext, listDataHeader, total,maptitulos)
        listView!!.setAdapter(listAdapter)







                return vista
            }






    companion object {
        fun newInstance(): MisComics = MisComics()
    }






}
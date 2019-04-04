package com.mihost.ebooks


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_publicar.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder


/**
 * A simple [Fragment] subclass.
 *
 */

class Publicar : Fragment() {


    var nombreLbl: EditText? = null
    var emailLbl: EditText? = null
    var telefonoLbl: EditText? = null
    var mensajeLbl: EditText? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val vista = inflater.inflate(R.layout.fragment_publicar, container, false)

        val context = this.activity?.applicationContext

        /**
         * Set title en Action bar
         */
        activity?.title = "Publicar"



        val nombreLbl = vista.findViewById<EditText>(R.id.nombre)
        val emailLbl = vista.findViewById<EditText>(R.id.email)
        val telefonoLbl = vista.findViewById<EditText>(R.id.telefono)
        val mensajeLbl = vista.findViewById<EditText>(R.id.mensaje)
        val enviarBtn = vista.findViewById<Button>(R.id.enviarBtn)


        // Evita errores en SDK antiguas para el request http
        if (android.os.Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }



        enviarBtn.setOnClickListener {

           if((nombreLbl.text.toString().trim().isNotEmpty()) && (emailLbl.text.toString().trim().isNotEmpty()) && (telefonoLbl.text.toString().trim().isNotEmpty()) && (mensajeLbl.text.toString().trim().isNotEmpty())) {


               sendGetRequest(nombreLbl.text.toString(), emailLbl.text.toString(), telefonoLbl.text.toString(), mensajeLbl.text.toString())
               showNewNameDialog()

               //clear campos
               nombreLbl.text.clear()
               emailLbl.text.clear()
               telefonoLbl.text.clear()
               mensajeLbl.text.clear()


           } else {

               AlertDialog.Builder(activity)
                       .setTitle("Error")
                       .setMessage("Debe completar todos los campos")
                       .setPositiveButton("Aceptar") { dialog, which ->
                       }.show()

           }



            Log.e("EditText", nombre.toString())
        }


        return vista
    }

    companion object {
        fun newInstance(): Publicar = Publicar()
    }


    /** funcion que muestra alerta dialogo confirmando el envio del reporte **/
    fun showNewNameDialog() {

        AlertDialog.Builder(activity).apply {
                    setTitle("Formulario")
                    setMessage("Enviado correctamente")
                    .setPositiveButton("Aceptar") { _, _ ->
                    }.show()
        }


    }

    /** fin alerta**/

    /** chequeo de campos validos y que no esten vacios **/



    /** funcion de solicitud al servidor */

    fun sendGetRequest(nombre: String, correo: String, telef: String, msje: String) {

        var reqParam = URLEncoder.encode("nombre", "UTF-8") + "=" + URLEncoder.encode(nombre, "UTF-8")
        reqParam += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(correo, "UTF-8")
        reqParam += "&" + URLEncoder.encode("telefono", "UTF-8") + "=" + URLEncoder.encode(telef, "UTF-8")
        reqParam += "&" + URLEncoder.encode("mensaje", "UTF-8") + "=" + URLEncoder.encode(msje, "UTF-8")

        val mURL = URL("https://ebooks.mihost.com/contactoapp-android.php?$reqParam")


        Log.e("EditText", mURL.toString())

        with(mURL.openConnection() as HttpURLConnection) {
            // optional default is GET
            requestMethod = "GET"

    //        println("===============")
    //         println("URL : ${url}")
   //            println("Response Code : ${responseCode}")


            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuffer()
                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                it.close()
                //println("Response : $response")
            }
        }
    }

    /***/



}
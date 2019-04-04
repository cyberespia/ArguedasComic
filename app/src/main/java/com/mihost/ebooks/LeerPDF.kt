/*
 * *
 *  * Creado por  Mario Burga - MiHost.com el 13/08/18 04:41 PM
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Última modificación 13/08/18 04:41 PM
 *
 */

package com.mihost.ebooks
import android.app.ProgressDialog
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_leer_pdf.*
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL



class LeerPDF : AppCompatActivity() {

    private val TAG = "Descargas"
    var nombref: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leer_pdf)

        nombref = intent.getStringExtra("nombre")
        val titulof = intent.getStringExtra("titulo")
        val tituloOK = titulof.replace("(MH eBooks)", "")



        /**
         * Action bar
         */
        val actionbar = supportActionBar
        actionbar?.title = tituloOK
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setDisplayShowHomeEnabled(true)



        Log.e("paso por aqui", "oncreate")

        // inicia descarga
        val file = baseContext.getFileStreamPath("$nombref.pdf")

        if (!file.exists()) {

            descargaPDF()

        } else {

            pdfView.fromFile(file).load()

        }






    }








    private fun descargaPDF() {

        val nombreArchivo = intent.getStringExtra("nombre")

        val urlADescargar = "https://ebooks.mihost.com/3b00ks4D0wn/$nombreArchivo.pdf"

        println(urlADescargar)
        Log.e("La url a descargar es ", urlADescargar)

        /**add para progresDialog */
        val progressDialog = ProgressDialog(this)
        progressDialog.isIndeterminate = true
        /**progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); */
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        progressDialog.setMessage("Descargando...")
        /**Fin progresDialog */

        /** sin dialog new DescargarPDFAsyncTask().execute( urlADescargar ); */
        DescargarPDFAsyncTask(progressDialog).execute(urlADescargar)
    }


    internal inner class DescargarPDFAsyncTask(
            /** agregado para el progressDialog */
            var progressDialog: ProgressDialog) : AsyncTask<String, Int, String>() {
        /** fin para el progressDialog */


        override fun onPreExecute() {
            super.onPreExecute()

            /**aqui cargamos ProgressDialog */
            progressDialog.show()
        }


        override fun doInBackground(vararg urlPDF: String): String {
            val urlADescargar = urlPDF[0]

            var conexion: HttpURLConnection? = null
            var input: InputStream? = null
            var output: OutputStream? = null


            try {
                val url = URL(urlADescargar)
                conexion = url.openConnection() as HttpURLConnection
                conexion.connect()

                if (conexion.responseCode != HttpURLConnection.HTTP_OK) {

                    return "Conexión no realizada"

                }

                input = conexion.inputStream
                val rutaFicheroGuardado = filesDir.toString() + "/$nombref.pdf"
                output = FileOutputStream(rutaFicheroGuardado)

                // tamaño de fichero
                val sizeFichero = conexion.contentLength
                val data = ByteArray(1024)
                var count : Int? = null
                var total = 0

                while ({ count = input.read(data); count }() != -1) {

                    output.write(data, 0, count!!)
                    total += count!!
                    publishProgress(total * 100 / sizeFichero)
                }



            } catch (e: MalformedURLException) {

                e.printStackTrace()
                return "Error" + e.message
            } catch (e: IOException) {

                e.printStackTrace()
                return "Error" + e.message
            } finally {

                try {
                    input?.close()
                    output?.close()
                    conexion?.disconnect()
                } catch (e: IOException) {

                    e.printStackTrace()

                }

            }

            return "Se realizo la descarga"
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            /** add progress Dialogo */
            progressDialog.isIndeterminate = false
            progressDialog.max = 100
            progressDialog.progress = values[0]!!
        }


        override fun onPostExecute(mensaje: String) {
            super.onPostExecute(mensaje)

            /**Cuando termine paramos ProgressDialog */
            progressDialog.dismiss()

            Toast.makeText(applicationContext, mensaje, Toast.LENGTH_LONG).show()


            /** termina descraga mostra so el pdf **/
            val file = baseContext.getFileStreamPath("$nombref.pdf")
            pdfView.fromFile(file).load()
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



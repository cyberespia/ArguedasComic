/*
 * *
 *  * Creado por  Mario Burga - MiHost.com el 07/09/18 05:53 PM
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Última modificación 07/09/18 05:53 PM
 *
 */

package com.mihost.ebooks

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val background = object : Thread() {
            override fun run() {
                try {
                    // Thread will sleep for 3 seconds
                    Thread.sleep((2 * 1000).toLong())

                    // After 5 seconds redirect to another intent
                    val i = Intent(baseContext, MainActivity::class.java)
                    startActivity(i)
                    //Remove activity
                    finish()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
        // start thread
        background.start()
    }
}

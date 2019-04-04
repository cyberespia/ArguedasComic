/*
 * *
 *  * Creado por  Mario Burga - MiHost.com el 13/08/18 12:55 PM
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Última modificación 13/08/18 12:55 PM
 *
 */

package com.mihost.ebooks

data class Comics(
        val nombre: String,
        val autor: String,
        val year: Int,
        val description: String,
        val cover: Int,
        val codigo: String
)


data class Ventas(
        val nombreVolumen: String

)
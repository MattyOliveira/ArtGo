package br.com.t4.artgoproject.extension

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController

fun AppCompatActivity.navigate(destination: String) {
    NavController(this).navigate(convertDestination(destination))
}

private fun convertDestination(destination: String) = Uri.parse("app://artgo/$destination")
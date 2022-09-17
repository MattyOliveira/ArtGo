package br.com.t4.artgoproject.extension

import android.net.Uri
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions

fun Fragment.navigate(destination: String) {
    findNavController().navigate(convertDestination(destination))
}

private fun convertDestination(destination: String) = Uri.parse("app://artgo/$destination")
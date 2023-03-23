package com.example.bininfo.utilits

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.example.bininfo.R

fun AppCompatActivity.replaceFragment(fragment: Fragment, addStack: Boolean = true) {
    if (addStack) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.dataContainer, fragment)
            .commit()
    } else {
        supportFragmentManager.beginTransaction()
            .replace(R.id.dataContainer, fragment)
            .commit()
    }
}

fun Fragment.replaceFragment(fragment: Fragment) {
    this.fragmentManager?.beginTransaction()
        ?.addToBackStack(null)
        ?.replace(R.id.dataContainer, fragment)
        ?.commit()
}

fun intentPhone(Phone: String): Intent {
    val query: Uri = Uri.parse("tel:${Phone}")
    val intent = Intent(Intent.ACTION_DIAL, query)
    return intent
}

fun intentUrl(Url: String): Intent {
    val queryUrl: Uri = Uri.parse("https://${Url}")
    val intent = Intent(Intent.ACTION_VIEW, queryUrl)
    return intent
}

fun intentCoordinates(Coordinates: String): Intent {
    val queryCoordinates: Uri = Uri.parse("geo:${Coordinates}")
    val intent = Intent(Intent.ACTION_VIEW, queryCoordinates)
    return intent
}

fun Fragment.showToast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

/*Открытие и скрытие клавиатуры*/
fun Fragment.hideKeyboard() = activity?.hideKeyboard()
fun Activity.showKeyboard() = WindowInsetsControllerCompat(window, window.decorView).show(
    WindowInsetsCompat.Type.ime()
)

fun Activity.hideKeyboard() =
    WindowInsetsControllerCompat(window, window.decorView).hide(WindowInsetsCompat.Type.ime())
/*Открытие и скрытие клавиатуры*/

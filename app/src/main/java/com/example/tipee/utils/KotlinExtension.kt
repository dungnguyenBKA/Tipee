package com.example.tipee.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import java.io.ByteArrayOutputStream


fun View.setEnableView(isEnable: Boolean){
    this.isEnabled = isEnable
    if (isEnable) {
        this.alpha = 1.0f
    } else {
        this.alpha = 0.3f
    }
}

fun View.hide(){
    this.visibility = View.GONE
}

fun View.show(){
    this.visibility = View.VISIBLE
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.convertImageToBase64(uri: Uri) : String {
    val imageStream = contentResolver.openInputStream(uri)
    val bitmap = BitmapFactory.decodeStream(imageStream)
    return encodeImage(bitmap)
}

private fun encodeImage(bm: Bitmap): String {
    val byteStream = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.JPEG, 100, byteStream)
    val b: ByteArray = byteStream.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}
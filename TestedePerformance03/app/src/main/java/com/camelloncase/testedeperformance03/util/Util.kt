package com.camelloncase.testedeperformance03.util

import android.content.Context
import android.widget.Toast

fun showMessageToUser(context: Context, message: String) {

    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

}
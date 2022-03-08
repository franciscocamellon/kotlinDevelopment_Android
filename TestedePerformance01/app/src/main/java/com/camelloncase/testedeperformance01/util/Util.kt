package com.camelloncase.testedeperformance01.util

import android.app.Activity
import android.content.Context
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import java.text.SimpleDateFormat
import java.util.*

fun formattedCurrentDate(pattern: String): String {

    val date = Calendar.getInstance().time
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())

    return formatter.format(date)
}

fun showMessageToUser(context: Context, message: String) {

    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

}

fun getExternalStorageReadOnlyStatus() : Boolean {

    val extStorageState = Environment.getExternalStorageState()
    return Environment.MEDIA_MOUNTED_READ_ONLY == extStorageState

}

fun getExternalStorageAvailableStatus() : Boolean {

    val extStorageState = Environment.getExternalStorageState()
    return Environment.MEDIA_MOUNTED == extStorageState

}

fun callPermissionDialog(context: Context,
                         activity: Activity,
                         message: String,
                         permissions: Array<String>,
                         permissionCode: Int) {

    val permissionDialog = AlertDialog.Builder(context)
        .setTitle("Requested permission")
        .setMessage(message)
        .setPositiveButton("Ok")
        { dialog, _ ->
            ActivityCompat.requestPermissions(activity, permissions, permissionCode)
            dialog.dismiss()
        }
        .setNegativeButton("Cancel")
        { dialog, _ ->
            dialog.dismiss()
        }
    permissionDialog.show()
}

fun getFileName(fileName: String) : String{

    val currentDate = formattedCurrentDate("dd-MMM-yy_hh:mm")

    return "${fileName}_${currentDate}"

}

fun prepareFileData(recipeName: String, recipeStyle: String) : String {

    val currentDate = formattedCurrentDate("dd-MMM-yy_hh:mm")

    return "${recipeName}\n${recipeStyle}\n${currentDate}"
}

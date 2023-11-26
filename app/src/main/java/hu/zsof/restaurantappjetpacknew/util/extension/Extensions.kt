package hu.zsof.restaurantappjetpacknew.util.extension

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

fun showToast(
    context: Context,
    message: String?,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(context, message, duration).show()
}

@SuppressLint("Recycle")
fun getRealPathFromURI(uri: Uri, context: Context): String? {

    val returnCursor = context.contentResolver.query(uri, null, null, null, null)
    val nameIndex = returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor.moveToFirst()
    val name = returnCursor.getString(nameIndex)
    val file = File(context.filesDir, name)
    try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        var read = 0
        val maxBufferSize = 1 * 1024 * 1024
        val bytesAvailable: Int = inputStream?.available() ?: 0
        val bufferSize = bytesAvailable.coerceAtMost(maxBufferSize)
        val buffers = ByteArray(bufferSize)
        while (inputStream?.read(buffers).also {
                if (it != null) {
                    read = it
                }
            } != -1) {
            outputStream.write(buffers, 0, read)
        }
        inputStream?.close()
        outputStream.close()
    } catch (e: java.lang.Exception) {
        Timber.e("Exception  ${e.message!!}")
    }
    return file.path
}
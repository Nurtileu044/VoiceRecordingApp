package kz.ablazim.voicerecordingapp.base

import android.content.Context
import kotlinx.coroutines.CancellationException
import kz.ablazim.voicerecordingapp.BuildConfig
import java.io.IOException

class ErrorHandler(private val context: Context) {

    fun handle(e: Throwable) {
        if (e is CancellationException) return
        if (BuildConfig.DEBUG && e is IOException) {
//            context.longToast(e.message ?: "")
        } else {
//            context.toast("")
        }

    }
}

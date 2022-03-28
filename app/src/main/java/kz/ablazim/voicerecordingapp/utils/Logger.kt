package kz.ablazim.voicerecordingapp.utils

import android.util.Log
import timber.log.Timber

interface Logger {
    fun v(message: String, throwable: Throwable? = null)
    fun d(message: String, throwable: Throwable? = null)
    fun i(message: String, throwable: Throwable? = null)
    fun w(message: String, throwable: Throwable? = null)
    fun e(message: String, throwable: Throwable? = null)
}

class DefaultLogger(debugMode: Boolean) : Logger {

    init {
        if (debugMode) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashlyticsTree())
        }
    }

    override fun v(message: String, throwable: Throwable?): Unit = Timber.v(throwable, message)
    override fun d(message: String, throwable: Throwable?): Unit = Timber.d(throwable, message)
    override fun i(message: String, throwable: Throwable?): Unit = Timber.i(throwable, message)
    override fun w(message: String, throwable: Throwable?): Unit = Timber.e(throwable, message)
    override fun e(message: String, throwable: Throwable?): Unit = Timber.e(throwable, message)
}

private class CrashlyticsTree : Timber.Tree() {

    override fun isLoggable(tag: String?, priority: Int): Boolean = priority >= Log.WARN

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
    }
}
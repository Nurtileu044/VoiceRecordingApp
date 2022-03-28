package kz.ablazim.voicerecordingapp

import android.app.Application
import kz.ablazim.voicerecordingapp.voicerecordings.VoiceRecordingsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                VoiceRecordingsModule.create()
            )
        }
    }
}
package kz.ablazim.voicerecordingapp.voicerecordings

import android.content.Context
import kz.ablazim.voicerecordingapp.BuildConfig
import kz.ablazim.voicerecordingapp.base.BaseViewModelDependency
import kz.ablazim.voicerecordingapp.base.ErrorHandler
import kz.ablazim.voicerecordingapp.di.InjectionModule
import kz.ablazim.voicerecordingapp.utils.DefaultLogger
import kz.ablazim.voicerecordingapp.utils.Logger
import kz.ablazim.voicerecordingapp.voicerecordings.presentation.VoiceRecordingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel

import org.koin.core.module.Module
import org.koin.dsl.module

object VoiceRecordingsModule : InjectionModule {
    override fun create(): Module = module {
        single<Logger> { DefaultLogger(BuildConfig.DEBUG) }
        single {
            val errorHandler = ErrorHandler(androidContext())
            BaseViewModelDependency(androidContext(), get(), errorHandler)
        }
        viewModel { (context: Context) -> VoiceRecordingsViewModel(get(), context) }
    }
}

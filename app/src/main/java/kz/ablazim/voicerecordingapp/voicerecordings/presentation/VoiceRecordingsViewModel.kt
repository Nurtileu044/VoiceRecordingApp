package kz.ablazim.voicerecordingapp.voicerecordings.presentation

import android.content.Context
import android.content.ContextWrapper
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Environment
import kz.ablazim.voicerecordingapp.base.Action
import kz.ablazim.voicerecordingapp.base.BaseViewModel
import kz.ablazim.voicerecordingapp.base.BaseViewModelDependency
import kz.ablazim.voicerecordingapp.base.ViewState
import java.io.File

class VoiceRecordingsViewModel(
    baseViewModelDependency: BaseViewModelDependency,
    private val context: Context
) : BaseViewModel<VoiceRecordingsViewState, VoiceRecordingsAction>(
    baseViewModelDependency,
    VoiceRecordingsViewState()
) {
    fun onRecordButtonClicked(mediaRecorder: MediaRecorder?) {
        try {
            mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder?.setOutputFile(getRecordingFilePath())
            mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mediaRecorder?.prepare()
            mediaRecorder?.start()

            sendAction(VoiceRecordingsAction.ShowRecordingStartedToast)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onStopButtonClicked(mediaRecorder: MediaRecorder?) {
        mediaRecorder?.stop()
        mediaRecorder?.release()

        sendAction(VoiceRecordingsAction.ShowRecordingStoppedToast)
    }

    fun onPlayButtonClicked(mediaPlayer: MediaPlayer?) {
        try {
            mediaPlayer?.setDataSource(getRecordingFilePath())
            mediaPlayer?.prepare()
            mediaPlayer?.start()

            sendAction(VoiceRecordingsAction.ShowPlayRecordingToast)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getRecordingFilePath(): String {
        val contextWrapper = ContextWrapper(context)
        val musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val file = File(musicDirectory, "testRecordingFile" + ".mp3")
        return file.path
    }
}

data class VoiceRecordingsViewState(
    val isLoading: Boolean = false
) : ViewState

sealed class VoiceRecordingsAction : Action {
    object ShowRecordingStartedToast : VoiceRecordingsAction()
    object ShowRecordingStoppedToast : VoiceRecordingsAction()
    object ShowPlayRecordingToast : VoiceRecordingsAction()
}

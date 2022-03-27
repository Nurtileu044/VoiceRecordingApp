package kz.ablazim.voicerecordingapp.voicerecordings.presentation

import android.Manifest
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kz.ablazim.voicerecordingapp.R
import kz.ablazim.voicerecordingapp.databinding.FragmentVoiceRecordingsBinding
import java.io.File

private const val MICROPHONE_PERMISSION_CODE = 200

class VoiceRecordingsFragment : Fragment(R.layout.fragment_voice_recordings) {

    private lateinit var binding: FragmentVoiceRecordingsBinding
    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVoiceRecordingsBinding.bind(view)

        if (isMicrophonePresent()) {
            getMicrophonePermission()
        }

        with(binding) {
            recordButton.setOnClickListener {
                try {
                    mediaRecorder = MediaRecorder()
                    mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
                    mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                    mediaRecorder?.setOutputFile(getRecordingFilePath())
                    mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                    mediaRecorder?.prepare()
                    mediaRecorder?.start()

                    Toast.makeText(context, "Recording is started", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            stopButton.setOnClickListener {
                mediaRecorder?.stop()
                mediaRecorder?.release()
                mediaRecorder = null

                Toast.makeText(context, "Recording is stopped", Toast.LENGTH_SHORT).show()
            }

            playButton.setOnClickListener {
                try {
                    mediaPlayer = MediaPlayer()
                    mediaPlayer?.setDataSource(getRecordingFilePath())
                    mediaPlayer?.prepare()
                    mediaPlayer?.start()

                    Toast.makeText(context, "Recording is playing", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun isMicrophonePresent(): Boolean {
        return activity?.packageManager?.hasSystemFeature(PackageManager.FEATURE_MICROPHONE) == true
    }

    private fun getMicrophonePermission() {
        if (ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_DENIED
        ) {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.RECORD_AUDIO),
                    MICROPHONE_PERMISSION_CODE
                )
            }
        }
    }

    private fun getRecordingFilePath(): String {
        val contextWrapper = ContextWrapper(context)
        val musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val file = File(musicDirectory, "testRecordingFile" + ".mp3")
        return file.path
    }
}
package kz.ablazim.voicerecordingapp.voicerecordings.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kz.ablazim.voicerecordingapp.R
import kz.ablazim.voicerecordingapp.databinding.FragmentVoiceRecordingsBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

private const val MICROPHONE_PERMISSION_CODE = 200

class VoiceRecordingsFragment : Fragment(R.layout.fragment_voice_recordings) {

    private lateinit var binding: FragmentVoiceRecordingsBinding
    private val viewModel: VoiceRecordingsViewModel by viewModel { parametersOf(context) }
    private var mediaRecorder: MediaRecorder? = MediaRecorder()
    private var mediaPlayer: MediaPlayer? = MediaPlayer()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVoiceRecordingsBinding.bind(view)

        if (isMicrophonePresent()) {
            getMicrophonePermission()
        }

        with(binding) {
            recordButton.setOnClickListener {
                viewModel.onRecordButtonClicked(mediaRecorder)
            }

            stopButton.setOnClickListener {
                viewModel.onStopButtonClicked(mediaRecorder)
            }

            playButton.setOnClickListener {
                viewModel.onPlayButtonClicked(mediaPlayer)
            }
        }

        viewModel.actions.observe(viewLifecycleOwner) { action ->
            when (action) {
                is VoiceRecordingsAction.ShowRecordingStartedToast -> Toast.makeText(
                    context,
                    "Recording is started",
                    Toast.LENGTH_SHORT
                ).show()
                is VoiceRecordingsAction.ShowRecordingStoppedToast -> {
                    mediaRecorder = null
                    Toast.makeText(context, "Recording is stopped", Toast.LENGTH_SHORT).show()
                }
                is VoiceRecordingsAction.ShowPlayRecordingToast -> Toast.makeText(
                    context,
                    "Recording is playing",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer = null
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
}
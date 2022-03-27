package kz.ablazim.voicerecordingapp

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import kz.ablazim.voicerecordingapp.databinding.ActivitySingleBinding
import kz.ablazim.voicerecordingapp.extensions.replaceFragment
import kz.ablazim.voicerecordingapp.voicerecordings.presentation.VoiceRecordingsFragment

class SingleActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleBinding.inflate(LayoutInflater.from(applicationContext))
        setContentView(binding.root)

        if (savedInstanceState == null) {
            replaceFragment(VoiceRecordingsFragment())
        }
    }
}
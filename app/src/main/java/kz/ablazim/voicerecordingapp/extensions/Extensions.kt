package kz.ablazim.voicerecordingapp.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kz.ablazim.voicerecordingapp.R

fun FragmentActivity.replaceFragment(
    fragment: Fragment,
    tag: String = fragment::class.java.name
) {
    supportFragmentManager
        .beginTransaction()
        .replace(R.id.container, fragment, tag)
        .commit()
}
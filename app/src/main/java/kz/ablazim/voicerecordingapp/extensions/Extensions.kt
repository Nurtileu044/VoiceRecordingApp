package kz.ablazim.voicerecordingapp.extensions

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
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

fun Fragment.replaceFragment(
    fragment: Fragment,
    @IdRes container: Int = R.id.container,
    addToBackStack: Boolean = true,
    tag: String = fragment::class.java.name
) {
    requireActivity()
        .supportFragmentManager
        .beginTransaction()
        .replace(container, fragment, tag)
        .apply { if (addToBackStack) addToBackStack(tag) }
        .commit()
}

fun Fragment.findFragmentByTag(tag: String) = childFragmentManager.findFragmentByTag(tag)

fun Fragment.backToRoot(tag: String? = null) {
    requireActivity().supportFragmentManager
        .popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
}
package com.riso.defalutapp.ui

import com.riso.defalutapp.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.riso.defalutapp.utils.ScreenAnim
import java.security.InvalidParameterException

sealed class Screen {
    object CarList : Screen()
    object Back : Screen()
}

fun Fragment.navigate(to: Screen, from: Screen) {
    if (to == from) {
        throw InvalidParameterException("Can't navigate to $to")
    }
    when (to) {
        Screen.Back -> {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        Screen.CarList -> {
            navigateWithAnimation(resId = R.id.car_list_fragment)
        }
    }
}

private fun Fragment.cleanupBackStack() {
    while (findNavController().popBackStack()) {
        // empty
    }
}

private fun Fragment.navigateWithAnimation(
    resId: Int,
    bundle: Bundle? = null,
    navigationOptionBuilder: NavOptions.Builder = NavOptions.Builder(),
    animation: ScreenAnim = ScreenAnim.SLIDE_RIGHT_LEFT
) {
    val navOptions = navigationOptionBuilder
        .setEnterAnim(animation.enter)
        .setExitAnim(animation.exit)
        .setPopEnterAnim(animation.popEnter)
        .setPopExitAnim(animation.popExit)
        .build()

    findNavController().navigate(resId, bundle, navOptions)
}

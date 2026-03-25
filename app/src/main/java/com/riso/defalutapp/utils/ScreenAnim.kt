package com.riso.defalutapp.utils

import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import com.riso.defalutapp.R

/**
 * https://stackoverflow.com/questions/21026409/fragment-transaction-animation-slide-in-and-slide-out
 */
class ScreenAnim private constructor(
    @field:AnimRes @field:AnimatorRes @param:AnimatorRes @param:AnimRes val enter: Int,
    @field:AnimRes @field:AnimatorRes @param:AnimatorRes @param:AnimRes val exit: Int,
    @field:AnimRes @field:AnimatorRes @param:AnimatorRes @param:AnimRes val popEnter: Int,
    @field:AnimRes @field:AnimatorRes @param:AnimatorRes @param:AnimRes val popExit: Int
) {
    companion object {
        val NONE: ScreenAnim? = null
        val SLIDE_RIGHT_LEFT = ScreenAnim(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )
    }
}

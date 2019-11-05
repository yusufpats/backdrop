package com.yusufpats.backdrop

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.animation.Interpolator
import android.widget.FrameLayout
import androidx.core.animation.doOnEnd


/**
 *
 *
 * @author Yusuf I Patrawala
 */
class BackdropLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {


    val interpolator: Interpolator? = null
    var duration: Int = 300
    var frontSheet: View? = null
    var peakHeight: Int = 200
    var revealHeight: Int = 0

    private val animatorSet = AnimatorSet()
    private var backdropShown = false
    private val displayHeight: Int

    init {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayHeight = displayMetrics.heightPixels
    }

    fun toggleBackdrop() {
        backdropShown = !backdropShown
        toggleBackdrop(backdropShown)
    }

    private fun toggleBackdrop(showBackdrop: Boolean) {
        if (frontSheet == null) {
            Log.e("BackdropLayer", "FrontSheet view not set")
            return
        }

        // Cancel the existing animations
        animatorSet.removeAllListeners()
        animatorSet.end()
        animatorSet.cancel()

        val translateY: Int = if (revealHeight > 0) {
            convertDpToPixel(revealHeight)
        } else {
            height - convertDpToPixel(peakHeight)
        }

        val animator = ObjectAnimator.ofFloat(frontSheet!!, "translationY", (if (showBackdrop) translateY else 0).toFloat())
        animator.duration = duration.toLong()
        if (interpolator != null) {
            animator.interpolator = interpolator
        }
        animatorSet.play(animator)
        animator.start()
    }

    private fun convertDpToPixel(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

}
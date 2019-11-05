package com.yusufpats.backdrop

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.animation.Interpolator
import android.widget.FrameLayout
import android.widget.ImageView


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

    companion object {
        const val DEFAULT_ANIMATION_DURATION = 300
        const val DEFAULT_PEAK_HEIGHT = 200
    }

    val interpolator: Interpolator? = null
    var duration: Int = DEFAULT_ANIMATION_DURATION
    var frontSheet: View? = null
    var peakHeight: Int = DEFAULT_PEAK_HEIGHT
    var revealHeight: Int = 0
    private var triggerView: ImageView? = null
    private var openIcon: Drawable? = null
    private var closeIcon: Drawable? = null

    private val animatorSet = AnimatorSet()
    private var backdropShown = false
    private val displayHeight: Int

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.BackdropLayout, 0, 0)
            duration =
                typedArray.getInt(R.styleable.BackdropLayout_duration, DEFAULT_ANIMATION_DURATION)
            typedArray.recycle()
        }

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

        //Update the trigger view if set
        triggerView?.let { updateTriggerView(it) }

        val translateY: Int = if (revealHeight > 0) {
            convertDpToPixel(revealHeight)
        } else {
            height - convertDpToPixel(peakHeight)
        }

        val animator = ObjectAnimator.ofFloat(
            frontSheet!!,
            "translationY",
            (if (showBackdrop) translateY else 0).toFloat()
        )
        animator.duration = duration.toLong()
        if (interpolator != null) {
            animator.interpolator = interpolator
        }
        animatorSet.play(animator)
        animator.start()
    }

    fun showBackdrop() {
        if (!backdropShown) {
            toggleBackdrop(true)
        }
    }

    fun hideBackdrop() {
        if (backdropShown) {
            toggleBackdrop(false)
        }
    }

    fun setTriggerView(triggerView: ImageView, openIcon: Drawable, closeIcon: Drawable) {
        this.triggerView = triggerView
        this.openIcon = openIcon
        this.closeIcon = closeIcon

        this.triggerView!!.setOnClickListener { toggleBackdrop() }
    }

    private fun updateTriggerView(triggerView: ImageView) {
        if (openIcon == null || closeIcon == null) {
            Log.e("BackdropLayout", "trigger view icons not set")
            return
        }
        triggerView.setImageDrawable(if (backdropShown) closeIcon else openIcon)
    }

    private fun convertDpToPixel(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

}
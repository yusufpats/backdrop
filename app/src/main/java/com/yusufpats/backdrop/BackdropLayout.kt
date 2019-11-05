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
import androidx.core.animation.doOnEnd


/**
 * BackdropLayout is a custom ViewGroup based on the Material Design Backdrop View
 *
 * @author Yusuf I Patrawala
 */
class BackdropLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        const val DEFAULT_ANIMATION_DURATION = 500
        const val DEFAULT_PEAK_HEIGHT = 200
    }

    /**
     * The animation {@link Interpolator} for the backdrop reveal/hide animation
     */
    val interpolator: Interpolator? = null

    /**
     * The duration of the backdrop reveal/hide animation in milliseconds. The default is {@link DEFAULT_ANIMATION_DURATION}
     */
    var duration: Int =
        DEFAULT_ANIMATION_DURATION

    /**
     * The front layer of the {@link BackdropLayout} which will toggle to show/hide the backdrop layer
     */
    var frontSheet: View? = null

    /**
     * The Peak height of the frontSheet in dp.
     *
     * (NOTE: If Reveal height and Peak Height both are set for the view, the reveal height will take precedence)
     */
    var peakHeight: Int = DEFAULT_PEAK_HEIGHT

    /**
     * The Reveal height of the backdrop in dp.
     */
    var revealHeight: Int = 0

    /**
     * The ImageView which needs to be used as a trigger for the backdrop toggle
     */
    private var triggerView: ImageView? = null

    /**
     * The {@link Drawable} icon for the trigger view when backdrop is not open
     */
    private var openIcon: Drawable? = null

    /**
     * The {@link Drawable} icon for the trigger view when backdrop is open
     */
    private var closeIcon: Drawable? = null

    /**
     * Register a callback to be invoked when this backdrop view is toggled.
     */
    var stateChangeListener: OnStateChangeListener? = null

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

    /**
     * Reveals the backdrop if hidden or vice-versa.
     */
    fun toggleBackdrop() {
        backdropShown = !backdropShown
        toggleBackdrop(backdropShown)
    }

    /**
     * Reveals/hides the backdrop according to the flag provided
     *
     * @param showBackdrop Reveals the backdrop if set to true
     */
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
        stateChangeListener?.let {
            animator.doOnEnd { stateChangeListener?.onBackdropStateChanged(showBackdrop) }
        }

        animator.start()
    }

    /**
     * Reveal the backdrop
     */
    fun showBackdrop() {
        if (!backdropShown) {
            toggleBackdrop(true)
        }
    }

    /**
     * Hide the backdrop
     */
    fun hideBackdrop() {
        if (backdropShown) {
            toggleBackdrop(false)
        }
    }

    /**
     * Sets the view to be used as trigger for toggling the backdrop
     *
     * @param triggerView {@link ImageView} to be used as trigger
     * @param openIcon    {@link Drawable} to be used as the icon for the trigger view when backdrop is not open
     * @param closeIcon   {@link Drawable} to be used as the icon for the trigger view when backdrop is open
     */
    fun setTriggerView(triggerView: ImageView, openIcon: Drawable, closeIcon: Drawable) {
        this.triggerView = triggerView
        this.openIcon = openIcon
        this.closeIcon = closeIcon

        this.triggerView!!.setOnClickListener { toggleBackdrop() }
    }

    /**
     * Update the icon of the triggerView based on the state of the backdrop
     *
     * @param triggerView trigger view for the toggle of the backdrop
     */
    private fun updateTriggerView(triggerView: ImageView) {
        if (openIcon == null || closeIcon == null) {
            Log.e("BackdropLayout", "trigger view icons not set")
            return
        }
        triggerView.setImageDrawable(if (backdropShown) closeIcon else openIcon)
    }

    /**
     * Helper function to convert dp to pixel value
     *
     * @param  dp value to conerted to pixel
     * @return converted pixel value
     */
    private fun convertDpToPixel(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    /**
     * Interface definition for a callback to be invoked when the backdrop view is toggled.
     */
    interface OnStateChangeListener {
        fun onBackdropStateChanged(isRevealed: Boolean)
    }

}
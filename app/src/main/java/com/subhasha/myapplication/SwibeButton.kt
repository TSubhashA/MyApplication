package com.subhasha.myapplication

import android.animation.*
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout


class SwibeButton : RelativeLayout {

    private var slidingButton: ImageView? = null
    private var initialX = 0f
    private var active = false
    private var initialButtonWidth = 0
    private var centerText: TextView? = null
    private var disabledDrawable: Drawable? = null
    private var enabledDrawable: Drawable? = null


    constructor(context:Context) : super(context) {

        init(context,null,-1,-1)
    }

    constructor(context: Context?, attrs: AttributeSet?):super(context, attrs) {

        init(context!!, attrs!!, -1, -1)
    }

    constructor( context:Context, attrs:AttributeSet , defStyleAttr:Int):super(context, attrs, defStyleAttr){

        init(context, attrs, defStyleAttr, -1)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :super(context, attrs, defStyleAttr, defStyleRes){

        init(context!!, attrs!!, defStyleAttr, defStyleRes)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {

        //Adding Background

        val background = RelativeLayout(context)

        val layoutParamsView = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        layoutParamsView.addRule(CENTER_IN_PARENT, TRUE)

        background.background = ContextCompat.getDrawable(context, R.drawable.shape_rounded)

        addView(background, layoutParamsView)


        // Addinng textView

        val centerText = TextView(context)
        this.centerText = centerText

        val layoutParams = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val shimmerLayout=ShimmerFrameLayout(context)



        layoutParams.addRule(CENTER_IN_PARENT, TRUE)
        centerText.text = "SWIPE TO ADD COMMENT" //add any text you need

        centerText.setTextColor(Color.WHITE)
        centerText.setPadding(35, 35, 35, 35)

        val builder = Shimmer.AlphaHighlightBuilder()
        builder.setDirection(com.facebook.shimmer.Shimmer.Direction.RIGHT_TO_LEFT)
        shimmerLayout.setShimmer(builder.build())

        shimmerLayout.addView(centerText,layoutParams)


        background.addView(shimmerLayout, layoutParams)


        //Adding moving icon

        val swipeButton = ImageView(context)
        slidingButton = swipeButton

//        disabledDrawable =
//            ContextCompat.getDrawable(getContext(), R.drawable.ic_lock_open_black_24dp)
        enabledDrawable =
            ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_double_arrow_24)
        slidingButton!!.setImageDrawable(enabledDrawable)
        slidingButton!!.setPadding(40, 40, 40, 40)

        val layoutParamsButton = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        layoutParamsButton.addRule(ALIGN_PARENT_END, TRUE)
        layoutParamsButton.addRule(CENTER_VERTICAL, TRUE)
        swipeButton.background = ContextCompat.getDrawable(context, R.drawable.shape_button)
        swipeButton.setImageDrawable(enabledDrawable)
        initialButtonWidth=swipeButton.width
        addView(swipeButton, layoutParamsButton)

//        initialX=swipeButton.getX()


        //Touch Listener

        setOnTouchListener(getButtonTouchListener());


    }

    @SuppressLint("ClickableViewAccessibility")
    private fun getButtonTouchListener(): OnTouchListener {
        return OnTouchListener { v,  event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN ->

                    return@OnTouchListener true
                MotionEvent.ACTION_MOVE -> {
                    //Movement logic here
                    if (initialX == 0f) {
                        initialX = slidingButton!!.getX();
                    }
                    if (event.getX() > initialX + slidingButton!!.getWidth() / 2 &&
                        event.getX() + slidingButton!!.getWidth() / 2 < getWidth()
                    ) {
//                        slidingButton!!.setX(event.getX() - slidingButton!!.getWidth() / 2)
                        slidingButton!!.setX(width - (event.getX() + slidingButton!!.getWidth() / 2))

                        centerText!!.setAlpha(1 - 1.2f * (slidingButton!!.getX() - event.getX() / slidingButton!!.getWidth()))
                    }

//                    if (width - (event.getX() + slidingButton!!.getWidth() / 2) )

                    if (event.getX() + slidingButton!!.getWidth() / 2 > getWidth() &&
                        slidingButton!!.getX() + slidingButton!!.getWidth() / 2 < getWidth()
                    ) {
                        slidingButton!!.setX((getWidth() - slidingButton!!.getWidth()).toFloat())
                    }

                    if (event.getX() < slidingButton!!.getWidth() / 2 &&
                        slidingButton!!.getX() > 0
                    ) {
                        slidingButton!!.setX(1f);
                    }
                    return@OnTouchListener true
                }
                MotionEvent.ACTION_UP -> {
                    //Release logic here
                    if (active) {
                        collapseButton()
                    } else {
//                        initialButtonWidth = slidingButton!!.getWidth();

                        if (slidingButton!!.getX() !=initialX)
                          if(  slidingButton!!.getWidth() > getWidth() * 0.5) {
                            expandButton()
                        } else {
                            moveButtonBack();
                        }
                    }

                    return@OnTouchListener true
                }

                }

            false
        }
    }

    private fun expandButton() {
        val positionAnimator = ValueAnimator.ofFloat(slidingButton!!.x, 0f)
        positionAnimator.addUpdateListener {
            val x = positionAnimator.animatedValue as Float
            slidingButton!!.x = x
        }
        val widthAnimator = ValueAnimator.ofInt(
            slidingButton!!.width,
            width
        )
        widthAnimator.addUpdateListener {
            val params = slidingButton!!.layoutParams
            params.width = (widthAnimator.animatedValue as Int)
//            params.height=(widthAnimator.animatedValue as Int)
            slidingButton!!.layoutParams = params
        }
        val animatorSet = AnimatorSet()
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                active = true
                slidingButton!!.setImageDrawable(enabledDrawable)
            }
        })
        animatorSet.playTogether(positionAnimator, widthAnimator)
        animatorSet.start()
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun collapseButton() {
        val widthAnimator = ValueAnimator.ofInt(
            slidingButton!!.width,
            initialButtonWidth
        )
        widthAnimator.addUpdateListener {
            val params = slidingButton!!.layoutParams
            params.width = (widthAnimator.animatedValue as Int)
            slidingButton!!.layoutParams = params
        }
        widthAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                active = false
                slidingButton!!.setImageDrawable(enabledDrawable)
            }
        })
        val objectAnimator = ObjectAnimator.ofFloat(
            centerText, "alpha", 1f
        )
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(objectAnimator, widthAnimator)
        animatorSet.start()
//        active=true
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun moveButtonBack() {
        val positionAnimator = ValueAnimator.ofFloat(slidingButton!!.x, initialX)
        positionAnimator.interpolator = AccelerateDecelerateInterpolator()
        positionAnimator.addUpdateListener {
            val x = positionAnimator.animatedValue as Float
            slidingButton!!.x = x
        }
        val objectAnimator = ObjectAnimator.ofFloat(
            centerText, "alpha", 1f
        )
        positionAnimator.duration = 200
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(objectAnimator, positionAnimator)
        animatorSet.start()
    }

}
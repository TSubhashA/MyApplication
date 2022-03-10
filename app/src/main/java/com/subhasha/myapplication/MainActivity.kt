package com.subhasha.myapplication

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.RelativeLayout
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.facebook.shimmer.ShimmerFrameLayout
import com.ncorti.slidetoact.SlideToActView
import com.subhasha.slidingbutton.SlideButton


class MainActivity : AppCompatActivity() {

    lateinit var slide:SlideToActView
    var oldPer=0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Shimer
        val swibeBvutton=findViewById(R.id.swibeBvutton) as RelativeLayout

swibeBvutton.visibility=View.GONE
        val shimmer = findViewById(R.id.shimmer) as ShimmerFrameLayout

         slide = findViewById(R.id.example_gray_on_green) as SlideToActView

        val slide_button = findViewById(R.id.slide_button) as SlideButton

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            slide.isForceDarkAllowed = true
        }
        slide.visibility=View.GONE

        slide_button.onSlideCompleteListener = object : SlideButton.OnSlideCompleteListener {
            override fun onSlideComplete(view: SlideButton) {
                Toast.makeText(this@MainActivity, "SlideCompleted", LENGTH_LONG).show()
                slide_button.resetSlider()
            }


        }
        slide.setVisibility(View.VISIBLE)
        var param = slide.height
        val height=param

        slide_button.onSlideToActAnimationEventListener =
            object : SlideButton.OnSlideToActAnimationEventListener {
                override fun onSliding(view: SlideButton, threshold: Float) {
                    val per = threshold / slide_button.width
                    Log.e("TAG", "onSliding: $per")
                    Log.e("TAG", "onSlidingHeight: $height")

                    slide.alpha = 1 - 1.3f * per
                    shimmer.alpha = 1 - 1.3f * per

//                    val new=height*per
                    swibeBvutton.visibility=View.VISIBLE
                    swibeBvutton.alpha=0f
                    swibeBvutton.translationY=swibeBvutton.height.toFloat()

//
                    swibeBvutton.animate().translationY(per).alpha(1.3f * per - 1f)
                        .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            swibeBvutton.visibility = View.VISIBLE
                            swibeBvutton.alpha = 1f
                        }
                    })

//                    slideUp(swibeBvutton,per)
//                    val anim = ObjectAnimator.ofFloat(slide, "alpha", 0f, 1f)
//                    anim.duration = 1000
//                    anim.start()

//                    slide_button.animate().translationX(threshold)

//                    param.height= (height*threshold).toInt()
//
//                    slide.layoutParams=param


//                    if (per*100>10) {
////                        slide.animate().translationY(per)
////                        slide.visibility = View.VISIBLE
////
////                        slidingIn(true,per)
//
//                    }
//

//                    slide.visibility=View.GONE
//                        slidingIn(false,per)


//                    slide.invalidate()
//                    if (per == 0f)
//                        slide.visibility=View.GONE
//////                        slide_button.animate().scaleY(1f)
////                    else {
//                        slide.visibility=View.VISIBLE
////                        slide_button.pivotY = 100f
////                        slide_button.animate().scaleY(per + 1)
//                    }



//                    slide.animate().translationY(per)


//                val newHeight=per*height
//                if (newHeight>height)
//                slide_button.animate().scaleY(newHeight)
//                else
//                    slide_button.animate().scaleY(slide.height.toFloat())


//                slide_button.height= per.toInt()


//                if (threshold ==threshold)

//                Toast.makeText(applicationContext,"Sliding", LENGTH_SHORT).show()
                }

                override fun onSlideCompleteAnimationStarted(view: SlideButton, threshold: Float) {

                }

                override fun onSlideCompleteAnimationEnded(view: SlideButton) {

                }

                override fun onSlideResetAnimationStarted(view: SlideButton) {

                }

                override fun onSlideResetAnimationEnded(view: SlideButton) {

                }

            }


        slide.onSlideToActAnimationEventListener =
            object : SlideToActView.OnSlideToActAnimationEventListener {

                override fun onSlideCompleteAnimationEnded(view: SlideToActView) {
                    Toast.makeText(this@MainActivity, "onSlideCompleteAnimationEnded", LENGTH_SHORT)
                        .show()
//                view.visibility=View.GONE
                    slide.resetSlider()


                }

                override fun onSlideCompleteAnimationStarted(
                    view: SlideToActView,
                    threshold: Float
                ) {
                    Toast.makeText(
                        this@MainActivity,
                        "onSlideCompleteAnimationStarted",
                        LENGTH_SHORT
                    ).show()
                    shimmer.stopShimmer()
                    shimmer.visibility = View.GONE

                }

                override fun onSlideResetAnimationEnded(view: SlideToActView) {
                    Toast.makeText(this@MainActivity, "onSlideResetAnimationEnded", LENGTH_SHORT)
                        .show()
                    shimmer.visibility = View.VISIBLE
                    shimmer.startShimmer()
                }

                override fun onSlideResetAnimationStarted(view: SlideToActView) {

                    Toast.makeText(this@MainActivity, "onSlideResetAnimationStarted", LENGTH_SHORT)
                        .show()

                }


            }






//        fun setVisiblity(checkedStatus: Boolean, position: Int) {
//
//            if (checkedStatus) {
////                isExpansion = true
//
//                swipeRelativeLayout.visibility = View.GONE
////                llLayout_comment.alpha=0f
//                question_option_ll.visibility = View.GONE
//                llLayout_comment.visibility = View.VISIBLE
//
////                val anim = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
//
//                //                llLayout_comment.alpha=0.0f
////                if (commentPos == position)
////                    editText.editText?.setText(commentSaved)
////                else
////                    editText.editText?.setText("")
////
////                llLayout_comment.animate().alpha(1.0f).setDuration(500).start()
////                llLayout_question.setBackgroundColor(context.resources.getColor(R.color.purple_6))
//
////                val anim =AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
////                llLayout_comment.layoutAnimation=anim
//
//            } else {
//
//                    comment_saved.visibility = View.GONE
//
//                llLayout_question.setBackgroundColor(Color.WHITE)
//                llLayout_comment.visibility = View.GONE
//                question_option_ll.visibility = View.VISIBLE
//                optionValue.alpha=1f
////                optionValue.setTextColor(Color.BLACK)
//            }
//        }
//
//    }


    }

    private fun slidingIn(check:Boolean,valu:Float) {
     if (check) {

         slide.visibility = View.VISIBLE
         val transAnimation: ObjectAnimator =
             ObjectAnimator.ofFloat(slide, "translationY", slide.height*oldPer,valu)
//         transAnimation.duration = 1000 //set duration

         transAnimation.start() //start animation
         oldPer=valu


//         slide.visibility = View.VISIBLE
//         val animation = TranslateAnimation(0f, 0f, slide.height.toFloat(), 0f)
//         animation.apply {
//             duration = 1000
//             fillAfter = true
//             start()
//         }
     }else
     {
         slide.visibility = View.GONE
         val animation = TranslateAnimation(0f, 0f, 0f, slide.height.toFloat())
         animation.apply {

             start()
         }
     }

    }

    fun slideUp(view: View,per:Float) {
        view.visibility = View.VISIBLE
        view.alpha = 0f
        if (view.height > 0) {
            slideUpNow(view,per)
        } else {
            // wait till height is measured
            view.post { slideUpNow(view,per) }
        }
    }

    private fun slideUpNow(view: View,per:Float) {
        view.translationY = view.height.toFloat()*per
        view.animate()
            .translationY(0f)
            .alpha(1f)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = View.VISIBLE
                    view.alpha = 1f
                }
            })
    }
}
package com.example.feedapplegame.activity

import android.animation.*
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.feedapplegame.R
import com.example.feedapplegame.databinding.ActivityMainBinding
import com.example.feedapplegame.model.Feed


class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding:ActivityMainBinding
    private lateinit var  feed:Feed
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this@MainActivity,
            R.layout.activity_main
        )
        setInitialView()
        setOnClickListeners()
    }

    private fun setInitialView() {
        activityMainBinding.llButtonPlayContainer.visibility=View.GONE
        activityMainBinding.llButtonContainer.visibility=View.VISIBLE
        activityMainBinding.tvYouNeed.text = getString(R.string.you_need)
        activityMainBinding.ivDinoContainer.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_stage_one_dino))
         feed=Feed(1,0)
        activityMainBinding.feed=feed

    }

    private fun setOnClickListeners() {
        activityMainBinding.llButtonContainer.setOnClickListener {
            setNewData()
           startAnimation()
        }
        activityMainBinding.llButtonPlayContainer.setOnClickListener{
            setInitialView()
        }
    }

    private fun startAnimation() {
        activityMainBinding.ivDinoContainer.pivotY = activityMainBinding.ivDinoContainer.measuredHeight
            .toFloat()
        val scaleUp = ObjectAnimator.ofPropertyValuesHolder(
            activityMainBinding.ivDinoContainer,
            PropertyValuesHolder.ofFloat("scaleX", 1.1f),
            PropertyValuesHolder.ofFloat("scaleY", 1.1f)
        )
        val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
            activityMainBinding.ivDinoContainer,
            PropertyValuesHolder.ofFloat("scaleX", 1.0f),
            PropertyValuesHolder.ofFloat("scaleY", 1.0f)
        )

        scaleUp.repeatMode = ValueAnimator.REVERSE
        scaleUp.duration=500
        scaleDown.duration=500
        scaleUp.start()
        scaleUp.addListener(object :Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                scaleDown.start()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })
    }

    private fun setNewData() {
        val count=feed.appleCount+1
        val stage=(count/5)+1
        val drawable:Drawable?
        when(stage){
            1->{
                drawable=ContextCompat.getDrawable(this,R.drawable.ic_stage_one_dino)
            }
            2->{
                drawable=ContextCompat.getDrawable(this,R.drawable.ic_stage_two_dino)
            }
            3->{
                drawable=ContextCompat.getDrawable(this,R.drawable.ic_stage_three_dino)
            }
            4-> {
                drawable = ContextCompat.getDrawable(this, R.drawable.ic_stage_four_dino)
            }
            else->{
                drawable=ContextCompat.getDrawable(this,R.drawable.ic_stage_five_dino)
                activityMainBinding.tvYouNeed.text = getString(R.string.congratulation)
                activityMainBinding.llButtonContainer.visibility=View.GONE
                activityMainBinding.llButtonPlayContainer.visibility=View.VISIBLE
            }
        }
        val newFeed=feed.copy(stage = stage, appleCount = count)
        feed=newFeed
        activityMainBinding.feed=newFeed
        activityMainBinding.ivDinoContainer.setImageDrawable(drawable)
    }
}
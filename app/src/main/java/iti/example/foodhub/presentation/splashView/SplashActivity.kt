package iti.example.foodhub.presentation.splashView

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import iti.example.foodhub.R
import iti.example.foodhub.viewModel.splash.SplashViewModel
import iti.example.foodhub.viewModel.splash.SplashViewModelFactory

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModels {
        SplashViewModelFactory(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    override fun onStart() {
        super.onStart()
        animateLogo()
        viewModel.navigateToAuth()
    }

    private fun animateLogo() {
        val logo = findViewById<CardView>(R.id.logo_splash)
        val appName = findViewById<ImageView>(R.id.app_name_splash)

        val logoAnimator = ObjectAnimator.ofFloat(
            logo,View.ALPHA,0f,1f).apply {
            duration = 800
            interpolator = AccelerateDecelerateInterpolator()
        }
        val logoScaleXAnimator = ObjectAnimator.ofFloat(logo, View.SCALE_X, 0.8f, 1f).apply {
            duration = 1000
            interpolator = OvershootInterpolator()
        }
        val logoScaleYAnimator = ObjectAnimator.ofFloat(logo, View.SCALE_Y, 0.8f, 1f).apply {
            duration = 1000
            interpolator = OvershootInterpolator()
        }

        val appNameAnimator = ObjectAnimator.ofFloat(
            appName,View.ALPHA,0f,1f).apply {
            duration = 800
            startDelay = 300
            interpolator = AccelerateDecelerateInterpolator()
        }
        val appNameTranslationY = ObjectAnimator.ofFloat(appName, View.TRANSLATION_Y, 50f, 0f).apply {
            duration = 800
            startDelay = 300
            interpolator = OvershootInterpolator()
        }

        AnimatorSet().apply {
            playTogether(logoAnimator,logoScaleXAnimator,logoScaleYAnimator,appNameAnimator,appNameTranslationY)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        viewModel.navigateToAuth()
                    }, 500)
                    super.onAnimationEnd(animation)
                }
            })
            start()
        }

    }
}
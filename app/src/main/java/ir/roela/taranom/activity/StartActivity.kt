package ir.roela.taranom.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import androidx.activity.ComponentActivity
import ir.roela.taranom.R
import ir.roela.taranom.databinding.ActivityStartBinding

class StartActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityStartBinding.inflate(LayoutInflater.from(this))
        setContentView(viewBinding.root)
        viewBinding.imgLogo.startAnimation(
            AnimationUtils.loadAnimation(
                this,
                R.anim.fade_in_logo
            )
        )
        viewBinding.txtTitle.startAnimation(
            AnimationUtils.loadAnimation(
                this,
                R.anim.fade_in_logo
            )
        )
        viewBinding.txtSubTitle.startAnimation(
            AnimationUtils.loadAnimation(
                this,
                R.anim.fade_in_logo
            )
        )

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }
}
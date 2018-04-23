package com.shankar.pickit.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.TextUtils
import android.view.Window
import android.view.WindowManager
import com.shankar.pickit.R
import com.shankar.pickit.data.USER_PNR
import com.shankar.pickit.data.USER_PNR2
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        splashVerifyPNRBtn.setOnClickListener {
            var userPnr = splashPNREt.text.toString().trim()
            if(!TextUtils.isEmpty(userPnr)){
                if (userPnr.equals(USER_PNR2)){
                    startActivity(Intent(this, DashboardActivity::class.java))
                    //TODO:Add PNR to SHAREDPREF to prevent asking again
                    finish()
                }
                else{
                    Snackbar.make(
                            splashRootLayout, // Parent view
                            "Invalid PNR", // Message to show
                            Snackbar.LENGTH_SHORT // How long to display the message.
                    ).show()
                }
            }
            else{
                Snackbar.make(
                        splashRootLayout, // Parent view
                        "Enter your PNR number", // Message to show
                        Snackbar.LENGTH_SHORT // How long to display the message.
                ).show()
            }
        }

    }
}

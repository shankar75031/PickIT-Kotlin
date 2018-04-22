package com.shankar.pickit.activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.shankar.pickit.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.*

class ProfileActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        var currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser != null){
            settingsSignIn.visibility = View.GONE
            var userImageURL = currentUser.photoUrl.toString()
            if(!TextUtils.isEmpty(userImageURL)){
                Picasso.with(this)
                        .load(userImageURL)
                        .placeholder(R.drawable.user_placeholder)
                        .into(settingsUserImage)
            }
            settingsUserEmailSignin.text = currentUser.email.toString()
            settingsUserNameSignin.text = currentUser.displayName.toString()

        }
        settingsSignIn.setOnClickListener {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(
                                    AuthUI.IdpConfig.EmailBuilder().build(),
                                    AuthUI.IdpConfig.GoogleBuilder().build()))
                            .setIsSmartLockEnabled(false)
                            .setLogo(R.drawable.app_logo)
                            .setTheme(R.style.FullscreenTheme)
                            .build(),
                    RC_SIGN_IN)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            // Successfully signed in
            if (resultCode == Activity.RESULT_OK) {
                settingsSignIn.visibility = View.GONE
                Toast.makeText(this, "Logged in successfully", Toast.LENGTH_SHORT).show()
                return
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Toast.makeText(this, "Logged in cancelled by user", Toast.LENGTH_SHORT).show()
                    return
                }

                if (response.error!!.errorCode == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, "No network", Toast.LENGTH_SHORT).show()
                    return
                }

                Toast.makeText(this, "Failed to login", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

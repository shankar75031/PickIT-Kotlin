package com.shankar.pickit.activities

import android.app.Activity
import android.app.FragmentTransaction
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.MenuItem
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.shankar.pickit.R
import com.shankar.pickit.data.USER_VISITING_AIRPORTS
import com.shankar.pickit.fragments.CartFragment
import com.shankar.pickit.fragments.OrdersFragment
import com.shankar.pickit.fragments.RewardsFragment
import com.shankar.pickit.fragments.StoreFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.app_bar_dashboard.*
import kotlinx.android.synthetic.main.nav_header_dashboard.view.*
import java.util.*


class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val RC_SIGN_IN = 1

    private var transaction: FragmentTransaction? = null
    private var fireBaseAuth: FirebaseAuth? = null
    private var authStateListner: FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        fireBaseAuth = FirebaseAuth.getInstance()
        authStateListner = FirebaseAuth.AuthStateListener { firebaseAuth: FirebaseAuth ->
            checkLogin()
        }


        nav_view.setNavigationItemSelectedListener(this)
        supportActionBar!!.title = "Store"
        nav_view.menu.getItem(0).isChecked = true

        val storeFragment = StoreFragment()
        transaction = fragmentManager.beginTransaction()
        transaction!!.replace(R.id.dashboardFrahmentHolder, storeFragment)
        transaction!!.commit()
    }

    private fun checkLogin() {
        try {
            var textToDisplay = USER_VISITING_AIRPORTS[0] + " -> " + USER_VISITING_AIRPORTS[2]
            nav_view.nav_bar_user_email.text = textToDisplay
            var user = FirebaseAuth.getInstance().currentUser
            if (user == null) {
                nav_view.menu.findItem(R.id.nav_rewards).isVisible = false
                nav_view.menu.findItem(R.id.nav_cart).isVisible = false
                nav_view.menu.findItem(R.id.nav_orders).isVisible = false
                nav_view.menu.findItem(R.id.nav_logout).isVisible = false
                nav_view.menu.findItem(R.id.nav_settings).isVisible = false
                nav_view.menu.findItem(R.id.nav_login).isVisible = true
            } else {
                nav_view!!.menu.findItem(R.id.nav_rewards).isVisible = true
                nav_view!!.menu.findItem(R.id.nav_cart).isVisible = true
                nav_view!!.menu.findItem(R.id.nav_orders).isVisible = true
                nav_view!!.menu.findItem(R.id.nav_logout).isVisible = true
                nav_view!!.menu.findItem(R.id.nav_settings).isVisible = true
                nav_view!!.menu.findItem(R.id.nav_login).isVisible = false
                var imageUrl = user.photoUrl
                var userName = user.displayName
                if (imageUrl != null){
                    Picasso.with(this)
                            .load(imageUrl.toString())
                            .placeholder(R.drawable.user_placeholder)
                            .into(nav_view.nav_bar_image)
                    nav_view.nav_bar_header_layout.background
                }
                if (!TextUtils.isEmpty(userName)){
                    nav_view.nav_bar_user_name.text = userName
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            val f = this.fragmentManager.findFragmentById(R.id.dashboardFrahmentHolder)
            if (f !is StoreFragment) {
                supportActionBar!!.title = "Store"
                val storeFragment = StoreFragment()
                transaction = fragmentManager.beginTransaction()
                transaction!!.replace(R.id.dashboardFrahmentHolder, storeFragment)
                transaction!!.commit()
                nav_view.menu.getItem(0).isChecked = true
            } else {
                super.onBackPressed()
            }

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_store -> {
                if (checkFragmentType(0)) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                } else {
                    supportActionBar!!.title = "Store"
                    val storeragment = StoreFragment()
                    transaction = fragmentManager.beginTransaction()
                    transaction!!.replace(R.id.dashboardFrahmentHolder, storeragment)
                    transaction!!.commit()
                }
            }
            R.id.nav_cart -> {
                if (checkFragmentType(1)) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                } else {
                    supportActionBar!!.title = "Cart"
                    val cartFragment = CartFragment()
                    transaction = fragmentManager.beginTransaction()
                    transaction!!.replace(R.id.dashboardFrahmentHolder, cartFragment)
                    transaction!!.commit()
                }
            }
            R.id.nav_orders -> {

                if (checkFragmentType(2)) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                } else {
                    supportActionBar!!.title = "Orders"
                    val ordersFragment = OrdersFragment()
                    transaction = fragmentManager.beginTransaction()
                    transaction!!.replace(R.id.dashboardFrahmentHolder, ordersFragment)
                    transaction!!.commit()
                }
            }
            R.id.nav_rewards -> {

                if (checkFragmentType(3)) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                } else {
                    supportActionBar!!.title = "Rewards"
                    val rewardsFragment = RewardsFragment()
                    transaction = fragmentManager.beginTransaction()
                    transaction!!.replace(R.id.dashboardFrahmentHolder, rewardsFragment)
                    transaction!!.commit()
                }
            }
            R.id.nav_share -> {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, "http://codepath.com")
                startActivity(Intent.createChooser(shareIntent, "Share link using"))
            }
            R.id.nav_feedback -> {

            }
            R.id.nav_settings -> {
                startActivity(Intent(this, ProfileActivity::class.java))
            }
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_login -> {
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

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    //checkFragmentTypeFunction returns true if the currently loded fragment is of the same type that the user is planning to load
    fun checkFragmentType(fragmentNumber: Int): Boolean {
        val fragment = this.fragmentManager.findFragmentById(R.id.dashboardFrahmentHolder)
        when (fragmentNumber) {
            0 -> if (fragment is StoreFragment) {
                return true
            }
            1 -> if (fragment is CartFragment) {
                return true
            }
            2 -> if (fragment is OrdersFragment) {
                return true
            }
            3 -> if (fragment is RewardsFragment) {
                return true
            }
            else -> {
                return false
            }
        }
        return false
    }


    override fun onStart() {
        super.onStart()
        fireBaseAuth!!.addAuthStateListener(authStateListner!!)
    }

    override fun onStop() {
        super.onStop()
        fireBaseAuth!!.removeAuthStateListener(authStateListner!!)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            // Successfully signed in
            if (resultCode == Activity.RESULT_OK) {
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


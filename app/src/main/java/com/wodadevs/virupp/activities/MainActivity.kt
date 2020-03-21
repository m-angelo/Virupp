package com.wodadevs.virupp.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton
import com.wodadevs.virupp.R
import com.wodadevs.virupp.classes.UserClass
import com.wodadevs.virupp.fragments.HandsFragment
import com.wodadevs.virupp.fragments.InfoFragment
import com.wodadevs.virupp.fragments.ShopsFragment
import com.wodadevs.virupp.services.LocationService
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    companion object{
        var user_doc: UserClass ?= null
    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser

        updateUI(currentUser)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
        val db = Firebase.firestore
        auth = FirebaseAuth.getInstance()
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val user_db = db.collection("users").document(user!!.uid)
                    Log.d("TAG", "signInAnonymously:success")
                    user_db
                        .get()
                        .addOnSuccessListener { document ->
                            if (document.data != null) {
                                Log.d("receive data","${document.data}")
                                user_doc = document.toObject<UserClass>()
                                if (System.currentTimeMillis() - user_doc!!.last_shop!!.toLong() > 360000){
                                    user_db
                                        .update("shops",listOf<String>() )
                                        .addOnSuccessListener { Log.d("TAG", "Shops resetted!") }
                                        .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }
                                }
                                if (System.currentTimeMillis() - user_doc!!.last_wash!!.toLong() > 86400000 && user_doc!!.streak!! != 0 ){
                                    user_db
                                        .update("streak",0 )
                                        .addOnSuccessListener { Log.d("TAG", "Streak resetted!") }
                                        .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }
                                }

                            } else {
                                Log.d("send data","data")
                                val user_data = hashMapOf(
                                    "last_shop" to 0.toLong(),
                                    "last_wash" to 0.toLong(),
                                    "streak" to 0,
                                    "washes" to 0,
                                    "shops" to listOf<String>()
                                )

                                db.collection("users").document(user!!.uid).set(user_data)
                            }
                            user_doc!!.id = user!!.uid
                            Log.d("userdoc",user_doc!!.id)
                        }

                    //
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInAnonymously:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

            }
        if (Build.VERSION.SDK_INT >= 23){
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions( arrayOf( Manifest.permission.ACCESS_FINE_LOCATION),1)
            }else{
                //req pern
                startTrackingService()
            }
        }else{
            startTrackingService()
        }

        val info_icon = ImageView(this)
        val shop_icon = ImageView(this)
        val hands_icon = ImageView(this)
        //CHANGE THAT LINE ONLY TESTING SUITE
        replaceFragment(InfoFragment())
        info_icon.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.info
            ))
        shop_icon.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.cart
            ))
        hands_icon.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.hold
            ))
        val transp = ColorDrawable(Color.TRANSPARENT);
        val itembuilder = SubActionButton.Builder(this).setBackgroundDrawable(transp)
        val shop_btn = itembuilder.setContentView(shop_icon).build()
        val info_btn = itembuilder.setContentView(info_icon).build()
        val hands_btn = itembuilder.setContentView(hands_icon).build()

        shop_btn.setOnClickListener{
            replaceFragment(ShopsFragment())
            btn_radial.performClick()
            Log.d("replace","shop_btn")

        }
        info_btn.setOnClickListener{
            replaceFragment(InfoFragment())
            btn_radial.performClick()
            Log.d("replace","info_btn")
        }
        hands_btn.setOnClickListener{
            replaceFragment(HandsFragment())
            btn_radial.performClick()
            Log.d("replace","hands_btn")

        }
        btn_radial.setOnClickListener{
            val animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
            btn_radial.startAnimation(animation)
        }
        val action_menu = FloatingActionMenu.Builder(this)
            .addSubActionView(shop_btn)
            .addSubActionView(info_btn)
            .addSubActionView(hands_btn)
            .attachTo(btn_radial)
            .build()


    }

    fun startTrackingService(){
        val intent =Intent(this,LocationService::class.java)
        startService(intent)
    }

    private fun updateUI(user: FirebaseUser?) {


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startTrackingService()
            }}}

        }
    }


fun AppCompatActivity.replaceFragment(fragment: Fragment){
    val fragmentManager = supportFragmentManager
    val transaction = fragmentManager.beginTransaction()
    transaction.setCustomAnimations(R.anim.fadein,R.anim.fadeout)
    transaction.replace(R.id.host,fragment)
    transaction.commit()
}
package com.healtify.virupp.activities

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.healtify.virupp.R
import com.healtify.virupp.fragments.HandsFragment
import com.healtify.virupp.fragments.InfoFragment
import com.healtify.virupp.fragments.ShopsFragment
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
        val info_icon = ImageView(this)
        val shop_icon = ImageView(this)
        val hands_icon = ImageView(this)
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
            Log.d("replace","shop_btn")

        }
        info_btn.setOnClickListener{
            replaceFragment(InfoFragment())
            Log.d("replace","info_btn")
        }
        hands_btn.setOnClickListener{
            replaceFragment(HandsFragment())
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
}

fun AppCompatActivity.replaceFragment(fragment: Fragment){
    val fragmentManager = supportFragmentManager
    val transaction = fragmentManager.beginTransaction()
    transaction.setCustomAnimations(R.anim.fadein,R.anim.fadeout)
    transaction.replace(R.id.host,fragment)
    transaction.commit()
}
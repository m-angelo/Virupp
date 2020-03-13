package com.healtify.virupp.activities

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.healtify.virupp.R
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
        val menu_icon = ImageView(this)
        val shop_icon = ImageView(this)
        menu_icon.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.cart
            ))
        shop_icon.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.cart
            ))

        val transp = ColorDrawable(Color.TRANSPARENT);
        val itembuilder = SubActionButton.Builder(this).setBackgroundDrawable(transp)
        val shop_btn = itembuilder.setContentView(shop_icon).build()
        val menu_btn = itembuilder.setContentView(menu_icon).build()
        shop_btn.setOnClickListener{

        }
        menu_btn.setOnClickListener{

        }
        val action_menu = FloatingActionMenu.Builder(this)
            .addSubActionView(shop_btn)
            .addSubActionView(menu_btn)
            .attachTo(btn_radial)
            .build()


    }
}

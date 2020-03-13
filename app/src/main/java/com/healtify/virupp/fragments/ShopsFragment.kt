package com.healtify.virupp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.healtify.virupp.R
import com.healtify.virupp.classes.Shops_class
import kotlinx.android.synthetic.main.shops.*

class ShopsFragment : Fragment() {

    private val ShopsData = listOf(
        Shops_class("Biedronka","ul Strazacka"),
        Shops_class("Carrefour","ul Nosala"),
        Shops_class("Biedronka","ul Krzyzowa")

    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.shops, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shops_view.apply{
            layoutManager = LinearLayoutManager(activity)
            adapter = ShopsAdapter(ShopsData,context)
        }

    }
    companion object {
        fun newInstance(): ShopsFragment = ShopsFragment()
    }
}
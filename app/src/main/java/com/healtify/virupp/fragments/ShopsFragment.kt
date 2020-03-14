package com.healtify.virupp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.healtify.virupp.R
import com.healtify.virupp.classes.ShopsClass
import kotlinx.android.synthetic.main.shops.*

class ShopsFragment : Fragment() {

    private val ShopsData = listOf(
        ShopsClass("Biedronka","ul Strazacka"),
        ShopsClass("Carrefour","ul Nosala"),
        ShopsClass("Biedronka","ul Krzyzowa")

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
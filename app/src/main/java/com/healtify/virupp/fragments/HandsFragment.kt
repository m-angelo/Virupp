package com.healtify.virupp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.healtify.virupp.R
import com.healtify.virupp.activities.WashActivity
import kotlinx.android.synthetic.main.hands.*

class HandsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.hands, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        start_wash.setOnClickListener{
                val intent = Intent(context, WashActivity::class.java)
                    context?.startActivity(intent)
        }
        }

    }


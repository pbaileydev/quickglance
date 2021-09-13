package com.pbaileyapps.shoppingappclone

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import java.security.Permissions


class settings : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view =  inflater.inflate(R.layout.fragment_settings, container, false)
        val switch =  view.findViewById<Switch>(R.id.notify_switch)
        var sharedPreferences = activity?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        var editor = sharedPreferences?.edit()
        var enabled = sharedPreferences?.getBoolean("ENABLED",false)
        if(enabled == true && context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_NOTIFICATION_POLICY) } == PackageManager.PERMISSION_GRANTED){
            switch.isChecked = true
        }
        else{
            switch.isChecked = false
        }
        switch.setOnCheckedChangeListener({_, isChecked->
            if (isChecked){
                editor?.putBoolean("ENABLED",true)
                editor?.commit()
            }
            else{
                editor?.putBoolean("ENABLED",false)
                editor?.commit()
            }
        })
return view
    }



}
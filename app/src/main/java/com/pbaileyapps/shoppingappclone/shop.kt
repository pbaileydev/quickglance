package com.pbaileyapps.shoppingappclone

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pbaileyapps.shoppingappclone.data.Item
import com.pbaileyapps.shoppingappclone.data.ItemViewModel


class shop : Fragment() {
    val args:shopArgs by navArgs()
    lateinit var viewModel: ItemViewModel
    lateinit var recyclerViewAdapter: CustomRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_shop, container, false)
        val recyclerView: RecyclerView = v.findViewById(R.id.low_recycler_view)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerViewAdapter = CustomRecyclerViewAdapter(requireContext())
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = linearLayoutManager
        viewModel.readAllData.observe(viewLifecycleOwner, Observer { item->
            var list = ArrayList<Item>()
            for(i in item){
                if(i.needed > i.amount){
                    list.add(i)
                }
            }
            recyclerViewAdapter.setData(list)
        })

        return v
    }




}
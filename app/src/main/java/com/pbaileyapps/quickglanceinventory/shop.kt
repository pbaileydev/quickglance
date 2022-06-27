package com.pbaileyapps.quickglanceinventory

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.pbaileyapps.quickglanceinventory.data.Item
import com.pbaileyapps.quickglanceinventory.data.ItemViewModel


class shop : Fragment(), androidx.appcompat.widget.SearchView.OnQueryTextListener {
    val args:shopArgs by navArgs()
    lateinit var auth: FirebaseAuth
    lateinit var viewModel: ItemViewModel
    lateinit var destination:String
    lateinit var recyclerViewAdapter: CustomRecyclerViewAdapter
    var searchView:androidx.appcompat.widget.SearchView? = null
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
        auth = FirebaseAuth.getInstance()
        setHasOptionsMenu(true)
        if(auth.currentUser != null) {
            viewModel.getAllItemsForUser(auth.currentUser?.uid!!)
                .observe(viewLifecycleOwner, Observer { item ->
                    var list = ArrayList<Item>()
                    for (i in item) {
                        if (i.needed > i.amount) {
                            list.add(i)
                        }
                    }
                    recyclerViewAdapter.setData(list)
                })
        }
        else{
            viewModel.readAllData
                .observe(viewLifecycleOwner, Observer { item ->
                    var list = ArrayList<Item>()
                    for (i in item) {
                        if (i.needed > i.amount) {
                            list.add(i)
                        }
                    }
                    recyclerViewAdapter.setData(list)
                })
        }
        return v
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
        val search = menu.findItem(R.id.search_magnifying_glass)
        searchView = search?.actionView as? androidx.appcompat.widget.SearchView
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.search_items){
            startActivity(Intent(context,AddItem::class.java))
            return true
        }
        else if(item.itemId == R.id.logout) {
            auth.signOut()
            startActivity(Intent(context,SignInActivity::class.java))
            return true
        }
        else{
            return true
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        var dataStuff = "%$query%"
        if(query != null){
            viewModel.searchDatabase(dataStuff).observe(this, Observer { t ->
                t.let{recyclerViewAdapter.setData(it)}
            })

        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        var dataStuff ="%$newText%"
        if(newText != null){
            viewModel.searchDatabase(dataStuff).observe(this, Observer { t ->
                t.let{recyclerViewAdapter.setData(it)}
            })
            return true
        }
        return true
    }



}
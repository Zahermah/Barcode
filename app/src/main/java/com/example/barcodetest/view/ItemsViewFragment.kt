package com.example.barcodetest.view

import android.content.ClipData
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.barcodetest.R
import com.example.barcodetest.model.Items
import com.example.barcodetest.model.ItemsList
import com.example.barcodetest.network.getItemApi
import com.example.barcodetest.network.RetrofitInstance
import com.example.barcodetest.testForFirebase.FirebaseEanCode
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.*


class ItemsViewFragment : Fragment() {
    private var TAG = "ItemViewFragment"
    lateinit var listOfArray: ArrayList<String?>
    lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.items_list_fragment, container, false)
        fetchDataFromFirebase()
        recyclerView = rootView.findViewById(R.id.recycler_view)
        return rootView
    }

    fun fetchDataFromFirebase() {
        val databaseReference = FirebaseDatabase.getInstance().getReference("EAN")
        listOfArray = arrayListOf()

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {}
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (eanCodes in dataSnapshot.children) {
                    if (dataSnapshot.exists()) {
                        Log.i(TAG + "EAN", eanCodes.toString())
                        val eanItems = eanCodes.getValue(FirebaseEanCode::class.java)
                        listOfArray.add(eanItems?.eanValue)
                    }
                }
                getEanItems(listOfArray)
            }

        })
    }


    fun getEanItems(eanCode: ArrayList<String?>) {
        val service = RetrofitInstance().getRetrofitInstance().create(getItemApi::class.java)
        GlobalScope.launch(Dispatchers.Main) {
            repeat(eanCode.size) { count ->
                val call = service.getItemsData(eanCode[count].toString())
                delay(500)
                call.enqueue(object : Callback<ItemsList> {
                    override fun onFailure(call: Call<ItemsList>, t: Throwable) {
                        Toast.makeText(context, "Could not fetch data", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<ItemsList>, response: Response<ItemsList>) {
                        Log.i(TAG, response.body().toString())
                        Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show()
                        response.body()?.getitemsArrayList()?.let { generateItemsList(it) }
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                })
            }
        }
    }


    fun generateItemsList(getitemsArrayList: ArrayList<Items>) {
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = ItemsAdapter(getitemsArrayList)
            (adapter as ItemsAdapter).notifyDataSetChanged()
        }
    }
}









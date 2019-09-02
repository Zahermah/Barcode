package com.example.barcodetest.view

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
import com.example.barcodetest.network.GetItemApi
import com.example.barcodetest.network.RetrofitInstance
import com.example.barcodetest.testForFirebase.FirebaseEanCode
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ItemsViewFragment : Fragment() {
    private var TAG = "ItemViewFragment"
    lateinit var listofArray: ArrayList<String?>
    lateinit var listofItems: ArrayList<Items>
    lateinit var mutableList: MutableList<String?>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.items_list_fragment, container, false)
        val databaseReference = FirebaseDatabase.getInstance().getReference("EAN")
        listofArray = arrayListOf()
        listofItems = arrayListOf()
        mutableList = mutableListOf()
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (eanCodes in dataSnapshot.children) {
                    if (dataSnapshot.exists()) {
                        val eanItems = eanCodes.getValue(FirebaseEanCode::class.java)
                        mutableList.add(eanItems?.eanValue)
                        listofArray.add(eanItems?.eanValue)
                        eanItems?.eanValue?.let { showlistofitems(rootView, it) }
                    }
                }
            }
        })


        return rootView
    }

    fun showlistofitems(view: View, eanCode: String) {
        val service = RetrofitInstance().getRetrofitInstance().create(GetItemApi::class.java)
        listofArray.add(eanCode)
        listofArray.listIterator()

        // val call = eanCode?.let { service.getItemsData(it) }
            Log.i(TAG,listofArray.toString())
            // service.getItemsListData(itemsArrayList)

        val call = service.getItemsData(eanCode)
        call.enqueue(object : Callback<ItemsList> {
            override fun onFailure(call: Call<ItemsList>, t: Throwable) {
                Toast.makeText(activity, "Could not fetch data", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ItemsList>, response: Response<ItemsList>?) {
                if (response?.isSuccessful!!) {
                    response.body()?.getitemsArrayList()?.let { generateItemsList(it) }
                    Log.i(TAG, response.body().toString())
                } else {
                    return
                }

                response.code().toString()

                //  response.body()?.getitemsArrayList()?.let { generateItemsList(it) }

            }

            fun generateItemsList(getitemsArrayList: ArrayList<Items>) {
                val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
                val layoutManager = LinearLayoutManager(context)
                recyclerView?.layoutManager = layoutManager
//                recyclerView?.adapter = ItemsAdapter(getitemsArrayList!!)
                getitemsArrayList.add(Items("Ketchup", "Pommes"))
                recyclerView?.adapter?.notifyDataSetChanged()
                recyclerView?.adapter = ItemsAdapter(getitemsArrayList)
            }


        })
    }

}









package com.example.barcodetest.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.barcodetest.R
import com.example.barcodetest.Koin.UserPresenter
import com.example.barcodetest.model.Items
import com.example.barcodetest.model.ItemsList
import com.example.barcodetest.network.getItemApi
import com.example.barcodetest.network.RetrofitInstance
import com.example.barcodetest.testForFirebase.FirebaseEanCode
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import retrofit2.*


class ItemsViewFragment : Fragment() {
    private val TAG = ItemsViewFragment::class.qualifiedName
    lateinit var listOfArray: ArrayList<String?>
    lateinit var listOfItems: ArrayList<Items>
    lateinit var listOfItemsID: ArrayList<String>
    lateinit var recyclerView: RecyclerView
    lateinit var rootView: View

    val userGreeeting: UserPresenter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.items_list_fragment, container, false)
        recyclerView = rootView.findViewById(R.id.recycler_view)
        userGreeeting.messageUser(rootView)
        fetchDataFromFirebase()
        return rootView
    }

    private fun fetchDataFromFirebase() {
        val databaseReference = FirebaseDatabase.getInstance().getReference("EAN")
        listOfArray = arrayListOf()
        listOfItems = arrayListOf()
        listOfItemsID = arrayListOf()
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {}
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (eanCodes in dataSnapshot.children) {
                    if (dataSnapshot.exists()) {
                        Log.i(TAG + "EAN", eanCodes.toString())
                        val eanItems = eanCodes.getValue(FirebaseEanCode::class.java)
                        eanItems?.eanId?.let { listOfItemsID.add(it) }
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
                delay(2500)
                call.enqueue(object : Callback<ItemsList> {
                    override fun onFailure(call: Call<ItemsList>, t: Throwable) {
                        Toast.makeText(context, "Could not fetch data", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<ItemsList>, response: Response<ItemsList>) {

                        if (response.isSuccessful) {
                            if (context != null) {
                                Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT)
                                    .show()
                            }
                            response.body()?.getitemsArrayList()?.let { generateItemsList(it) }

                        } else {
                            if (response.code() == 429) {
                                if (context != null) {
                                    Toast.makeText(context, "to many requests", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }

                        }
                        Log.i(TAG, response.body().toString())


                    }
                })
            }
        }
    }

    fun generateItemsList(getitemsArrayList: ArrayList<Items>) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
            itemTouchHelper.attachToRecyclerView(recyclerView)
            adapter = ItemsAdapter(addItems(getitemsArrayList))
        }
    }

    fun addItems(items: ArrayList<Items>): ArrayList<Items> {
        listOfItems.addAll(items)
        recyclerView.adapter?.notifyDataSetChanged()
        return listOfItems
    }

    val itemTouchCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                viewHolder1: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                val databaseReference = FirebaseDatabase.getInstance().getReference("/EAN")
                databaseReference.child(listOfItemsID[position]).removeValue()
                Log.i(TAG + "GODA", listOfItemsID[position])
                listOfItems.removeAt(position)
                recyclerView.adapter?.notifyDataSetChanged()
                Snackbar.make(rootView, "Removed", Snackbar.LENGTH_SHORT).show()
            }
        }
}
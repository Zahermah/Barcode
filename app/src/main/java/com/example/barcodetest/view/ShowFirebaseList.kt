package com.example.barcodetest.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import com.example.barcodetest.R
import com.example.barcodetest.testForFirebase.FirebaseEanCode
import com.example.barcodetest.testForFirebase.firebaseEanAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ShowFirebaseList : Fragment() {

    lateinit var mutableList: MutableList<FirebaseEanCode>
    lateinit var listView: ListView
    private val TAG = ShowFirebaseList::class.qualifiedName


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootview = inflater.inflate(R.layout.testforfirebase, container, false)
        listView = rootview.findViewById(R.id.FirebaseListView)
        mutableList = mutableListOf()
        return rootview
    }

    override fun onResume() {
        super.onResume()
        readFromFirebase()
    }

    fun showProgressBar() {


    }


    fun readFromFirebase() {
        val databaseReference = FirebaseDatabase.getInstance().getReference("EAN")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(activity, "Could not fetch from Firebasre", Toast.LENGTH_SHORT)
                    .show()
                Log.i(TAG, "Cant connect to Firebase")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (eanCodes in dataSnapshot.children) {
                    if (dataSnapshot.exists()) {
                        val eanItems = eanCodes.getValue(FirebaseEanCode::class.java)
                        mutableList.add(eanItems!!)
                    }
                    val adapter = context?.let {
                        firebaseEanAdapter(
                            it,
                            R.layout.ean_code_items,
                            mutableList
                        )
                    }
                    listView.adapter = adapter
                }
            }
        })
    }
}
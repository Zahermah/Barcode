package com.example.barcodetest.model

import com.example.barcodetest.testForFirebase.FirebaseEanCode
import com.google.firebase.database.*

class EanModel {
    lateinit var databaseReference: DatabaseReference

    fun getEanCodeFromDatabase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("EAN")
        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(databaseError: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val mutableList: MutableList<EanNumbers>
                for (dataSnapShot in dataSnapshot.children) {
                    val eanItems = dataSnapShot.getValue(FirebaseEanCode::class.java)
                }
            }
        })
    }

}
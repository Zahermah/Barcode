package com.example.barcodetest.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.barcodetest.R
import com.example.barcodetest.testForFirebase.FirebaseEanCode
import com.example.barcodetest.testForFirebase.firebaseEanAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.CirclePromptBackground
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.FullscreenPromptBackground
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.RectanglePromptBackground
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.CirclePromptFocal
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal

class FirebaseListFragment : Fragment() {

    lateinit var mutableList: MutableList<FirebaseEanCode>
    lateinit var listView: ListView
    private val TAG = FirebaseListFragment::class.qualifiedName
    private lateinit var rootView: View
    lateinit var floatingActionButton: FloatingActionButton


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.testforfirebase, container, false)
        listView = rootView.findViewById(R.id.FirebaseListView)
        floatingActionButton = rootView.findViewById(R.id.float_action_button)
        mutableList = mutableListOf()
        return rootView
    }

    override fun onResume() {
        super.onResume()
        readFromFirebase()
        promptLogIn()
    }

    /*

    fun Login() {
        floatingActionButton.setOnClickListener({
            startActivity(Intent(activity, LoginActivity::class.java))

        })
    }
*/
    private fun promptLogIn() {
        MaterialTapTargetPrompt.Builder(this@FirebaseListFragment)
            .setTarget(rootView.findViewById<View>(R.id.float_action_button))
            .setPrimaryText("Hey Hey")
            .setSecondaryText("Press here to Log in")
            .setBackButtonDismissEnabled(true)
            .setPromptBackground(FullscreenPromptBackground())
            .setPromptFocal(RectanglePromptFocal())
            .setPromptStateChangeListener { prompt, state ->
                if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
                    MaterialTapTargetPrompt.STATE_FINISHED
                }
            }
            .show()
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
package com.example.barcodetest.view.Utility

import android.util.Log
import androidx.fragment.app.Fragment
import com.example.barcodetest.view.FirebaseListFragment

class SplitList : Fragment() {

    private val TAG = FirebaseListFragment::class.qualifiedName
    var counter = 0
    lateinit var eanNumbers: ArrayList<String?>
    fun listSplit(listOfEanItems: ArrayList<String?>) {
        val newList = listOfEanItems.chunked(6)
        eanNumbers = arrayListOf()

        for (eanNumbers in newList) {
            counter++
            Log.i(TAG + "midas", eanNumbers[1].toString())
            // ItemsViewFragment().getEanItems(eanNumbers)
        }
        //ItemsViewFragment().getChunkedEanList(newList)
    }

}
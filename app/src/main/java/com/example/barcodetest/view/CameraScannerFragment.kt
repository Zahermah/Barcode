package com.example.barcodetest.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.barcodetest.testForFirebase.FirebaseEanCode
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView




class CameraScannerFragment : Fragment(), ZXingScannerView.ResultHandler {

    private var zxingScannerView: ZXingScannerView? = null
    private val TAG = CameraScannerFragment::class.qualifiedName


    companion object {

        fun newInstance(){

        }
    }


    var firebaseAuth = FirebaseAuth.getInstance()

    private fun addToFirebaseDatabase(eanCode: String?) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("/EAN")
        val eanId = databaseReference.push().key
        val ean = eanId?.let { eanCode?.let { it1 -> FirebaseEanCode(it, it1) } }
        eanId?.let { databaseReference.child(it).setValue(ean) }
    }

    override fun handleResult(rawResult: Result?) {
        addToFirebaseDatabase(rawResult?.text)
        zxingScannerView?.startCamera()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        zxingScannerView = ZXingScannerView(context)
        zxingScannerView!!.setAspectTolerance(0.5f)
        zxingScannerView?.setResultHandler(this)
        return zxingScannerView
    }

    override fun onPause() {
        super.onPause()
        zxingScannerView?.stopCamera()           // Stop camera on pause
    }

    override fun onResume() {
        super.onResume()
        zxingScannerView?.setResultHandler(this)
        zxingScannerView?.startCamera()
    }
}

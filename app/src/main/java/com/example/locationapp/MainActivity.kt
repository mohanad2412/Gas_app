package com.example.locationapp

import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import mumayank.com.airlocationlibrary.AirLocation

class MainActivity : AppCompatActivity(), AirLocation.Callback {
    lateinit var firstView: TextView
    lateinit var secondView: TextView
    lateinit var thirdView: TextView
    lateinit var fourthView: TextView
    lateinit var locButton: Button
    lateinit var firstLoc : EditText
    lateinit var secondLoc : EditText
    lateinit var thirdLoc : EditText
    lateinit var fourthLoc : EditText
    lateinit var location:AirLocation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        firstView=findViewById(R.id.firstView)
        secondView=findViewById(R.id.secondView)
        thirdView=findViewById(R.id.thirdView)
        fourthView=findViewById(R.id.fourthView)
        locButton=findViewById(R.id.locButton)
        firstLoc=findViewById(R.id.firstLoc)
        secondLoc=findViewById(R.id.secondLoc)
        thirdLoc=findViewById(R.id.thirdLoc)
        fourthLoc=findViewById(R.id.fourthLoc)

        firstLoc.addTextChangedListener {
            if (firstLoc.text.isNotEmpty()) {
                locButton.isEnabled=true
            }
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        location.onActivityResult(requestCode, resultCode, data) // ADD THIS LINE INSIDE onActivityResult
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        location.onRequestPermissionsResult(requestCode, permissions, grantResults) // ADD THIS LINE INSIDE onRequestPermissionResult
    }

    fun location(view: View) {
        val location=AirLocation(this,this,true,0,"")
        location.start()
    }

    override fun onFailure(locationFailedEnum: AirLocation.LocationFailedEnum) {
        Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(locations: ArrayList<Location>) {
        firstView.setText("")
        secondView.setText("")
        thirdView.setText("")
        fourthView.setText("")
        
        val coder = Geocoder(this)
        val address1 = "${firstLoc.text} egypt"
        val result = coder.getFromLocationName(address1, 1)
        val loc1 = Location("")
        loc1.latitude = result?.get(0)?.latitude ?: 0.0
        loc1.longitude = result?.get(0)?.longitude ?: 0.0

        val address2 = "${secondLoc.text} egypt"
        val result2 = coder.getFromLocationName(address2, 1)
        val loc2 = Location("")
        loc2.latitude = result2?.get(0)?.latitude ?: 0.0
        loc2.longitude = result2?.get(0)?.longitude ?: 0.0
        val distance = loc1.distanceTo(loc2)
        if (firstLoc.text.isNotEmpty() && secondLoc.text.isNotEmpty()) {
            firstView.setText("From The First Destination to the Second Destination the whole Distance is ${distance / 1000} kilometers and the price ${distance / 1000 * 0.75} egp\n")
        }
        val address3 = "${thirdLoc.text} egypt"
        val result3 = coder.getFromLocationName(address3, 1)
        val loc3 = Location("")
        loc3.latitude = result3?.get(0)?.latitude ?: 0.0
        loc3.longitude = result3?.get(0)?.longitude ?: 0.0
        val distance2 = loc2.distanceTo(loc3)
        val distance4 = loc1.distanceTo(loc3)
        if (secondLoc.text.isNotEmpty() && thirdLoc.text.isNotEmpty()) {
            secondView.setText("From The Second Destination to the Third Destination the whole Distance is ${distance2 / 1000} kilometers and the price ${distance2 / 1000 * 0.75} egp\n")
        }
        val address4 = "${fourthLoc.text} egypt"
        val result4 = coder.getFromLocationName(address4, 1)
        val loc4 = Location("")
        loc4.latitude = result4?.get(0)?.latitude ?: 0.0
        loc4.longitude = result4?.get(0)?.longitude ?: 0.0
        val distance3 = loc3.distanceTo(loc4)
        val distance5=loc1.distanceTo(loc4)
        if (thirdLoc.text.isNotEmpty() && fourthLoc.text.isNotEmpty()) {
            thirdView.setText("From The Third Destination to the Fourth Destination the whole Distance is ${distance3 / 1000} kilometers and the price ${distance3 / 1000 * 0.75} egp\n")
        }
        if (secondLoc.text.isEmpty() && thirdLoc.text.isNotEmpty() && fourthLoc.text.isEmpty()) {
            thirdView.setText("From The First Destination to the Third Destination the whole Distance is ${distance4 / 1000} kilometers and the price is ${distance4 / 1000 * 0.75} egp\n")
        } else if (secondLoc.text.isEmpty() && thirdLoc.text.isEmpty() && fourthLoc.text.isNotEmpty()) {
            fourthView.setText("From The First Destination to the Fourth Destination the whole Distance is ${distance5 / 1000} kilometers and the price is ${distance5 / 1000 * 0.75} egp\n")
        }
        if (firstLoc.text==null) {
            firstView.setText("Please Enter Your First Destination")
        }
        if (secondLoc.text.isEmpty() || secondLoc.text==null) {
            secondView.setText("Please Enter Your Second Destination\n")
        }
        if (thirdLoc.text.isEmpty() || thirdLoc.text==null) {
            thirdView.setText("Please Enter Your Third Destination\n")
        }
        if (fourthLoc.text.isEmpty() || fourthLoc.text==null) {
            fourthView.setText("Please Enter Your Fourth Destination\n")
        }
    }

}
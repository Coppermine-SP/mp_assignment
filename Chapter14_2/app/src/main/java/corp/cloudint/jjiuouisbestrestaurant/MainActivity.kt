/*
    MainActivity - MapsExample
    Copyright (C) 2024-2025 Coppermine-SP - <국립창원대학교 컴퓨터공학과 20233063 손유찬>
 */
package corp.cloudint.jjiuouisbestrestaurant

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import corp.cloudint.jjiuouisbestrestaurant.Contexts.LocationDbContext
import corp.cloudint.jjiuouisbestrestaurant.Contexts.RoomLocationDAO
import corp.cloudint.jjiuouisbestrestaurant.Models.Location

class MainActivity : AppCompatActivity() {
    private val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    private lateinit var mapView: MapView;
    private lateinit var googleMap: GoogleMap;
    private lateinit var locationProvider: FusedLocationProviderClient;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mapView = findViewById(R.id.mapsView);
        locationProvider = LocationServices.getFusedLocationProviderClient(this);
        val mapViewBundle = savedInstanceState?.getBundle(MAPVIEW_BUNDLE_KEY)
        mapView.onCreate(mapViewBundle)

        mapView.getMapAsync({m ->
            googleMap = m
            checkPermission()
        } )
    }

    override fun onResume(){
        super.onResume()
        mapView.onResume()
    }

    override fun onPause(){
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY)
        outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle ?: Bundle())
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == LOCATION_PERMISSION_REQUEST_CODE &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) showLocations()
    }

    private fun checkPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        else showLocations()
    }

    @SuppressLint("DefaultLocale", "SuspiciousIndentation")
    private fun showLocations(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            googleMap.isMyLocationEnabled = true
            locationProvider.lastLocation.addOnSuccessListener({ location ->
                if (location != null) {
                    val curLoc = LatLng(location.latitude, location.longitude)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLoc, 15F))
                }
            })
        }
    }

    private fun setDemoData(dao: RoomLocationDAO){
        dao.insert(Location())
    }

    private fun configureDatabase(){
        val helper = Room.databaseBuilder(this, LocationDbContext::class.java, "location_db")
            .allowMainThreadQueries() // not recommend.
            .build()

        val dao = helper.RoomLocationDAO();

        if(dao.selectAll().size == 0)
    }
}

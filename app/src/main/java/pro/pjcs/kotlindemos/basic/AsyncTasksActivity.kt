package pro.pjcs.kotlindemos.basic

import android.Manifest
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.LinearInterpolator
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_async_tasks.*
import kotlinx.coroutines.*
import pro.pjcs.kotlindemos.R
import pro.pjcs.kotlindemos.base.BaseActivity
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AsyncTasksActivity : BaseActivity() {

    private lateinit var locationClient: FusedLocationProviderClient
    private var useCorrectWay = false;

    private val job = Job()
    private val scope = CoroutineScope(job + Dispatchers.Default)

    private val handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_async_tasks)

        locationClient = LocationServices.getFusedLocationProviderClient(this)

        blocking_switch.setOnCheckedChangeListener { _, isOn -> useCorrectWay = isOn }
        startAnimation()

        if (locationPermission() != PackageManager.PERMISSION_GRANTED) {
            requestPermissions()
        }
    }

    override fun onResume() {
        super.onResume()

        handleHardWork()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun startAnimation(){

        val rotationAnimator = ValueAnimator.ofFloat(0f, 360f)
        rotationAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            pizza_image.rotation = value
        }

        rotationAnimator.interpolator = LinearInterpolator()
        rotationAnimator.duration = 2000
        rotationAnimator.repeatCount = ValueAnimator.INFINITE
        rotationAnimator.start()

    }

    private fun handleHardWork(){


        if( useCorrectWay ){

            scope.launch {
                handleHardWorkCorrectly()
            }

            printLocation()
        }else {
            performHardWork()
        }

        handler.postDelayed({ handleHardWork() }, 1000)

    }

    private suspend fun handleHardWorkCorrectly() {

        val work1Deferred = scope.async {
            val result = performHardWork()
            Log.v(TAG, "[A][1] Work 1 is $result")
            result
        }

        val work2Deferred = scope.async {
            val result = performHardWork()
            Log.v(TAG, "[A][1] Work 2 is $result")
            result
        }


        val work1 = work1Deferred.await()
        val work2 = work2Deferred.await()


        Log.v(TAG, "[A][2] Work 1 is $work1")
        Log.v(TAG, "[A][2] Work 2 is $work2")

    }
    private fun performHardWork(): Int {
        Thread.sleep(500)
        return 1
    }

    //-------

    private fun printLocation(){
        return //still blocking
        if( locationPermission() != PackageManager.PERMISSION_GRANTED ){
            return;
        }
        scope.launch {
            val location = getLastKnownLocation()
            Log.v(TAG, "Location is ${location.latitude},${location.longitude}")
        }
    }

    private suspend fun getLastKnownLocation(): Location {
        return suspendCancellableCoroutine { continuation ->
            val task = locationClient.lastLocation
            task.addOnCompleteListener {
                if(task.isSuccessful){
                    continuation.resume(task.result)
                }else{
                    task.exception?.let { exception ->  continuation.resumeWithException(exception) }
                }
            }


        }
    }

    private fun locationPermission() = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_PERMISSIONS_REQUEST_CODE)
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (shouldProvideRationale) {
            //TODO
        } else {
            startLocationPermissionRequest()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.isEmpty()) {
                Log.i(TAG, "User interaction was cancelled.")
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                // Permission denied.
            }
        }
    }

    companion object {
        private const val REQUEST_PERMISSIONS_REQUEST_CODE = 1212
    }

}

fun Context.asyncTasksActivity() : Intent {
    return Intent(this, AsyncTasksActivity::class.java).apply {

    };
}
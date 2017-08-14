package com.smilehacker.griffin.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.smilehacker.griffin.Route
import com.smilehacker.griffin.Router

class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val route = Route()
        route.url = "/test/(?<id>\\d+)"

        val routeChild = Route()
        routeChild.url = "/profile"

        route.child = arrayListOf(routeChild)

        //route.url = "test"
        Log.i(TAG, "route url = " + route.url)

        Router.register(route)
        findViewById<TextView>(R.id.btn).setOnClickListener {
            Router.match("/test/123/profile?user=111")
        }
    }
}

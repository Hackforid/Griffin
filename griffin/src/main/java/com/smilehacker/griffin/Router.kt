package com.smilehacker.griffin

import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by zhouquan on 17/8/13.
 */
object Router {
    const val TAG = "Router"

    const val KEY_URL = "key_url"
    const val KEY_PATHS = "key_paths"

    private val mDefaultRouteTable by lazy { Route() }

    private val mRouteTables : MutableList<Route> = LinkedList<Route>()

    init {
        //mRouteTables.add(mDefaultRouteTable)
    }

    fun register(routeTable: Route) {
        mRouteTables.add(routeTable)
    }


    fun match(url : String, routes : MutableList<Route> = mRouteTables, existParams: MutableMap<String, String> = HashMap<String, String>()) : Pair<Route, MutableMap<String, String>?>? {
        Log.d(TAG, "match $url")
        routes
            .filter { it.pattern != null }
            .forEach table@ {
                val matcher = it.pattern!!.matcher(url)
                if (matcher.find()) {
                    val groupCount = matcher.groupCount()
                    Log.d(TAG, "group count = " + matcher.groupCount())
                    Log.d(TAG, "match string = " + matcher.group(0))

                    var _url = ""
                    var matchChild = false
                    if (it.child != null && it.child!!.isNotEmpty()) {
                        matchChild = true
                        _url = url.replace(matcher.group(0), "")
                    }

                    if (groupCount > 0) {
                        val groups = matcher.namedGroups()
                        Log.d(TAG, "named groups = " + groups)
                        if (groups.size > 0) {
                            groups[0].putAll(existParams)
                            existParams.putAll(groups[0])
                        }
                    }

                    if (matchChild) {
                        return match(_url, it.child!!, existParams)
                    } else {
                        return Pair(it, existParams)
                    }
                }
            }

        return null
    }

    fun open(ctx: Context, url: String) : Boolean {
        val routeResult = match(url) ?: return false

        val route = routeResult.first

        if (route.component != null) {
            val intent = Intent(ctx, route.component)
            intent.putExtra(KEY_URL, url)
            routeResult.second?.forEach {
                intent.putExtra(it.key, it.value)
            }
            splitQuery(url).forEach {
                intent.putExtra(it.key, it.value)
            }
            ctx.startActivity(intent)

            return true
        }

        if (route.action != null) {
            route.action?.run()
            return true
        }

        if (route.redirectURL != null) {
            return open(ctx, route.redirectURL!!)
        }

        return false
    }

}
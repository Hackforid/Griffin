package com.smilehacker.griffin

import java.util.*

/**
 * Created by zhouquan on 17/8/13.
 */
class RouteTable {

    private val routes : MutableList<Route> = LinkedList()

    fun register(route : Route) {
        routes.add(route)
    }
}
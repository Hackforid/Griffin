package com.smilehacker.griffin

import android.app.Activity
import com.google.code.regexp.Pattern

/**
 * Created by zhouquan on 17/8/13.
 */
class Route {

    var url: String? = null
        set(value) {
            field = value
            url?.let { pattern = compileRegex(url!!) }
        }

    var component : Class<out Activity>? = null
    var redirectURL : String? = null
    var action : Runnable? = null

    var child : MutableList<Route>? = null

    internal var pattern : Pattern? = null
        private set
}
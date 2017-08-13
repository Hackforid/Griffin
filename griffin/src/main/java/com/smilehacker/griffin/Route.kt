package com.smilehacker.griffin

import android.app.Activity

/**
 * Created by zhouquan on 17/8/13.
 */
class Route {
    var url: String = ""
    var component : Class<out Activity>? = null
    var redirectURL : String? = null
    var action : Runnable? = null
}
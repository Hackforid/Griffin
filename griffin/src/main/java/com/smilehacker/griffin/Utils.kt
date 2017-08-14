package com.smilehacker.griffin

import android.net.Uri
import android.util.Log
import com.google.code.regexp.Pattern
import java.net.URLDecoder
import java.util.*

/**
 * Created by quan.zhou on 2017/8/14.
 */

fun splitQuery(url: String): Map<String, String> {
    val query_pairs = LinkedHashMap<String, String>()
    try {
        val uri = Uri.parse(url)
        val query = uri.query
        if (query != null) {
            val pairs = query.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (pair in pairs) {
                val idx = pair.indexOf("=")
                query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"))
            }
        }
    } catch (e : Exception) {
        Log.e("Utils", "", e)
    }
    return query_pairs
}

fun compileRegex(url: String) : Pattern? {
    var pattern : Pattern? = null
    try {
        pattern = Pattern.compile(url)
    } catch (e : Exception) {
        Log.e("route", "", e)
    }

    return pattern
}

package com.cococlip.android.client

import com.cococlip.android.model.Clip
import java.net.URL
import org.json.JSONObject
import org.json.JSONArray
import org.funktionale.either.Either
import org.funktionale.either.either
import com.cococlip.android.model.Location

/**
 * @author Taro Nagasawa
 */
public object ApiClient {

    private fun String.readText(): String = URL("https://cococlip.herokuapp.com" + this).readText("UTF-8")

    private fun <T> JSONArray.map(f: (JSONObject) -> T): List<T> {
        val builder = ImmutableArrayListBuilder<T>()
        for (i in 0..(length() - 1)) {
            val elem = getJSONObject(i)
            builder.add(f(elem))
        }
        return builder.build()
    }

    public fun getClips(latitude: Double, longitude: Double): Either<Exception, List<Clip>> {
        return either {
            val response = "/api/1/clips?lat=${latitude}&lon=${longitude}".readText()

            val jsonObject = JSONObject(response)
            jsonObject.getJSONArray("results").map {
                val loc = it.getJSONObject("loc")

                Clip(
                        id = it.getString("_id"),
                        title = it.getString("title"),
                        location = Location(loc.getDouble("lat"), loc.getDouble("lon")),
                        image1Url = it.findString("high_image1_url"),
                        image2Url = it.findString("high_image2_url"),
                        thumbnail1Url = it.findString("low_image1_url"),
                        thumbnail2Utl = it.findString("low_image2_url")
                )
            }
        }
    }

    private fun JSONObject.findString(key: String): String? = if (has(key)) getString(key) else null
}
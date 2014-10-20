package com.cococlip.android.client

import com.cococlip.android.model.Clip
import java.net.URL
import org.json.JSONObject
import org.json.JSONArray

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

    public fun getClips(latitude: Double, longitude: Double): List<Clip> {
        val response = "/api/1/clips?lat=${latitude}&lon=${longitude}".readText()

        val jsonObject = JSONObject(response)
        if (!jsonObject.has("results"))
            return listOf()

        return jsonObject.getJSONArray("results").map {
            Clip(
                    id = it.getString("id"),
                    title = it.getString("title"),
                    latitude = it.getDouble("lat"),
                    longitude = it.getDouble("lon"),
                    image1Url = it.getString("high_image1_url"),
                    image2Url = it.getString("high_image2_url"),
                    thumbnail1Url = it.getString("low_image1_url"),
                    thumbnail2Utl = it.getString("low_image2_url")
            )
        }
    }
}
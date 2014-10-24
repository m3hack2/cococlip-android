package com.cococlip.android.client

import com.cococlip.android.model.Clip
import org.json.JSONObject
import org.json.JSONArray
import org.funktionale.either.Either
import org.funktionale.either.either
import com.cococlip.android.model.Location
import com.squareup.okhttp.Request
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Response
import com.squareup.okhttp.FormEncodingBuilder

/**
 * @author Taro Nagasawa
 */
public object ApiClient {

    private val client = OkHttpClient()

    private fun Request.Builder.path(path: String): Request.Builder = url("https://cococlip.herokuapp.com" + path)

    private fun Request.Builder.executeBy(client: OkHttpClient): Response = client.newCall(build()).execute()

    private fun Request.Builder.post(initFormEncodingBuilder: FormEncodingBuilder.() -> Unit): Request.Builder {
        val builder = FormEncodingBuilder()
        builder.initFormEncodingBuilder()
        return post(builder.build())
    }

    private fun <T> FormEncodingBuilder.add(key: String, value: T) {
        add(key, value.toString())
    }

    private fun String.toJsonObject(): JSONObject = JSONObject(this)

    private fun JSONObject.findString(key: String): String? = if (has(key)) getString(key) else null

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
            Request.Builder()
                    .path("/api/1/clips?lat=${latitude}&lon=${longitude}")
                    .get()
                    .executeBy(client)
                    .body()
                    .string()
                    .toJsonObject()
                    .getJSONArray("results")
                    .map {
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

    public fun getClip(id: String): Either<Exception, Clip> {
        return either {
            Request.Builder()
                    .path("/api/1/clips/$id")
                    .get()
                    .executeBy(client)
                    .body()
                    .string()
                    .toJsonObject()
                    .let {
                        val loc = it.getJSONObject("loc")

                        Clip(
                                id = it.getString("_id"),
                                title = it.getString("title"),
                                location = Location(loc.getDouble("lat"), loc.getDouble("lon")),
                                body = it.getString("body"),
                                image1Url = it.findString("high_image1_url"),
                                image2Url = it.findString("high_image2_url"),
                                thumbnail1Url = it.findString("low_image1_url"),
                                thumbnail2Utl = it.findString("low_image2_url")
                        )
                    }
        }
    }

    public fun postClip(title: String, body: String, location: Location): Either<Exception, String> {
        return either {
            Request.Builder()
                    .path("/api/1/clips")
                    .post {
                        add("title", title)
                        add("body", body)
                        add("lat", location.latitude)
                        add("lon", location.longitude)
                    }.executeBy(client)
                    .body()
                    .string()
                    .toJsonObject()
                    .getString("_id")
        }
    }
}
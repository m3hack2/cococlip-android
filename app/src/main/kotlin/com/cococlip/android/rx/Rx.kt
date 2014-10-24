package com.cococlip.android.rx

import rx.Observable
import com.cococlip.android.model.Clip
import com.cococlip.android.client.ApiClient
import rx.Subscriber
import com.cococlip.android.model.Location

/**
 * @author Taro Nagasawa
 */
public object Rx {

    public fun clips(location: Location): Observable<List<Clip>> {
        return Observable.create(object : Observable.OnSubscribe<List<Clip>> {
            override fun call(subscriber: Subscriber<in List<Clip>>) {
                val (exception, clips) = ApiClient.getClips(location.latitude, location.longitude)
                if (clips != null) {
                    subscriber.onNext(clips)
                    subscriber.onCompleted()
                } else {
                    subscriber.onError(exception)
                }
            }
        })
    }

    public fun clip(id: String): Observable<Clip> {
        return Observable.create(object : Observable.OnSubscribe<Clip> {
            override fun call(subscriber: Subscriber<in Clip>) {
                val (exception, clip) = ApiClient.getClip(id)
                if (clip != null) {
                    subscriber.onNext(clip)
                    subscriber.onCompleted()
                } else {
                    subscriber.onError(exception)
                }
            }

        })
    }

    public fun post(title: String, body: String, location: Location): Observable<String> {
        return Observable.create(object : Observable.OnSubscribe<String> {
            override fun call(subscriber: Subscriber<in String>) {
                val (exception, id) = ApiClient.postClip(title, body, location)
                if (id != null) {
                    subscriber.onNext(id)
                    subscriber.onCompleted()
                } else {
                    subscriber.onError(exception)
                }
            }
        })
    }
}
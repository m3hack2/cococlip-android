package com.cococlip.android.rx

import rx.Observable
import com.cococlip.android.model.Clip
import com.cococlip.android.client.ApiClient
import rx.Subscriber

/**
 * @author Taro Nagasawa
 */
public object Rx {

    public fun clips(latitude: Double, longitude: Double): Observable<List<Clip>> {
        return Observable.create(object : Observable.OnSubscribe<List<Clip>> {
            override fun call(subscriber: Subscriber<in List<Clip>>) {
                val clips = ApiClient.getClips(latitude, longitude)
                subscriber.onNext(clips)
                subscriber.onCompleted()
            }
        })
    }
}
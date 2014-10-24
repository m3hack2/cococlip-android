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
}
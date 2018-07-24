package com.app.jetpack.repository

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository used to tests some Rx streams.
 */
@Singleton
class RxExampleRepository @Inject constructor() {

    class ZipObject(val int: Int, val string: String)

    // One callback for few observables
    fun zip(): Observable<ZipObject> {
        return Observable.zip(Observable.just(1), Observable.just("some text"),
                BiFunction { result1: Int, result2: String ->
                    ZipObject(result1, result2)
                })
    }

    // Rx wrapper around standards callbacks
    fun creationOfObservable(): Observable<List<Int>> {
        return Observable.create<List<Int>> {
            val list = arrayListOf(1, 4, 5, 3, 9, 10, 2, 8, 7, 5)
            list.sort()
            if (list[0] > list[1]) {
                it.onError(Throwable("This list is not ordered"))
            } else {
                it.onNext(list)
                it.onComplete()
            }
        }
    }

    // Do something every X second, to stop it just clear the disposable
    fun job(): Observable<Boolean> {
        return Observable.interval(1, TimeUnit.SECONDS)
                .flatMap { Observable.just(true) }
    }

    //TODO skip(10)/take(10) for pagination example.
}


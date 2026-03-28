package com.treebo.internetavailabilitychecker

import android.content.Context
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.CopyOnWriteArraySet

/** Top-level interface to match your existing imports. */
interface InternetConnectivityListener {
    fun onInternetConnectivityChanged(isConnected: Boolean)
}

/**
 * Compatibility shim for the original Treebo library.
 * Matches the API your code uses: top-level listener type, add/remove methods,
 * removeAll..., init/getInstance, and an **instance** field
 * currentInternetAvailabilityStatus.
 */
class InternetAvailabilityChecker private constructor(private val appContext: Context) {

    /** Instance field (your code accesses it via instance.currentInternetAvailabilityStatus). */
    var currentInternetAvailabilityStatus: Boolean = true

    private val listeners = CopyOnWriteArraySet<InternetConnectivityListener>()
    private val disposables = CompositeDisposable()

    init {
        val d = ReactiveNetwork.observeNetworkConnectivity(appContext)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ connectivity: Connectivity ->
                val connected = connectivity.available()
                currentInternetAvailabilityStatus = connected
                listeners.forEach { it.onInternetConnectivityChanged(connected) }
            }, {
                // ignore errors; keep last known state
            })
        disposables.add(d)
    }

    fun addInternetConnectivityListener(listener: InternetConnectivityListener) {
        listeners.add(listener)
        // Emit last known immediately
        listener.onInternetConnectivityChanged(currentInternetAvailabilityStatus)
    }

    fun removeInternetConnectivityChangeListener(listener: InternetConnectivityListener) {
        listeners.remove(listener)
    }

    fun removeAllInternetConnectivityChangeListeners() {
        listeners.clear()
    }

    fun isInternetOn(): Boolean = currentInternetAvailabilityStatus

    companion object {
        @Volatile private var instance: InternetAvailabilityChecker? = null

        @JvmStatic
        fun init(context: Context): InternetAvailabilityChecker {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = InternetAvailabilityChecker(context.applicationContext)
                    }
                }
            }
            return instance!!
        }

        @JvmStatic
        fun getInstance(): InternetAvailabilityChecker {
            return instance ?: throw IllegalStateException(
                "Call InternetAvailabilityChecker.init(context) first"
            )
        }
    }
}
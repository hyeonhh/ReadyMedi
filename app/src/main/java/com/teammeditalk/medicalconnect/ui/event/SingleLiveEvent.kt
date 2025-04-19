package com.teammeditalk.medicalconnect.ui.event

import androidx.annotation.MainThread
import androidx.annotation.Nullable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {
    companion object {
        private const val TAG = "SingleLiveEvent"
    }

    val mPending: AtomicBoolean = AtomicBoolean(false)

    override fun observe(
        owner: LifecycleOwner,
        observer: Observer<in T>,
    ) {
        if (hasActiveObservers()) {
            Timber.w("multiple observers registered but only one will be notified of changes")
        }
        super.observe(
            owner,
            Observer { t ->
                if (mPending.compareAndSet(true, false)) {
                    observer.onChanged(t)
                }
            },
        )
    }

    @MainThread
    override fun setValue(
        @Nullable value: T,
    ) {
        mPending.set(true)
        super.setValue(value)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        setValue(null as T)
    }
}

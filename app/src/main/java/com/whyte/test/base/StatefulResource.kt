package com.whyte.test.base

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import com.whyte.test.data.repo.base.Resource

class StatefulResource<T> {
    enum class State {
        LOADING,
        SUCCESS, //doesn't guarantee hasData!
        ERROR,
        ERROR_API,
        ERROR_NETWORK,
        EMPTY,
        ERROR_IN_FIRST_PAGE_LOAD,
        ERROR_IN_DB_LOAD
    }

    var state = State.SUCCESS

    fun setState(state: State): StatefulResource<T> {
        this.state = state
        return this
    }

    fun isSuccessful() = state == State.SUCCESS

    fun isLoading() = state == State.LOADING

    var resource: Resource<T>? = null

    fun getData(): T? = resource?.data

    fun hasData(): Boolean = resource?.hasData() ?: false

    @StringRes
    var message: Int? = null

    fun setMessage(@StringRes message: Int): StatefulResource<T> {
        this.message = message
        return this
    }
    @SuppressLint("SupportAnnotationUsage")
    @StringRes
    var messagenew: String? = null

    fun setMessageNew(@SuppressLint("SupportAnnotationUsage") @StringRes messagenew: String): StatefulResource<T> {
        this.messagenew = messagenew
        return this
    }
    companion object {
        fun <S : Any?> with(state: State, resource: Resource<S>? = null): StatefulResource<S> {
            return StatefulResource<S>().apply {
                setState(state)
                this.resource = resource
            }
        }

        fun <S : Any?> success(resource: Resource<S>? = null): StatefulResource<S> {
            return StatefulResource<S>().apply {
                setState(State.SUCCESS)
                this.resource = resource
            }
        }

        fun <S : Any?> loading(): StatefulResource<S> {
            return StatefulResource<S>().apply {
                setState(State.LOADING)
            }
        }
        fun <S : Any?> empty(resource: String): StatefulResource<S> {
            return StatefulResource<S>().apply {
                setState(State.EMPTY)
                this.messagenew = resource
            }
        }
    }
}
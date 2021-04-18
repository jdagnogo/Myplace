package com.jdagnogo.myplace.model

/**
 * This class will hold the state of the data
 *  SUCCESS when we have some data. Note that the data can be empty
 *  ERROR : an pb happened and we will have the code
 *  LOADING: When we are waiting for the data
 */
data class Resource<out T>(val status: Status, val data: T?, val code: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}
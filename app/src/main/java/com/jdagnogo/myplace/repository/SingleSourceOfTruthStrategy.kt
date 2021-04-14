package com.jdagnogo.myplace.repository

import com.jdagnogo.myplace.model.Resource
import kotlinx.coroutines.flow.*

fun <T, A> resourceAsFlow(
    fetchFromLocal: () -> Flow<T>,
    networkCall: suspend () -> Resource<A>,
    saveCallResource: suspend (A) -> Unit
) = flow {
    // we push values we have in database
    emit(fetchFromLocal().map { Resource.success<T>(it) }.first())

    // then we ask the server for more refresh values
    val responseStatus = networkCall.invoke()
    if (responseStatus.status == Resource.Status.SUCCESS) {
        // if it s a success, we save the data
        saveCallResource(responseStatus.data!!)
        emitAll(fetchFromLocal().map { dbData ->
            Resource.success(dbData)
        })
    } else if (responseStatus.status == Resource.Status.ERROR) {
        emit(Resource.error<T>(responseStatus.message!!))
        emitAll(fetchFromLocal().map {
            Resource.error(responseStatus.message, it)
        })
    }
}
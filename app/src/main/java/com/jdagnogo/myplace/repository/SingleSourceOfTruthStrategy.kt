package com.jdagnogo.myplace.repository

import com.jdagnogo.myplace.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

/**
 * This method allows us to trust only the database
 * We will first push an loading state
 * then ask the data from the database and push them
 * then ask the data from the api
 * if the data from the server are valid we update the database then push the data from the database
 * if the data from the server are not valid we display the error
 *
 */
fun <T, A> resourceAsFlow(
    fetchFromLocal: () -> Flow<T>,
    networkCall: suspend () -> Resource<A>,
    saveCallResource: suspend (A) -> Unit
) = flow {
    emit(Resource.loading(null))
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
        emit(Resource.error<T>(responseStatus.code!!))
        emitAll(fetchFromLocal().map {
            Resource.error(responseStatus.code, it)
        })
    }
}
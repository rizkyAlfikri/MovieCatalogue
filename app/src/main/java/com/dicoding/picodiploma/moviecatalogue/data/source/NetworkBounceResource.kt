package com.dicoding.picodiploma.moviecatalogue.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.ApiResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.StatusResponse
import com.dicoding.picodiploma.moviecatalogue.vo.Resource
import kotlinx.coroutines.*

@Suppress("LeakingThis")
abstract class NetworkBounceResource<ResultType, RequestType> {

    private val result = MediatorLiveData<Resource<ResultType>>()
    private val exception = CoroutineExceptionHandler { _, throwable ->
        Log.e("tv", "${throwable.message}")
    }

    init {
        result.value = Resource.loading(null, null)

        val dbSource = loadFromDB()


        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    result.value = Resource.success(newData)
                }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()

        result.addSource(dbSource) { newData ->
            result.value = Resource.loading(newData, null)
        }

        apiResponse?.let { apiResponses ->
            result.addSource(apiResponses) { response ->
                result.removeSource(dbSource)
                result.removeSource(apiResponses)

                when (response.status) {
                    StatusResponse.SUCCESS -> {

                        if (needSaveToDB()) {
                            CoroutineScope(Dispatchers.IO + exception).launch {
                                response.body?.let { saveCallResult(it) }
                                withContext(Dispatchers.Main + exception) {
                                    result.addSource(loadFromDB()) { newData ->
                                        result.value = Resource.success(newData)
                                    }
                                    Log.e(
                                        NetworkBounceResource::class.java.simpleName,
                                        "save to db"
                                    )
                                }
                            }
                        } else {
                            Log.e(
                                NetworkBounceResource::class.java.simpleName,
                                "not save to db"
                            )

                            result.addSource(loadFromDB()){ newData ->
                                result.removeSource(loadFromDB())

                                val data = response.body?.let { fetchDataFromCall(it, newData) }
                                val test = MutableLiveData<ResultType>()
                                test.value = data
                                result.addSource(test) {
                                    result.value = Resource.success(it)
                                }
                            }
                        }
                    }

                    StatusResponse.EMPTY -> {
                        result.addSource(loadFromDB()) { newData ->
                            result.value = Resource.success(newData)
                        }
                        Log.e(NetworkBounceResource::class.java.simpleName, "Empty")
                    }

                    StatusResponse.ERROR -> {
                        onFetchFailed()
                        result.addSource(dbSource) { newData ->
                            result.value = Resource.error(newData, response.message)
                        }
                        Log.e(NetworkBounceResource::class.java.simpleName, "Error")
                    }
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }

    protected fun onFetchFailed() {}

    protected abstract fun loadFromDB(): LiveData<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>?

    protected abstract fun needSaveToDB(): Boolean

    protected abstract fun fetchDataFromCall(data: RequestType, dbData: ResultType?): ResultType?

    protected abstract fun saveCallResult(data: RequestType)

}
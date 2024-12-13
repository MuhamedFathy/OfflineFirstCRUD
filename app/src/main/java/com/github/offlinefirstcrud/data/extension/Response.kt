package com.github.offlinefirstcrud.data.extension

import com.github.offlinefirstcrud.domain.exception.DataRetrievingFailException
import com.github.offlinefirstcrud.domain.exception.NoDataException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

fun <T> Flow<Response<T>>.parseResponse(): Flow<T> {
    return map { response ->
        if (response.isSuccessful) {
            if (response.body() != null) response.body()!! else throw NoDataException()
        } else {
            throw DataRetrievingFailException()
        }
    }
}

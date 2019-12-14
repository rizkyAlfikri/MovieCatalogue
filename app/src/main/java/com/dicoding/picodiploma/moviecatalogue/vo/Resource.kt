package com.dicoding.picodiploma.moviecatalogue.vo

import com.dicoding.picodiploma.moviecatalogue.vo.Status.*

class Resource<T>(val status: Status, val body: T?, val message: String?) {

    companion object{
        fun <T> success(body: T?): Resource<T> {
            return Resource(SUCCESS, body, null)
        }

        fun <T> error(body: T?, message: String?): Resource<T> {
            return Resource(ERROR, body, message)
        }

        fun <T> loading(body: T?, message: String?): Resource<T> {
            return Resource(LOADING, body, message)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Resource<*>

        if (status != other.status) return false
        if (body != other.body) return false
        if (message != other.message) return false

        return true
    }

    override fun hashCode(): Int {
        var result = status.hashCode()
        result = 31 * result + (body?.hashCode() ?: 0)
        result = 31 * result + (message?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Resource(status=$status, body=$body, message=$message)"
    }


}
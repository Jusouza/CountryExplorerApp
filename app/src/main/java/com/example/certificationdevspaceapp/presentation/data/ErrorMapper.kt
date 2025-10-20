package com.example.certificationdevspaceapp.presentation.data

import retrofit2.HttpException
import java.io.*
import java.net.*

fun Throwable.toError(): ErrorKind = when (this) {
    is UnknownHostException,
    is SocketTimeoutException,
    is ConnectException,
    is EOFException,
    is IOException -> ErrorKind.NO_INTERNET
    is HttpException -> ErrorKind.HTTP
    else -> ErrorKind.UNKNOWN
}

fun Throwable.httpCodeOrNull(): Int? = (this as? HttpException)?.code()
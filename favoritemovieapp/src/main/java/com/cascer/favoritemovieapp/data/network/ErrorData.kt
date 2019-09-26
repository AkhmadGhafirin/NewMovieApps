package com.cascer.favoritemovieapp.data.network

class ErrorData(
    var message: String = "",
    var errorCode: String = "",
    var throwable: Throwable? = null
)
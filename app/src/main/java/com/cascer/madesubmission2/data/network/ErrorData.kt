package com.cascer.madesubmission2.data.network

class ErrorData(
    var message: String = "",
    var errorCode: String = "",
    var throwable: Throwable? = null
)
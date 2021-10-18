package com.compose.domain.exception

import java.io.IOException

class NoInternetException(cause: Throwable) : IOException(cause)

class ClientException(val error: String) : Exception(error)

class RemoteException : Exception()

class FieldException(val errorMessage: String) : Exception()

class ValidationFieldException(val errorList: List<FieldException>) : Exception()
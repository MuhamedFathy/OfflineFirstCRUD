package com.github.offlinefirstcrud.domain.exception

class DataRetrievingFailException : Throwable("Error getting data from server.")
class NoDataException : Throwable("No data.")
class NoLocalDataException : Throwable("No local data.")

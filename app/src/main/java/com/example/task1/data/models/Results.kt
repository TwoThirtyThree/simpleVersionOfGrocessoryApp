package com.example.task1.data.models



sealed class Results<out T> {
    data class Success<out T>(val data: T) : Results<T>()
    data class Failure( val message: String, val title: String) : Results<Nothing>()
}


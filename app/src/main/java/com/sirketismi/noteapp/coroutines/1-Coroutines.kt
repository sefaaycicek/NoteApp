package com.sirketismi.noteapp.coroutines

import android.provider.Settings.Global
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

fun main() {
    println("main_start ${Thread.currentThread().name}")

    runBlocking {

        launch(Dispatchers.Default) {
            // api request // thread -> t1
            //callback

           launch(Dispatchers.Main) {
               //showLoader
           }
        }

        val milis = measureTimeMillis {
            var name = async(start =  CoroutineStart.LAZY) { getName() }
            var surname = async(start =  CoroutineStart.LAZY) { getLastName()}

            println("${name.await()} ${surname.await()}")
        }
        println(milis)

    }

    println("main_end ${Thread.currentThread().name}")
}

suspend fun getName() : String {
    delay(1000)
    println("getName")
    return "Sefa"
}

suspend fun getLastName() : String {
    delay(2000)
    println("getLastName")
    return "Ayçiçek"
}
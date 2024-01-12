//package com.tommannson.familycooking.infrastructure.logger
//
//import android.util.Log
//
//interface Logger: Log {
//
//
//    fun v(tag: String?, msg: String?, tr: Throwable?)
//
//    fun d(tag: String?, msg: String): Int {
//        throw RuntimeException("Stub!")
//    }
//
//    fun d(tag: String?, msg: String?, tr: Throwable?): Int {
//        throw RuntimeException("Stub!")
//    }
//
//    fun i(tag: String?, msg: String): Int {
//        throw RuntimeException("Stub!")
//    }
//
//    fun i(tag: String?, msg: String?, tr: Throwable?): Int {
//        throw RuntimeException("Stub!")
//    }
//
//    fun w(tag: String?, msg: String): Int {
//        throw RuntimeException("Stub!")
//    }
//
//    fun w(tag: String?, msg: String?, tr: Throwable?): Int {
//        throw RuntimeException("Stub!")
//    }
//
//    external fun isLoggable(var0: String?, var1: Int): Boolean
//
//    fun w(tag: String?, tr: Throwable?): Int {
//        throw RuntimeException("Stub!")
//    }
//
//    fun e(tag: String?, msg: String): Int {
//        throw RuntimeException("Stub!")
//    }
//
//    fun e(tag: String?, msg: String?, tr: Throwable?): Int {
//        throw RuntimeException("Stub!")
//    }
//}
package com.cascer.madesubmission2.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {

    @TypeConverter
    fun fromIntList(intList: List<Int>): String {
        val type: Type = object : TypeToken<List<Int>>() {}.type
        return Gson().toJson(intList, type)
    }

    @TypeConverter
    fun toIntList(string: String): List<Int> {
        val type: Type = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(string, type) as List<Int>
    }

    @TypeConverter
    fun fromStringList(stringList: List<String>): String {
        val type: Type = object : TypeToken<List<String>>() {}.type
        return Gson().toJson(stringList, type)
    }

    @TypeConverter
    fun toStringList(string: String): List<String> {
        val type: Type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(string, type) as List<String>
    }
}
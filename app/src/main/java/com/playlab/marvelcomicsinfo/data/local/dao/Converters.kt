package com.playlab.marvelcomicsinfo.data.local.dao

import androidx.room.TypeConverter
import com.playlab.marvelcomicsinfo.model.Creator
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {
    @TypeConverter
    fun fromString(value: String?): List<Creator> {
        val listType: Type = object : TypeToken<List<Creator?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<Creator?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
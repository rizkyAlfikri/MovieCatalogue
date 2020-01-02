package com.dicoding.picodiploma.moviecatalogue.utils

import androidx.room.TypeConverter

class ListStringConverter {

    companion object{
        @TypeConverter
        fun fromArrayList(strings: ArrayList<String>): String {
            var string = ""

            strings.forEach {
                string += ("$it, ")
            }

            return string
        }

        @TypeConverter
        fun toArrayList(concatString: String): ArrayList<String> {
            val myStrings = ArrayList<String>()

            concatString.split(", ").forEach {
                myStrings.add(it)
            }

            return myStrings
        }
    }
}
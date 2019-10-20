package com.example.myfitness.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.persistence.AttributeConverter
import javax.persistence.Convert

@Convert
class EserciziConverter : AttributeConverter<ArrayList<ArrayList<Esercizio>>, String> {

    var gson = Gson()

    override fun convertToDatabaseColumn(attribute: ArrayList<ArrayList<Esercizio>>?): String {
        return gson.toJson(attribute).toString()
    }

    override fun convertToEntityAttribute(dbData: String?): ArrayList<ArrayList<Esercizio>> {
        if (dbData == null)
            return ArrayList()

        val listType = object : TypeToken<ArrayList<ArrayList<Esercizio>>>() {}.type

        return gson.fromJson(dbData, listType)
    }

}


package com.domain.fica.Data;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import androidx.room.TypeConverter;

public class ArrayConverter {

    @TypeConverter
    public String JSONArrayfromIntArray(int[] values) {
        try {
            JSONArray jsonArray = new JSONArray(Arrays.asList(values));
            return jsonArray.toString();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    @TypeConverter
    public int[] JSONArrayToIntArray(String values) {
        try {
            String[] integerStrings = values.split(",");
            int[] integers = new int[integerStrings.length];
            for (int i = 0; i < integers.length; i++){
                integerStrings[i] = integerStrings[i].replace("[", "");
                integerStrings[i] = integerStrings[i].replace("]", "");
                integers[i] = Integer.parseInt(integerStrings[i]);
            }
            return integers;

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

}

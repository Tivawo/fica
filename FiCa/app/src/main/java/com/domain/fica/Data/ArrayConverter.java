package com.domain.fica.Data;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Arrays;

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
            JSONArray jsonArray = new JSONArray(values);
            int[] intArray = new int[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                intArray[i] = Integer.parseInt(jsonArray.getString(i));
            }
            return intArray;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

}

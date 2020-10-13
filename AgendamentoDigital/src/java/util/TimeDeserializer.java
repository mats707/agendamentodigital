/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author matht
 */
public class TimeDeserializer implements JsonDeserializer<Time> {

    @Override
    public Time deserialize(JsonElement jsonElement, Type typeOF,
            JsonDeserializationContext context) throws JsonParseException {
        try {

            String s = jsonElement.getAsString();
            SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
            sdf.parse(s);
            long ms = sdf.parse(s).getTime();
            Time t = new Time(ms);
            return t;
        } catch (ParseException e) {
        }
        throw new JsonParseException("Unparseable time: \"" + jsonElement.getAsString()
                + "\". Supported formats: kk:mm");
    }
}

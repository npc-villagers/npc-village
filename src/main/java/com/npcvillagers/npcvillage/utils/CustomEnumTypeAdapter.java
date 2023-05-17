package com.npcvillagers.npcvillage.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CustomEnumTypeAdapter<T extends Enum<T>> extends TypeAdapter<T> {

    private final Class<T> enumClass;
    private final Map<String, T> displayNameToConstantMap = new HashMap<>();

    public CustomEnumTypeAdapter(Class<T> enumClass) {
        this.enumClass = enumClass;
        try {
            for (T constant : enumClass.getEnumConstants()) {
                Method getDisplayNameMethod = enumClass.getMethod("getDisplayName");
                String displayName = (String) getDisplayNameMethod.invoke(constant);
                displayNameToConstantMap.put(displayName, constant);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(JsonWriter out, T value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            try {
                Method getDisplayNameMethod = enumClass.getMethod("getDisplayName");
                String displayName = (String) getDisplayNameMethod.invoke(value);
                out.value(displayName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public T read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        } else {
            return displayNameToConstantMap.get(reader.nextString());
        }
    }
}

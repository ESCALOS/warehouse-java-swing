package com.nanoka.almacenrepuestos.adapters;

import com.google.gson.*;
import java.lang.reflect.Type;
import com.nanoka.almacenrepuestos.models.MovementType;

public class MovementTypeAdapter implements JsonSerializer<MovementType>, JsonDeserializer<MovementType> {
    @Override
    public JsonElement serialize(MovementType src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.name());
    }

    @Override
    public MovementType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return MovementType.valueOf(json.getAsString());
    }
}

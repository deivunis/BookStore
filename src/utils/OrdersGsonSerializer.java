package utils;

import bookds.Book;
import bookds.Cart;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.List;

public class OrdersGsonSerializer implements JsonSerializer<List<Cart>> {
    @Override
    public JsonElement serialize(List<Cart> carts, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Cart.class, new OrdersGsonSerializer());
        Gson parser = gsonBuilder.create();

        for (Cart c : carts) {
            jsonArray.add(parser.toJson(c));
        }
        System.out.println(jsonArray);
        return jsonArray;
    }
}

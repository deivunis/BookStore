package utils;

import bookds.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

public class OrderGsonSerializer implements JsonSerializer<Cart> {
    @Override
    public JsonElement serialize(Cart cart, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject cartJson = new JsonObject();
        cartJson.addProperty("id", cart.getId());
        cartJson.addProperty("dateCreated", cart.getDateCreated().toString());
        cartJson.addProperty("modifiedDate", cart.getModifiedDate().toString());
        cartJson.addProperty("customerName", cart.getCustomerName());
        cartJson.addProperty("shippingAddress", cart.getShippingAddress());
        cartJson.addProperty("cartPrice", cart.getCartPrice());
        cartJson.addProperty("cartSalePrice", cart.getCartSalePrice());
        cartJson.addProperty("customerEmail", cart.getCustomerEmail());
        cartJson.addProperty("items", cart.getItems().toString());
        cartJson.addProperty("supervisingEmployees", cart.getSupervisingEmployees().toString());
        cartJson.addProperty("totalPrice", cart.getTotalPrice());
        cartJson.addProperty("status", cart.getStatus().toString());

        return cartJson;
    }

}

package utils;

import bookds.Book;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookGsonSerializer implements JsonSerializer<Book> {
    @Override
    public JsonElement serialize(Book book, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject bookJson = new JsonObject();
        bookJson.addProperty("id", book.getId());
        bookJson.addProperty("bookTitle", book.getBookTitle());
        bookJson.addProperty("description", book.getDescription());
        bookJson.addProperty("publishDate", book.getPublishDate().toString());
        bookJson.addProperty("price", book.getPrice());
        bookJson.addProperty("salePrice", book.getSalePrice());
        bookJson.addProperty("stockQuantity", book.getStockQuantity());
        bookJson.addProperty("authors", book.getAuthors());
        bookJson.addProperty("pageNum", book.getPageNum());
        bookJson.addProperty("edition", book.getEdition());
        bookJson.addProperty("comments", book.getComments().toString());
        bookJson.addProperty("inOrders", book.getInOrders().toString());
        bookJson.addProperty("genre", book.getGenre().toString());
        return bookJson;
    }

}

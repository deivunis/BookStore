package webControllers;

import bookds.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import hibernateControllers.BookHibControl;
import hibernateControllers.UserHibControl;
import net.bytebuddy.description.method.MethodDescription;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utils.BookGsonSerializer;
import utils.BooksGsonSerializer;
import utils.LocalDateGsonSerializer;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

@Controller
public class bookWebController {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookStore");
    BookHibControl bookHibControl = new BookHibControl(entityManagerFactory);

    @RequestMapping(value = "/book/createBook", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String createBookWeb(@RequestBody String request) {
        Gson gson = new Gson();
        Properties data = gson.fromJson(request, Properties.class);
        bookHibControl.createBook(new Book(data.getProperty("title"),
                LocalDate.parse(data.getProperty("publishDate")),
                Integer.parseInt(data.getProperty("pageNum")),
                data.getProperty("authors"),
                Integer.parseInt(data.getProperty("edition")),
                data.getProperty("description"),
                Genre.valueOf(data.getProperty("genre")),
                Double.parseDouble(data.getProperty("price")),
                Double.parseDouble(data.getProperty("salePrice")),
                data.getProperty("stockQuantity")));


        return "Success";
    }

    @RequestMapping(value = "/book/allBooks", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllBooksWeb() {
        List<Book> book = bookHibControl.getAllBook();
        GsonBuilder builder = new GsonBuilder();
        Type booksList = new TypeToken<List<Book>>() {
        }.getType();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        builder.registerTypeAdapter(Book.class, new BookGsonSerializer()).registerTypeAdapter(booksList, new BooksGsonSerializer());

        return builder.create().toJson(book);
    }

    @RequestMapping(value = "/book/getBookById/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getBookByIdWeb(@PathVariable(name = "id") int id) {
        Book book = bookHibControl.getBookById(id);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        builder.registerTypeAdapter(Book.class, new BookGsonSerializer());

        //return book.toString();

        if(book != null) return builder.create().toJson(book);
        else return "No such book by given ID!";
    }

    @RequestMapping(value = "book/updateBook", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateBookWeb(@RequestBody String request) {
        Gson gson = new Gson();
        Properties data = gson.fromJson(request, Properties.class);
        Book book = bookHibControl.getBookById(Integer.parseInt(data.getProperty("id")));
        book.setBookTitle(data.getProperty("title"));
        book.setPublishDate(LocalDate.parse(data.getProperty("publishDate")));
        book.setPageNum(Integer.parseInt(data.getProperty("pageNum")));
        book.setAuthors(data.getProperty("authors"));
        book.setEdition(Integer.parseInt(data.getProperty("edition")));
        book.setDescription(data.getProperty("description"));
        book.setGenre(Genre.valueOf(data.getProperty("genre")));
        book.setPrice(Double.parseDouble(data.getProperty("price")));
        book.setSalePrice(Double.parseDouble(data.getProperty("salePrice")));
        book.setStockQuantity(data.getProperty("stockQuantity"));

        bookHibControl.updateBook(book);

        return bookHibControl.getBookById(Integer.parseInt(data.getProperty("id"))).toString();
    }

    @RequestMapping(value = "book/deleteBook/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String deleteUserWeb(@PathVariable(name = "id") int id) {
        bookHibControl.removeBook(id);
        Book book = bookHibControl.getBookById(id);
        if (book == null) return "Book deleted successfully";
        else return "Book not deleted";
    }

    //--------------------------------------------------COMMENTS WEB CONTROL--------------------------------------------------//
    @RequestMapping(value = "/book/getAllCommentsByBookId/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllCommentsByBookIdWeb(@PathVariable(name = "id") int id) {
        Book book = bookHibControl.getBookById(id);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        builder.registerTypeAdapter(Book.class, new BookGsonSerializer());

        //return book.toString();

        if(book != null) return builder.create().toJson(book.getComments());
        else return "No such comment by given ID!";
    }

    @RequestMapping(value = "/book/getCommentById/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getCommentByIdWeb(@PathVariable(name = "id") int id) {
        Comment comment = bookHibControl.getCommentById(id);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        builder.registerTypeAdapter(Book.class, new BookGsonSerializer());


        //return comment.toString();

        if(comment != null) return builder.create().toJson(comment);
        else return "No such comment by given ID!";
    }
    @RequestMapping(value = "/book/createCommentByBookId", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String createCommentByBookId(@RequestBody String request) {
        Gson gson = new Gson();
        Properties data = gson.fromJson(request, Properties.class);

        Book book = bookHibControl.getBookById(Integer.parseInt(data.getProperty("id")));
        Comment comment = new Comment(data.getProperty("commenterName"),
                data.getProperty("commentTitle"),
                data.getProperty("commentText"),
                LocalDate.parse(data.getProperty("dateCreated")),
                Double.parseDouble(data.getProperty("rating")));
        book.getComments().add(comment);
        bookHibControl.updateBook(book);
        return book.toString();
    }

    @RequestMapping(value = "book/deleteComment/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String deleteCommentWeb(@PathVariable(name = "id") int id) {
        bookHibControl.removeComment(id);
        Comment comment = bookHibControl.getCommentById(id);
        if (comment == null) return "Comment deleted successfully";
        else return "Comment not deleted";
    }
}

package webControllers;

import bookds.Book;
import bookds.Cart;
import bookds.Genre;
import bookds.Status;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import hibernateControllers.BookHibControl;
import hibernateControllers.CartHibControl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utils.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Controller
public class cartWebController {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookStore");
    CartHibControl cartHibControl = new CartHibControl(entityManagerFactory);

    @RequestMapping(value = "/order/createOrder", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String createOrderWeb(@RequestBody String request) {
        Gson gson = new Gson();
        Properties data = gson.fromJson(request, Properties.class);
        cartHibControl.createCart(new Cart(LocalDate.parse(data.getProperty("dateCreated")),
                LocalDate.parse(data.getProperty("modifiedDate")),
                data.getProperty("customerName"),
                data.getProperty("shippingAddress"),
                Double.parseDouble(data.getProperty("cartPrice")),
                Double.parseDouble(data.getProperty("cartSalePrice")),
                data.getProperty("customerEmail"),
                Double.parseDouble(data.getProperty("totalPrice")),
                Status.valueOf(data.getProperty("status")) ));
        return "Success";
    }

    @RequestMapping(value = "/order/getAllOrders", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllOrdersWeb() {
        List<Cart> cart = cartHibControl.getAllOrders();
        GsonBuilder builder = new GsonBuilder();
        Type ordersList = new TypeToken<List<Cart>>() {}.getType();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        builder.registerTypeAdapter(Cart.class, new OrderGsonSerializer()).registerTypeAdapter(ordersList, new OrdersGsonSerializer());

        return builder.create().toJson(cart);
    }

    @RequestMapping(value = "/order/getOrderById/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getOrderByIdWeb(@PathVariable(name = "id") int id) {
        Cart cart = cartHibControl.getOrderById(id);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        builder.registerTypeAdapter(Cart.class, new OrderGsonSerializer());

        //return book.toString();

        if(cart != null) return builder.create().toJson(cart);
        else return "No such order by given ID!";
    }

    @RequestMapping(value = "order/updateOrder", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateOrderWeb(@RequestBody String request) {
        Gson gson = new Gson();
        Properties data = gson.fromJson(request, Properties.class);
        Cart cart = cartHibControl.getOrderById(Integer.parseInt(data.getProperty("id")));
        cart.setDateCreated(LocalDate.parse(data.getProperty("dateCreated")));
        cart.setModifiedDate(LocalDate.parse(data.getProperty("modifiedDate")));
        cart.setCustomerName(data.getProperty("customerName"));
        cart.setShippingAddress(data.getProperty("shippingAddress"));
        cart.setCartPrice(Double.parseDouble(data.getProperty("cartPrice")));
        cart.setCartSalePrice(Double.parseDouble(data.getProperty("cartSalePrice")));
        cart.setCustomerEmail(data.getProperty("customerEmail"));
        cart.setTotalPrice(Double.parseDouble(data.getProperty("totalPrice")));
        cart.setStatus(Status.valueOf(data.getProperty("status")));

        cartHibControl.updateOrder(cart);

        return cartHibControl.getOrderById(Integer.parseInt(data.getProperty("id"))).toString();
    }

    @RequestMapping(value = "order/deleteOrder/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String deleteOrderWeb(@PathVariable(name = "id") int id) {
        cartHibControl.removeOrder(id);
        cartHibControl.removeOrder(id);
        Cart cart = cartHibControl.getOrderById(id);
        if (cart == null) return "Order deleted successfully";
        else return "Order not deleted";
    }
}

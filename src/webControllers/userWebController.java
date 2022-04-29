package webControllers;

import bookds.Individual;
import bookds.LegalPerson;
import bookds.Role;
import bookds.User;
import com.google.gson.GsonBuilder;
import hibernateControllers.UserHibControl;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;
import utils.LocalDateGsonSerializer;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sound.sampled.Line;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

@Controller //localhost:8080/BookStore/user
public class userWebController {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookStore");
    UserHibControl userHibControl = new UserHibControl(entityManagerFactory);

    @RequestMapping(value = "user/test")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String testSpringCtrl() {
        return "Success";
    }

    @RequestMapping(value = "/user/validateUser", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String validateUserWeb(@RequestBody String userInfo) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(userInfo, Properties.class);
        User user = userHibControl.getUserByLoginData(data.getProperty("login"), data.getProperty("password"));
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());

        return builder.create().toJson(user);
    }

    @RequestMapping(value = "/user/createUser", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String createUserWeb(@RequestBody String request) {
        Gson gson = new Gson();
        Properties data = gson.fromJson(request, Properties.class);
        if(data.getProperty("userType").equals("I")) {
            userHibControl.createUser(new Individual(data.getProperty("login"),
                    data.getProperty("password"),
                    data.getProperty("email"),
                    data.getProperty("address"),
                    Role.valueOf(data.getProperty("role")),
                    data.getProperty("name"),
                    data.getProperty("surname"),
                    data.getProperty("phoneNumber"),
                    LocalDate.parse(data.getProperty("birthDate"))));
        }
        else if(data.getProperty("userType").equals("LP")) {
            userHibControl.createUser(new LegalPerson(data.getProperty("login"),
                    data.getProperty("password"),
                    data.getProperty("email"),
                    data.getProperty("address"),
                    Role.valueOf(data.getProperty("role")),
                    data.getProperty("companyTitle"),
                    data.getProperty("ceo"),
                    data.getProperty("registrationNumber"),
                    data.getProperty("vatNumber")));

        } else return "wrong user type given";
        //userHibControl.createUser(data);
        return "Success";
    }

    @RequestMapping(value = "/user/allUsers", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllUsersWeb() {
        List<User> users = userHibControl.getAllUsers();
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        return builder.create().toJson(users);
        //return users.toString();
    }

    @RequestMapping(value = "/user/getUserById/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getUserByIdWeb(@PathVariable(name = "id") int id) {
        User user = userHibControl.getUserById(id);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());

        if(user != null) return builder.create().toJson(user);
        else return "No such user by given ID!";
    }

    @RequestMapping(value = "user/updateUser", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateUserWeb(@RequestBody String request) {
        Gson gson = new Gson();
        Properties data = gson.fromJson(request, Properties.class);

        if (data.getProperty("userType").equals("I")) {
            Individual individual = (Individual) userHibControl.getUserById(Integer.parseInt(data.getProperty("id")));
            individual.setName(data.getProperty("name"));
            individual.setSurname(data.getProperty("surname"));
            individual.setPhoneNumber(data.getProperty("phoneNumber"));
            individual.setEmail(data.getProperty("email"));
            individual.setAddress(data.getProperty("address"));
            individual.setBirthDate(LocalDate.parse(data.getProperty("birthDate")));
            userHibControl.updateUser(individual);
        } else if (data.getProperty("userType").equals("LP")) {
            LegalPerson legalPerson = (LegalPerson) userHibControl.getUserById(Integer.parseInt(data.getProperty("id")));
            legalPerson.setCompanyTitle(data.getProperty("title"));
            legalPerson.setCeo(data.getProperty("ceo"));
            legalPerson.setRegistrationNumber(data.getProperty("registrationNumber"));
            legalPerson.setVatNumber(data.getProperty("vatNumber"));
            userHibControl.updateUser(legalPerson);
        } else return "wrong user type given";
        return userHibControl.getUserById(Integer.parseInt(data.getProperty("id"))).toString();
    }

    @RequestMapping(value = "user/deleteUser/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String deleteUserWeb(@PathVariable(name = "id") int id) {
        userHibControl.removeUser(id);
        User user = userHibControl.getUserById(id);
        if (user == null) return "User deleted successfully";
        else return "User not deleted";
    }
}

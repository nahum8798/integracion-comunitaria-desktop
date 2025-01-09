package controller;

import java.util.List;

import model.User;
import service.UserService;
import connection.ResultDataBase;

public class UserController {
    private UserService userService = new UserService();

    public ResultDataBase registerUser(User user, String role, String category, String profession, String typeProvider, String typeJornal,
                                       String city, String departament, String province, String street, String streetNumber) {
        return userService.registerUser(user, role, category, profession, typeProvider, typeJornal, city, departament, province, street, streetNumber);
    }

    public List<String> getTypeProviders() {
        return userService.getTypeProviders();
    }

    public List<String> getProfessions() {
        return userService.getProfessions();
    }

    public List<String> getCategories() {
        return userService.getCategories();
    }

    public List<String> getTypeJornals() {
        return userService.getTypeJornals();
    }

    public List<String> getCities() {
        return userService.getCities();
    }

    public List<String> getDepartments() {
        return userService.getDepartments();
    }

    public List<String> getProvinces() {
        return userService.getProvinces();
    }

}
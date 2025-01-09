package service;

import java.util.List;

import connection.ResultDataBase;
import model.User;
import model.Provider;
import model.Customer;
import repository.UserRepository;

public class UserService {
    private UserRepository userRepository = new UserRepository();

    public ResultDataBase registerUser(User user, String role, String category, String profession, String typeProvider, String typeJornal,
                                       String city, String departament, String province, String street, String streetNumber) {
        return userRepository.registerUser(user, role, category, profession, typeProvider, typeJornal, city, departament, province, street, streetNumber);
    }

    public List<String> getTypeProviders() {
        return userRepository.getTypeProviders();
    }

    public List<String> getProfessions() {
        return userRepository.getProfessions();
    }

    public List<String> getCategories() {
        return userRepository.getCategories();
    }

    public List<String> getTypeJornals() {
        return userRepository.getTypeJornals();
    }

    public List<String> getCities() {
        return userRepository.getCities();
    }

    public List<String> getDepartments() {
        return userRepository.getDepartments();
    }

    public List<String> getProvinces() {
        return userRepository.getProvinces();
    }
}
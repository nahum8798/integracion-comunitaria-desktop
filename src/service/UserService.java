package service;

import java.util.List;

import connection.ResultDataBase;
import model.User;
import model.Provider;
import model.Customer;
import repository.AddressRepository;
import repository.UserRepository;

public class UserService {
    private UserRepository userRepository = new UserRepository();
    private AddressRepository addressRepository = new AddressRepository();

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
        return addressRepository.getCities();
    }

    public List<String> getDepartments() {
        return addressRepository.getDepartments();
    }

    public List<String> getProvinces() {
        return addressRepository.getProvinces();
    }
}
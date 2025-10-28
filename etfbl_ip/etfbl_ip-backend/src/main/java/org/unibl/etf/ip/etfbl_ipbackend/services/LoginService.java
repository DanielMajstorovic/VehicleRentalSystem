package org.unibl.etf.ip.etfbl_ipbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.etfbl_ipbackend.data.dtos.LoginRequest;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Client;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Employee;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.User;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.ClientRepository;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.EmployeeRepository;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());
        if(!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Username does not exist!");
        }

        User user = userOptional.get();

        Optional<Client> clientOptional = clientRepository.findByUser(user);
        if (clientOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Clients cannot log in through this endpoint.");
        }

        if(user.getDeleted() != 0){
            return ResponseEntity.badRequest().body("Account has been deleted!");
        }

        if(!user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.badRequest().body("Wrong password!");
        }

        Optional<Employee> employeeOptional = employeeRepository.findByUser(user);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            Map<String, String> response = new HashMap<>();
            response.put("username", user.getUsername());
            response.put("role", employee.getRole().toString());
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body("Unknown user type.");

    }

}

package com.ivanfrias.myapi.Controllers;

import com.ivanfrias.myapi.Dto.*;
import com.ivanfrias.myapi.Exceptions.EmailErrorException;
import com.ivanfrias.myapi.Exceptions.EmailNotUniqueException;
import com.ivanfrias.myapi.Services.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/users")
public class UsersController {
    @Autowired
    private UsersServiceImpl usersService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UsersDTO user) {
        ArrayList<Object> results = null;
        try {
            results = usersService.createUser(user);
        } catch (EmailErrorException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constants.EMAIL_ERROR);
        } catch (EmailNotUniqueException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constants.EMAIL_NOT_UNIQUE);
        }
        // Desgranamos el result
        UsersDTONoPass usersDTONoPass = (UsersDTONoPass) results.get(0);
        int emailValidation = (int) results.get(1);
        // Validations
        if (emailValidation==1) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constants.EMPTY_EMAIL);
        if (emailValidation==2) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constants.SPECIAL_CHARACTER_EMAIL);
        if (emailValidation==3) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constants.LONG_EMAIL);

        if (usersDTONoPass.getId()!=null){
            return ResponseEntity.status(HttpStatus.OK).body(usersDTONoPass);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersDTONoPass> getUserById(@PathVariable Long id) {
        UsersDTONoPass usersDTONoPass = usersService.getUserById(id);
        if (usersDTONoPass.getId()!=null){
            return ResponseEntity.status(HttpStatus.OK).body(usersDTONoPass);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<UsersDTONoPass> deleteUserById(@PathVariable Long id) {
        UsersDTONoPass usersDTONoPass = usersService.deleteUserById(id);
        if (usersDTONoPass.getId()!=null){
            return ResponseEntity.status(HttpStatus.OK).body(usersDTONoPass);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UsersDTONoPass>> getAllUsers() {
        List<UsersDTONoPass> usersDTONoPasses = usersService.getAllUsers();
        if (!usersDTONoPasses.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(usersDTONoPasses);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<UsersDTONoPass> getProfile(HttpServletRequest request){
        Long id = usersService.verifyToken(request);
        UsersDTONoPass usersDTONoPass = usersService.getUserById(id);
        if (usersDTONoPass.getId()!=null){
            return ResponseEntity.status(HttpStatus.OK).body(usersDTONoPass);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/validateEmail") ResponseEntity<?> validateEmailCode(HttpServletRequest request, @RequestBody CodeDTO code){
        Long id = usersService.verifyToken(request);
        int codeInt = Integer.parseInt(code.getCode());
        UsersDTONoPass usersDTONoPass = usersService.validateEmailCode(id, codeInt);
        if (usersDTONoPass.getId()!=null){
            return ResponseEntity.status(HttpStatus.OK).body(usersDTONoPass);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

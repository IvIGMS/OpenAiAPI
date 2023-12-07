package com.ivanfrias.myapi.Services;


import com.ivanfrias.myapi.Dto.UsersDTO;
import com.ivanfrias.myapi.Dto.UsersDTONoPass;
import com.ivanfrias.myapi.Exceptions.EmailErrorException;
import com.ivanfrias.myapi.Exceptions.EmailNotUniqueException;
import com.ivanfrias.myapi.Models.Users;
import com.ivanfrias.myapi.Repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UsersServiceImplTest {
    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UsersServiceImpl usersServiceImpl;

    @Test
    public void getUserById_UserFound() {
        Users expectedUser = new Users(1L, "1234", "friasgilivan@gmail.com", "Ivan", "Frias");
        when(usersRepository.findById(1L)).thenReturn(Optional.of(expectedUser));
        UsersDTONoPass usersDTONoPass = usersServiceImpl.getUserById(1L);
        Assertions.assertEquals(expectedUser.getId(), usersDTONoPass.getId());
        Assertions.assertEquals(expectedUser.getName(), usersDTONoPass.getName());
        Assertions.assertEquals(expectedUser.getEmail(), usersDTONoPass.getEmail());
    }

    @Test
    public void getUserById_UserNotFound() {
        Users expectedUser = new Users();
        UsersDTONoPass usersDTONoPass = usersServiceImpl.getUserById(2L);
        Assertions.assertEquals(expectedUser.getId(), usersDTONoPass.getId());
        Assertions.assertEquals(expectedUser.getName(), usersDTONoPass.getName());
        Assertions.assertEquals(expectedUser.getEmail(), usersDTONoPass.getEmail());
    }

    @Test
    public void deleteUserById_Ok() {
        Users expectedUser = new Users(1L, "1234", "friasgilivan@gmail.com", "Ivan", "Frias");
        when(usersRepository.findById(1L)).thenReturn(Optional.of(expectedUser));
        UsersDTONoPass usersDTONoPass = usersServiceImpl.deleteUserById(1L);
        Assertions.assertEquals(expectedUser.getId(), usersDTONoPass.getId());
        Assertions.assertEquals(expectedUser.getName(), usersDTONoPass.getName());
        Assertions.assertEquals(expectedUser.getEmail(), usersDTONoPass.getEmail());
    }

    @Test
    public void deleteUserById_Ko() {
        Users expectedUser = new Users();
        UsersDTONoPass usersDTONoPass = usersServiceImpl.deleteUserById(2L);
        Assertions.assertEquals(expectedUser.getId(), usersDTONoPass.getId());
        Assertions.assertEquals(expectedUser.getName(), usersDTONoPass.getName());
        Assertions.assertEquals(expectedUser.getEmail(), usersDTONoPass.getEmail());
    }

    @Test
    public void getAllUsers_Ok() {
        List<Users> usersExpected = new ArrayList<Users>();
        Users expectedUser1 = new Users(1L, "1234", "friasgilivan@gmail.com", "Ivan", "Frias");
        Users expectedUser2 = new Users(2L, "1234", "friasgilivan@gmail.com", "Ivan", "Frias");
        usersExpected.add(expectedUser1);
        usersExpected.add(expectedUser2);

        when(usersRepository.findAll()).thenReturn(usersExpected);

        List<UsersDTONoPass> users = usersServiceImpl.getAllUsers();
        Assertions.assertEquals(usersExpected.size(), users.size());
    }

    @Test
    public void getAllUsers_OkEmpty() {
        List<UsersDTONoPass> users = usersServiceImpl.getAllUsers();
        Assertions.assertEquals(new ArrayList<Users>().size(), users.size());
    }

    // Este metodo es dificil de testear puesto que el codigo esta encriptado y no se puede saber en el DTO
    // porque incurririamos en un problema de seguridad. Ademas que tenemos que poner el codigo cifrado.
    @Test
    public void validateEmailCode_Ok() {
        Users expectedUser = new Users(1L, "1234", "friasgilivan@gmail.com", "Ivan", "Frias");
        expectedUser.setEmailVerificationCode("2978ef852a8e50e7b073fc0292ac0fb58503ba0a14147cca795c73a9b9316987");
        when(usersRepository.findById(1L)).thenReturn(Optional.of(expectedUser));
        UsersDTONoPass usersDTONoPass = usersServiceImpl.validateEmailCode(1L, 345319);
        // El donde es porque en la bbdd se cambia el code por un "done"
        Assertions.assertEquals(expectedUser.getEmailVerificationCode(), "done");
    }

    @Test
    public void validateEmailCode_KoBadCOde() {
        Users expectedUser = new Users(1L, "1234", "friasgilivan@gmail.com", "Ivan", "Frias");
        expectedUser.setEmailVerificationCode("2978ef852a8e50e7b073fc0292ac0fb58503ba0a14147cca795c73a9b9316987");
        when(usersRepository.findById(1L)).thenReturn(Optional.of(expectedUser));
        UsersDTONoPass usersDTONoPass = usersServiceImpl.validateEmailCode(1L, 541829);
        // El donde es porque en la bbdd se cambia el code por un "done"
        Assertions.assertNotEquals(expectedUser.getEmailVerificationCode(), "done");
    }

    // ESTOS TEST ESTAN MAL, hay que poder validar bastantes mas cosas
    @Test
    public void createUser_Ok() {
        Users savedUser = new Users(1L, "1234", "friasgilivan@gmail.com", "Ivan", "Frias");
        // Configurar el comportamiento simulado del repositorio al guardar el usuario
        when(usersRepository.save(any(Users.class))).thenReturn(savedUser);
        //UsersDTONoPass expectedUsersDTONoPass = new UsersDTONoPass(2L, "jero@gmail.com", "Jero", "Casares", 11_000_000.0, "Fc Barcelona");

        UsersDTO expectedUsersDTO = new UsersDTO("1234", "jero@gmail.com");
        ArrayList<Object> results = null;
        try {
            results = usersServiceImpl.createUser(expectedUsersDTO);
            UsersDTONoPass resultUsersDTONoPass = (UsersDTONoPass) results.get(0);

        } catch (EmailErrorException e) {
            System.out.println("Email error");
        } catch (EmailNotUniqueException e) {
            System.out.println("Not unique error");
        }

    }

    @Test
    public void createUser_KoNotValidateEmail() {
        UsersDTO expectedUsersDTO = new UsersDTO("1234", "jerogmail.com");
        ArrayList<Object> results = null;
        try {
            results = usersServiceImpl.createUser(expectedUsersDTO);
            UsersDTONoPass resultUsersDTONoPass = (UsersDTONoPass) results.get(0);

            Assertions.assertEquals(new UsersDTONoPass().getId(), resultUsersDTONoPass.getId());
            // El emailValidation es otra cosa que no es 0 (es decir, no está bien)
            Assertions.assertNotEquals(0, results.get(1));
        } catch (EmailErrorException e) {
            System.out.println("Email error");
        } catch (EmailNotUniqueException e) {
            System.out.println("Not unique error");
        }
    }

    @Test
    public void verifyToken_KoExceptionBadToken(){
        // Hacemos saltar la excepcion y devuelve -1
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer eyJhbGiOiJIUzI1NiJ9.eyJzdWIiOiJmcmlhc2dpbGl2YW5AZ21haWwuY29tIiwiZXhwIjoxNzA0MjY5ODYwLCJpZCI6MjEsImVtYWlsIjoiZnJpYXNnaWxpdmFuQGdtYWlsLmNvbSIsImlzRW1haWxWZXJpZmllZCI6dHJ1ZSwiYnVkZ2V0IjoxLjBFN30.xJPCB-1hkFE36VoOp1Z9TCUIun0x9imd68avfxwHbbU");
        Long expectedResponse = -1L; // Es el id del user al que pertenece el beared token
        Long response = usersServiceImpl.verifyToken(request);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void verifyToken_KoNotBearedToken(){
        // Si el token esta vacio o no empieza por beared y devuelve -1
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("");
        Long expectedResponse = -1L; // Es el id del user al que pertenece el beared token
        Long response = usersServiceImpl.verifyToken(request);
        assertEquals(expectedResponse, response);
    }
}
package com.ivanfrias.myapi.Services;

import com.ivanfrias.myapi.Dto.*;
import com.ivanfrias.myapi.Exceptions.EmailErrorException;
import com.ivanfrias.myapi.Exceptions.EmailNotUniqueException;
import com.ivanfrias.myapi.Interfaces.UsersService;
import com.ivanfrias.myapi.Models.Users;
import com.ivanfrias.myapi.Repositories.UsersRepository;
import com.ivanfrias.myapi.Validations.Validations;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;



    @Autowired
    EmailServiceImpl emailService;

    public ArrayList<Object> createUser(UsersDTO userDTO) throws EmailErrorException, EmailNotUniqueException {
        Users user = new Users();
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword())); // Encriptamos la pass

        int emailValidation = Validations.verifyEmail(userDTO.getEmail());

        if(emailValidation==0){
            user.setEmail(userDTO.getEmail());
            try{
                // Generamos codigo correo
                int emailCode = Validations.generarCodigo();
                String emailCodeString = String.valueOf(emailCode);
                String encryptedCode = Validations.encrypt(emailCodeString);
                user.setEmailVerificationCode(encryptedCode); // Casteamos a String para evitar complicaciones
                // Creamos el user
                Users createdUser = usersRepository.save(user);
                UsersDTONoPass usersDTONoPass = new UsersDTONoPass(createdUser.getId(), createdUser.getEmail(), createdUser.getName(), createdUser.getLastname());
                // Le pasamos el id para que salga en pantalla
                usersDTONoPass.setId(createdUser.getId());

                // Enviamos codigo al correo
                try{
                    emailService.sendEmail(user.getEmail(), emailCode);
                } catch (Exception e){
                    throw new EmailErrorException(Constants.EMAIL_ERROR);
                }

                return new ArrayList<>(Arrays.asList(usersDTONoPass, emailValidation));
            } catch (Exception e){
                // DATO REPETIDO
                throw new EmailNotUniqueException(Constants.EMAIL_NOT_UNIQUE);
            }
        }
        // Si no se pudo crear el usuario
        return new ArrayList<>(Arrays.asList(new UsersDTONoPass(), emailValidation));
    }

    public UsersDTONoPass getUserById(Long id) {
        Optional<Users> user = usersRepository.findById(id);

        if (user.isPresent()) {
            // Debemos devolver un userDTONoPass para no incurrir en un problema de seguridad al devolverle la password.
            UsersDTONoPass usersDTONoPass = new UsersDTONoPass(user.get().getId(), user.get().getEmail(), user.get().getName(), user.get().getLastname());
            // Le pasamos el id para que salga en pantalla
            usersDTONoPass.setId(id);
            return usersDTONoPass;
        }
        return new UsersDTONoPass();
    }

    public UsersDTONoPass deleteUserById(Long id) {
        Optional<Users> user = usersRepository.findById(id);

        if (user.isPresent()) {
            // Debemos devolver un userDTONoPass para no incurrir en un problema de seguridad al devolverle la password.
            UsersDTONoPass usersDTONoPass = new UsersDTONoPass(user.get().getId(), user.get().getEmail(), user.get().getName(), user.get().getLastname());
            // Le pasamos el id para que salga en pantalla
            usersDTONoPass.setId(id);
            usersRepository.deleteById(id);
            return usersDTONoPass;
        }
        return new UsersDTONoPass();
    }

    public List<UsersDTONoPass> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        List<UsersDTONoPass> usersDTONoPasses = new ArrayList<UsersDTONoPass>();

        for(Users a : users ){
            usersDTONoPasses.add(new UsersDTONoPass(a.getId(), a.getEmail(), a.getName(), a.getLastname()));
        }
        return usersDTONoPasses;
    }

    public UsersDTONoPass validateEmailCode(long id, int code) {
        Optional<Users> user = usersRepository.findById(id);
        String codeString = String.valueOf(code);
        String encryptedCode = Validations.encrypt(codeString);
        if (user.get().getEmailVerificationCode().equals(encryptedCode)){
            user.get().setEmailVerified(true);
            user.get().setEmailVerificationCode("done");
            usersRepository.save(user.get());

            // Creamos el DTO
            return new UsersDTONoPass(user.get().getId(), user.get().getEmail(), user.get().getName(), user.get().getLastname());
        }

        return new UsersDTONoPass();
    }

    public Long verifyToken(HttpServletRequest request){
        String secretKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
        // Obtener el token del encabezado "Authorization"
        String token = request.getHeader("Authorization");

        // Verificar si el token comienza con "Bearer "
        if (token != null && token.startsWith("Bearer ")) {
            try {
                // Eliminar la parte "Bearer " del token para obtener solo el token JWT
                String jwtToken = token.substring(7); // 7 es la longitud de "Bearer "

                // Decodificar el token JWT y obtener los claims (información del usuario)
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                        .build()
                        .parseClaimsJws(jwtToken)
                        .getBody();

                // Acceder a los claims específicos del token JWT
                int id_ = (int) claims.get("id");
                return (long) id_;
            } catch (Exception e) {
                return -1L;
            }
        }
        return -1L;
    }
}
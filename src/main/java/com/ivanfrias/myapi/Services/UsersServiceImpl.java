package com.ivanfrias.myapi.Services;

import com.ivanfrias.myapi.Dto.*;
import com.ivanfrias.myapi.Exceptions.EmailErrorException;
import com.ivanfrias.myapi.Exceptions.EmailNotUniqueException;
import com.ivanfrias.myapi.Interfaces.UsersService;
import com.ivanfrias.myapi.Models.Users;
import com.ivanfrias.myapi.Repositories.UsersRepository;
import com.ivanfrias.myapi.Validations.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    EmailServiceImpl emailService;

    public ArrayList<Object> createUser(UsersDTO userDTO) throws EmailErrorException, EmailNotUniqueException {
        Users user = new Users();
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword())); // Encriptamos la pass

        int emailValidation = Validations.verifyEmail(userDTO.getEmail());

        if(emailValidation==0){
            user.setEmail(userDTO.getEmail());
            user.setBudget(11_000_000);
            try{
                // Generamos codigo correo
                int emailCode = Validations.generarCodigo();
                String emailCodeString = String.valueOf(emailCode);
                String encryptedCode = Validations.encrypt(emailCodeString);
                user.setEmailVerificationCode(encryptedCode); // Casteamos a String para evitar complicaciones
                // Creamos el user
                Users createdUser = usersRepository.save(user);
                UsersDTONoPass usersDTONoPass = new UsersDTONoPass(createdUser.getId(), createdUser.getEmail(), createdUser.getName(), createdUser.getLastname(), user.getBudget(), user.getTeamName());
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
            UsersDTONoPass usersDTONoPass = new UsersDTONoPass(user.get().getId(), user.get().getEmail(), user.get().getName(), user.get().getLastname(), user.get().getBudget(), user.get().getTeamName());
            // Le pasamos el id para que salga en pantalla
            usersDTONoPass.setId(id);
            return usersDTONoPass;
        }
        return new UsersDTONoPass();
    }

    public UsersDTONoPass deleteUserById(Long id) {
        Optional<Users> user = usersRepository.findById(id);

        // Desvinculamos los jugadores de ese player.
        playerRepository.resetTeam(id);

        if (user.isPresent()) {
            // Debemos devolver un userDTONoPass para no incurrir en un problema de seguridad al devolverle la password.
            UsersDTONoPass usersDTONoPass = new UsersDTONoPass(user.get().getId(), user.get().getEmail(), user.get().getName(), user.get().getLastname(), user.get().getBudget(), user.get().getTeamName());
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
            usersDTONoPasses.add(new UsersDTONoPass(a.getId(), a.getEmail(), a.getName(), a.getLastname(), a.getBudget(), a.getTeamName()));
        }
        return usersDTONoPasses;
    }


    public ArrayList<Object> setTeamName(Long id, TeamNameDTO teamNameDTO) throws Exception {
        Optional<Users> user = usersRepository.findById(id);
        int verifyTeamName = Validations.verifyName(teamNameDTO.getTeamName());

        if (user.isPresent()) {
            if (verifyTeamName==0){
                // Seteamos el Nombre del equipo y lo guardamos
                String teamName = teamNameDTO.getTeamName().toLowerCase();
                user.get().setTeamName(teamName);

                usersRepository.save(user.get());

                // Debemos devolver un userDTONoPass para no incurrir en un problema de seguridad al devolverle la password.
                UsersDTONoPass usersDTONoPass = new UsersDTONoPass(user.get().getId(), user.get().getEmail(), user.get().getName(), user.get().getLastname(), user.get().getBudget(), teamName);
                // Le pasamos el id para que salga en pantalla
                usersDTONoPass.setId(id);
                return new ArrayList<>(Arrays.asList(usersDTONoPass, verifyTeamName));
            }
        }
        return new ArrayList<>(Arrays.asList(new UsersDTONoPass(), verifyTeamName));
    }

    public ArrayList<Object> setNameLastname(Long id, NameLastnameDTO nameLastnameDTO) {
        Optional<Users> user = usersRepository.findById(id);
        int verifyName = Validations.verifyName(nameLastnameDTO.getName());
        int verifySurname = Validations.verifyName(nameLastnameDTO.getLastname());
        if (user.isPresent()) {
            if (verifyName==0 && verifySurname==0){
                // Seteamos el Nombre del name y lastname
                user.get().setName(nameLastnameDTO.getName());
                user.get().setLastname(nameLastnameDTO.getLastname());
                usersRepository.save(user.get());
                // Debemos devolver un userDTONoPass para no incurrir en un problema de seguridad al devolverle la password.
                UsersDTONoPass usersDTONoPass = new UsersDTONoPass(user.get().getId(), user.get().getEmail(), user.get().getName(), user.get().getLastname(), user.get().getBudget(), user.get().getTeamName());
                // Le pasamos el id para que salga en pantalla
                usersDTONoPass.setId(id);
                return new ArrayList<>(Arrays.asList(usersDTONoPass, verifyName, verifySurname));
            }
        }
        return new ArrayList<>(Arrays.asList(new UsersDTONoPass(), verifyName, verifySurname));
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
            return new UsersDTONoPass(user.get().getId(), user.get().getEmail(), user.get().getName(), user.get().getLastname(), user.get().getBudget(), user.get().getTeamName());
        }

        return new UsersDTONoPass();
    }
}
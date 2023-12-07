package com.ivanfrias.myapi.Interfaces;

import com.ivanfrias.myapi.Dto.NameLastnameDTO;
import com.ivanfrias.myapi.Dto.TeamNameDTO;
import com.ivanfrias.myapi.Dto.UsersDTO;
import com.ivanfrias.myapi.Dto.UsersDTONoPass;
import com.ivanfrias.myapi.Exceptions.EmailErrorException;
import com.ivanfrias.myapi.Exceptions.EmailNotUniqueException;

import java.util.ArrayList;
import java.util.List;

public interface UsersService {
    public ArrayList<Object> createUser(UsersDTO userDTO) throws EmailErrorException, EmailNotUniqueException;
    public UsersDTONoPass getUserById(Long id);
    public UsersDTONoPass deleteUserById(Long id);
    public List<UsersDTONoPass> getAllUsers();
    public ArrayList<Object> setTeamName(Long id, TeamNameDTO teamNameDTO) throws Exception;
    public ArrayList<Object> setNameLastname(Long id, NameLastnameDTO nameLastnameDTO);
    public UsersDTONoPass validateEmailCode(long id, int code);
}

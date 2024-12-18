package com.example.userms.Service;

import com.ctc.wstx.util.StringUtil;
import com.example.userms.DTO.UserDTO;
import com.example.userms.Entity.User;
import com.example.userms.Exception.UserNotFoundException;
import com.example.userms.Repository.UserRepositor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepositor userRepositor;
    private final ModelMapper modelMapper;

    public UserDTO SaveUser(UserDTO request) {
       User user = modelMapper.map(request ,User.class);
       User Saveuser =userRepositor.save(user);
        return modelMapper.map(Saveuser ,UserDTO.class);
    }
    public void UpdateUser(UUID id ,UserDTO request){
        User user = userRepositor.findById(id).orElseThrow(() -> new UserNotFoundException(
                String.format("Impossible de mettre à jour l'utilisateur : Aucun utilisateur trouvé avec l'ID fourni : %s", request.name())
        ));
        mergeUser(user,request);
        userRepositor.save(user);

    }
//DESC:La méthode mergeUser permet de mettre à jour un utilisateur en fonction des données d'un UserDTO, en ne remplaçant que les champs non vides ou non nuls. Elle garantit que les informations existantes sont préservées si elles ne sont pas modifiées
    private void mergeUser(User user, UserDTO request) {
        if (StringUtils.isNotBlank(request.name())) {
            user.setName(request.name());
        }
        if (StringUtils.isNotBlank(request.email())) {
            user.setEmail(request.email());
        }
        if (request.address() != null) {
            user.setAddress(request.address());
        }
        if (StringUtils.isNotBlank(request.phoneNumber())) {
            user.setPhoneNumber(request.phoneNumber());
        }
    }

    public List<User> findAllUsers() {
        return userRepositor.findAll() ;
    }

    public Boolean exitsbyid(UUID id) {
        return userRepositor.findById(id).isPresent();
    }
    public User findById(UUID id){
        return userRepositor.findById(id).orElseThrow(()-> new UserNotFoundException(
                String.format("Impossible de mettre à jour l'utilisateur : Aucun utilisateur trouvé avec l'ID fourni : %s", id)));
    }

    public void DeleteUser(UUID id) {
        userRepositor.deleteById(id  );
    }
}

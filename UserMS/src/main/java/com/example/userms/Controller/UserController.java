package com.example.userms.Controller;


import com.example.userms.DTO.UserDTO;
import com.example.userms.Entity.User;
import com.example.userms.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private  UserService userService;

    @PostMapping("/save")
    public ResponseEntity<UserDTO> CreateUser(@RequestBody @Valid UserDTO request){
        return ResponseEntity.ok(userService.SaveUser(request));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> UpdateUser(@PathVariable UUID id ,@RequestBody @Valid UserDTO request){
     userService.UpdateUser(id, request);
      return ResponseEntity.accepted().build();
    }
        /*
    test sur postman uri :http://localhost:8081/api/v1/users/update/210c220e-aeb8-452f-8029-ff43ac880f15
    {
        "name": "Nouveau Nom",
            "email": "nouveau.email@example.com",
            "phoneNumber": "+12345678901",
            "role": "USER",
            "address": "Nouvelle Adresse"
    }*/


    @GetMapping("/all")
    public ResponseEntity<List<User>> FindAll(){
        return ResponseEntity.ok(userService.findAllUsers());
    }//done
    @GetMapping("/check/{id}")
    public ResponseEntity<Boolean> VerifyExistsByID(@PathVariable("id") UUID id){
       return ResponseEntity.ok(userService.exitsbyid(id)) ;
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") UUID id) {
        User user = userService.findById(id);
        if (user != null) {
            // Convertir l'objet User en UserDTO
            UserDTO userDTO = new UserDTO(user.getName(), user.getEmail(), user.getPhoneNumber(), user.getAddress());
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.status(404).body(null); // Si l'utilisateur n'existe pas
    }
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") UUID id){
        Boolean Result = userService.exitsbyid(id);
        if (Result == true){
            userService.DeleteUser(id);
            return  "L'utilisateur a été supprimé avec succès.";
        }
        return "Aucun utilisateur trouvé avec l'ID fourni.";
    }



}

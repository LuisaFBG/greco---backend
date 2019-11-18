package com.greco.rest;

import com.greco.dtos.AuthenticationDto;
import com.greco.exception.BadRequestException;
import com.greco.exception.ForbiddenException;
import com.greco.messages.GenericCheckingMessage;
import com.greco.model.Users;
import com.greco.model.projection.IProjectable;
import com.greco.model.projection.Projection;
import com.greco.service.UsersService;
import com.greco.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UsersRestController {
    @Autowired
    UsersService usersService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("{id}")
    public IProjectable byId(@PathVariable("id") Long id) {
        return Projection.convertSingle(usersService.findById(id), "users");
    }

    @PutMapping("/resetPassword")
    public void resetPassword(@RequestBody AuthenticationDto auth) {
        String password = auth.getPassword();
        String email = auth.getEmail();
        if(Utils.isEmpty(password) || Utils.isEmpty(email))
            throw new BadRequestException("Email and password are required");
        if(!auth.getPassword().equals(auth.getConfirmPassword()))
            throw new BadRequestException(GenericCheckingMessage.RESETPASSWORD_PASSWORD_AND_CONFIRM_PASSWORD_NOT_MATCH.toString());
        Users user = usersService.findByEmailAndUuid(auth.getEmail(), auth.getUuid());
        if(user == null) {
            user = usersService.findByEmail(auth.getEmail());
            if(user == null)
                throw new BadRequestException(GenericCheckingMessage.RESETPASSWORD_USER_NOT_REGISTERED.toString());
            else
                throw new BadRequestException(GenericCheckingMessage.RESETPASSWORD_INVALID_UUID.toString());
        }

        //Create a new uuid value
        user.setUuid(Utils.getRandomUUID());
        user.setPassword(passwordEncoder.encode(password));
        usersService.update(user);
    }

    /*@DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        usersService.deleteById(id);
    }*/
}

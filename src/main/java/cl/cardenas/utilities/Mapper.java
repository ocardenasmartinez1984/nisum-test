package cl.cardenas.utilities;

import cl.cardenas.controllers.entities.LoginRequest;
import cl.cardenas.controllers.entities.LoginResponse;
import cl.cardenas.controllers.entities.UserRequest;
import cl.cardenas.repositories.entities.PhoneEntity;
import cl.cardenas.repositories.entities.UserEntity;
import cl.cardenas.servicies.entities.Login;
import cl.cardenas.servicies.entities.Phone;
import cl.cardenas.servicies.entities.User;

import java.util.ArrayList;

public class Mapper {

    public static Login loginRequestToLogin(LoginRequest request){
        return Login
                .builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }

    public static User userRequestToUser(UserRequest userRequest) {
        var phonesOut = new ArrayList<Phone>();
        userRequest.getPhones().forEach(phones -> {
            var phone = Phone.builder()
                    .number(phones.getNumber())
                    .cityCode(phones.getCityCode())
                    .countryCode(phones.getCountryCode())
                    .build();
            phonesOut.add(phone);
        });
        return User
                .builder()
                .email(userRequest.getEmail())
                .name(userRequest.getName())
                .password(userRequest.getPassword())
                .phones(phonesOut)
                .build();
    }

    public static UserEntity userToUserEntity(User user) {
        var userEntity = UserEntity
                .builder()
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .created(user.getCreated())
                .modified(user.getModified())
                .isActive(user.getIsActive())
                .build();
        var phonesOut = new ArrayList<PhoneEntity>();
        user.getPhones().forEach(phones -> {
            var phoneEntity = PhoneEntity.builder()
                    .number(phones.getNumber())
                    .cityCode(phones.getCityCode())
                    .countryCode(phones.getCountryCode())
                    .user(userEntity)
                    .build();
            phonesOut.add(phoneEntity);
        });
        userEntity.setPhones(phonesOut);
        return userEntity;
    }

    public static User userEntityToUser(UserEntity user) {
        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .modified(user.getModified())
                .created(user.getCreated())
                .isActive(user.getIsActive())
                .email(user.getEmail())
                .token(user.getToken())
                .build();
    }

    public static LoginResponse userToLoginResponse (User user) {
        return LoginResponse
                .builder()
                .token(user.getToken())
                .id(user.getId())
                .created(user.getCreated())
                .modified(user.getModified())
                .isActive(user.getIsActive())
                .build();
    }

}

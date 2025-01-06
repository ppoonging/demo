package com.example.demo.user;


import com.example.demo.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SiteUserSerevice {
    private final SiteUserRepository diaryUserRepository;
    private final PasswordEncoder passwordEncoder;


    public SiteUser create(String myName, String username, String password
            , String email, String phoneNumber){
        SiteUser dU= new SiteUser();
        dU.setMyName(myName);
        dU.setUsername(username);
        dU.setPassword(passwordEncoder.encode(password) );
        dU.setEmail(email);
        dU.setPhoneNumber(phoneNumber);
        this.diaryUserRepository.save(dU);
        return dU;
    }

    public SiteUser getUser(String username){
        Optional<SiteUser> diaryUser = this.diaryUserRepository.findByUsername(username);
        if(diaryUser.isPresent()){
            return diaryUser.get();
        }else{
            throw new DataNotFoundException("데이터 없음");
        }

    }


}

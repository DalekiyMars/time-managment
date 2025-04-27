package com.time.managment.service;

import com.time.managment.exceptions.SomethingWentWrong;
import com.time.managment.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtUtil jwtUtil;

    @Value("${security.jwt.subject}")
    private String subject;

    public String serviceAuthorized(String subjName){
        if (StringUtils.equals(subjName, subject))
            return jwtUtil.generateToken(subject);

        throw new SomethingWentWrong("UnAuthotized service");
    }
}

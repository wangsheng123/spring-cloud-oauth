package com.ws.icloud.user.service;

import com.ws.icloud.common.exception.service.ServiceException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public void testException() {
        throw new ServiceException("业务异常");
    }
}

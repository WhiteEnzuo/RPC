package Service.impl;

import Service.Arr;
import Service.UserService;
import lombok.Data;

/**
 * @Classname UserServiceImpl
 * @Description
 * @Version 1.0.0
 * @Date 2023/1/6 17:36
 * @Created by Enzuo
 */
@Data
public class UserServiceImpl implements UserService  {
    private String args;
    @Override
    public Arr say(String name) {
        return  new Arr();
    }
}



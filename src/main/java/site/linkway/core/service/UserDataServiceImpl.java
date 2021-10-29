package site.linkway.core.service;

import site.linkway.core.dao.UserDao;

import java.util.List;

public class UserDataServiceImpl implements UserDataService {

    @Override
    public boolean checkRight(String id, String password) {
        UserDao.User user=new UserDao.User();
        user.stuId=id;
        UserDao.User user1=UserDao.selectUserById(user);
        if(null==user1.stuId){return false;}
        if(null==user1.password){return false;}
        if(false==user1.password.equals(password)){
            return false;
        }
        return true;
    }
}

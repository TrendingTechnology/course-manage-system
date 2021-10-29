package site.linkway.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import site.linkway.core.dao.UserDao;
import site.linkway.core.json.StatusResult;
import site.linkway.core.service.UserDataService;

import java.util.List;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class AccountSecurityController {
    static Logger logger= Logger.getLogger(AccountSecurityController.class);
    @Autowired
    @Qualifier("UserDataServiceImpl")
    private UserDataService userDataService;

    /*
    * Login API    /login?id=XXX&password=XXX
    * */
    private ObjectMapper mapper=new ObjectMapper();

    @RequestMapping(value = "/login",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String login(@NonNull String id, @NonNull String password, HttpSession httpSession) throws Exception{
        System.out.println(id+password);
        String session_id=(String)httpSession.getAttribute("id");
        if(session_id!=null){
            StatusResult statusResult=new StatusResult();
            statusResult.setStatus(200);statusResult.setResult(true);
            return mapper.writeValueAsString(statusResult);
        }
        boolean right=userDataService.checkRight(id,password);
        if(right){
            httpSession.setAttribute("id",id);
        }
        StatusResult statusResult=new StatusResult();
        statusResult.setStatus(200);statusResult.setResult(right);
        return mapper.writeValueAsString(statusResult);
    }

    @RequestMapping(value = "/logined/changePassword",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String changePassword(@NonNull String newPassword,HttpSession httpSession) throws Exception{
        String session_id=(String)httpSession.getAttribute("id");
        UserDao.User user=new UserDao.User();
        user.stuId=session_id;
        UserDao.User findedUser=UserDao.selectUserById(user);
        findedUser.password=newPassword;
        boolean result=UserDao.update(findedUser);
        StatusResult statusResult=new StatusResult();
        statusResult.setStatus(200);statusResult.setResult(result);
        return mapper.writeValueAsString(statusResult);
    }

}

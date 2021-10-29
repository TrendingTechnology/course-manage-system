package site.linkway.core.dao;

import org.apache.hadoop.hbase.client.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public static class User{
        @Override
        public String toString() {
            return "User{" +
                    "stuId='" + stuId + '\'' +
                    ", name='" + name + '\'' +
                    ", sex='" + sex + '\'' +
                    ", age='" + age + '\'' +
                    ", department='" + department + '\'' +
                    ", major='" + major + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
        public String stuId;
        public String name;
        public String sex;
        public String age;
        public String department;
        public String major;
        public String password;
    }

    public static final String TABLENAME=HbaseUtil.tableNames[0];
    public static boolean addStudent(User user)  {
        boolean result=false;
        try{
            if(null!=selectUserById(user).stuId){//exist
                return false;
            }
            System.out.println("execute insert");
            HbaseUtil.addRecord(TABLENAME,user.stuId,HbaseUtil.studentStruct,new String[]{
                    user.stuId,
                    user.name,
                    user.sex,
                    user.age,
                    user.department,
                    user.major,
                    user.password
            });
            result=true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }
    public static User selectUserById(User user){
        User back=new User();
        try{
            Result result=HbaseUtil.getData(TABLENAME,user.stuId);
            if(null!=result){
                back.password= new String(result.getValue("Studies".getBytes(),"password".getBytes()));
                back.major=new String(result.getValue("Studies".getBytes(),"major".getBytes()));
                back.sex=new String(result.getValue("SInfo".getBytes(),"gender".getBytes()));
                back.department=new String(result.getValue("Studies".getBytes(),"department".getBytes()));
                back.name=new String(result.getValue("SInfo".getBytes(),"name".getBytes()));
                back.stuId=new String(result.getValue("SInfo".getBytes(),"id".getBytes()));
                back.age=new String(result.getValue("SInfo".getBytes(),"age".getBytes()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return back;
        }
    }

    public static boolean update(User user){
        boolean result=false;
        try{
            if(null==selectUserById(user).stuId){//no exist
                System.out.println("update filed no exist");
                return false;
            }
            System.out.println("execute update");
            HbaseUtil.addRecord(TABLENAME,user.stuId,HbaseUtil.studentStruct,new String[]{
                    user.stuId,
                    user.name,
                    user.sex,
                    user.age,
                    user.department,
                    user.major,
                    user.password
            });
            result=true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    public static boolean delete(User user){
        boolean result=false;
        try{
            if(null==selectUserById(user).stuId){//no exist
                System.out.println("delete filed no exist");
                return false;
            }
            System.out.println("execute delete");
            HbaseUtil.deleteRow(TABLENAME,user.stuId);
            result=true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    public static List<User> userListFromResultList(List<Result> results){
        List<User> users=new ArrayList<>();
        for(Result result:results){
            if(null!=result){
                User user=new User();
                user.password= new String(result.getValue("Studies".getBytes(),"password".getBytes()));
                user.major=new String(result.getValue("Studies".getBytes(),"major".getBytes()));
                user.sex=new String(result.getValue("SInfo".getBytes(),"gender".getBytes()));
                user.department=new String(result.getValue("Studies".getBytes(),"department".getBytes()));
                user.name=new String(result.getValue("SInfo".getBytes(),"name".getBytes()));
                user.stuId=new String(result.getValue("SInfo".getBytes(),"id".getBytes()));
                user.age=new String(result.getValue("SInfo".getBytes(),"age".getBytes()));
                users.add(user);
            }
        }
        return users;
    }


    /*
    * Dao Testing
    * */
    public static void main(String[] args) {
        User user=new User();
        user.age="12";
        user.stuId="1901420313";
        user.name="gaowanlu";
        user.sex="man";
        user.password="123456";
        user.department="jisuanji";
        user.major="ruanjiangongcheng";
        if(addStudent(user)){
            System.out.println("insert sucuss");
        }else{
            System.out.println("insert failed");
        }

        //update
        user.department="123456";
        update(user);

        //delete
        //delete(user);

        //select
        User user1=new User();
        user.stuId="1901420313";
        User user2=selectUserById(user);
        System.out.println(user2);

        //selectall
        try{
            List<User> users=userListFromResultList(HbaseUtil.scan(TABLENAME));
            for(User user3:users){
                System.out.println(user3);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

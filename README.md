# CourseManageService
> Spring、SpringMVC + Hasdoop HBase Course Manage System  

## Quick Start
#### 1
```shell
change your hBase service adress:site.linkway.core.dao.HbaseUtil  

    public static void initConnection () {
        configuration  = HBaseConfiguration.create();
        configuration.set("hbase.master", `"127.0.0.1:16000"`);
        try{
            System.out.println("---------------------尝试连接");
            connection = ConnectionFactory.createConnection(configuration);
            admin = connection.getAdmin();
            System.out.println("---------------------连接成功");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

```
#### 2
Aut System Admin Change:
change dao.AdminDao in and password   
#### 3
execute dao.UserDao.main() for init Admin User in HBase Table
#### 4  
execute HbaseUtil.main() method to init HBase tables  
using maven build this project  and run on tomcat server  
#### 5
webUI install   
you should install npm and nodejs  
```shell
 cd ./UIProject  
 npm install  
 npm run build 
 cd ./build  
 sudo mv ./* ../../src/main/webapp/
``` 
#### 6 
build javaEE project and run on server,or using the Network Request API

## Network Request API  

### User
#### login
>/login?id={}&password={}  
#### change password
>/logined/changePassword?newPassword={}
#### view all courses
>/logined/getCourseList  
#### stuent choose target course 
>/logined/chooseCourse?courseId={} 
#### student get choosed course
>/logined/getMyCourse
#### student delete back course
>/logined/deleteMyCourse?courseId={}  
#### student get self data    
>/logined/getMyData

### Admin
> contain apis of User 
#### add new student
>/admin/addStudent?id={}&password={}&sex={}&name={}&age={}&department={}&major={}  
#### add new course
>/admin/addCourse?courseId={}&name={}&credit={}&time={}&teacher={}&title={}  
#### delete old course
>/admin/deleteCourse?courseId={}
#### delete student
>/admin/deleteStudent?id={}  
#### view all student
>/admin/getStudentList
#### update score of student
>/admin/updateSC?courseId={}&userid={}&score={}
#### view student-course table all
>/admin/getSCList 


## About This Project 
> It's just my course work of 'BigData Tech' 

## Concat me
#### email:2209120827@qq.com  

## Thank you!!
welcome fork ,pull request , issue , give me a star


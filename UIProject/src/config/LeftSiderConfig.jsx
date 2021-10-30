import {
  ReadOutlined,
  SnippetsOutlined,
  AppstoreOutlined
} from "@ant-design/icons";
import IndexPage from "../page/IndexPage";
import LoginPage from "../page/LoginPage";
import BrowseCourses from './../page/BrowseCourses';
import MyCourses from './../page/MyCourses';
import AddStudent from './../page/AddStudent';
import CourseManage from './../page/CourseManage';
import ChangePassword from './../page/ChangePassword';
import ScoreManage from './../page/ScoreManage';
import UserData from './../page/UserData';
const mapper = [
    {
      key: "选课",
      title: "选课",
      icon:ReadOutlined,
      children: [{ key: "浏览课程", content: "浏览课程", url: "/xuanke/liulan",page:<BrowseCourses/>}],
    },
    {
      key: "已选课程",
      title: "已选课程",
      icon:SnippetsOutlined,
      children: [
        { key: "查看已选", content: "查看已选", url: "/yixuankecheng/chakanyiyuan",page:<MyCourses/> },
      ],
    },
    {
      key: "管理",
      title: "管理",
      icon:AppstoreOutlined,
      children: [
        {
          key: "学生账号管理",
          content: "学生账号管理",
          url: "/guanli/tianjiaxueshengzhanghao",
          page:<AddStudent/>
        },
        { key: "课程管理", content: "课程管理", url: "/guanli/kechengguanli",page:<CourseManage/> },
        { key: "成绩管理", content: "成绩管理", url: "/guanli/chengjiguanli",page:<ScoreManage/> },
      ],
    },
    {
      key: "个人信息",
      title: "个人信息",
      icon:ReadOutlined,
      children: [
        { key: "个人信息", content: "我的信息", url: "/gerenxinxi/userdata",page:<UserData/> },
        { key: "修改密码", content: "修改密码", url: "/gerenxinxi/xiugaimima",page:<ChangePassword/> },
      ],
    },
    {
      key: "登录",
      title: "登录",
      icon:ReadOutlined,
      children: [{ key: "登录账号", content: "登陆账号", url: "/loginPage",page:<LoginPage/>}],
    },
  ];

const LeftSiderConfig={
    mapper,
    fromUrlGetObj(url){
      if(url===undefined||url===""||url==="/"){return null;}
      for(let i=0;i<mapper.length;i++){
        for(let j=0;j<mapper[i].children.length;j++){
          if(mapper[i].children[j].url===url){
            return mapper[i].children[j];
          }
        }
      }
      return null;
    },
    fromUrlGetPath(url){
      if(url===undefined||url===""||url==="/"){return [mapper[4].key,mapper[4].children[0].key];}
      let returnArray=[];
      for(let i=0;i<mapper.length;i++){
        returnArray.push(mapper[i].key);
        for(let j=0;j<mapper[i].children.length;j++){
          if(mapper[i].children[j].url===url){
            returnArray.push(mapper[i].children[j].key);
            return returnArray;
          }
        }
        returnArray.pop();
      }
      return returnArray;
    },
    fromUrlGetPage(url){
      console.log(url);
      let page=this.fromUrlGetObj(url);
      if(null===page){
        return <LoginPage/>
      }
      return page.page;
    }
};

export default LeftSiderConfig;

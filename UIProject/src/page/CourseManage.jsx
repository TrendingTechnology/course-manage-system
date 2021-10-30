import React, { Component } from "react";
import styled from "styled-components";
import {
  Table,
} from "antd";
import axios from "axios";
import columns from '../config/CoursesTableConfig';
import { Select, message } from 'antd';
import AddCourseForm from "../component/AddCourseForm";
const { Option } = Select;

const Container = styled.div`
  width: 100%;
`;


export default class CourseManage extends Component {
  state = {
    data:[],
    loading:true
  };

  handleChange=(value)=>{
    console.log(`selected ${value}`);
  }
  getData=async()=>{
    let rp=await axios("/logined/getCourseList");
    if(rp.status!==302&&rp.data.status===200){
      this.setState({data:rp.data.courses,loading:false});
    }else{
      History.history.push("/loginPage");
    }
  }
  deleteCourseEvent=(record)=>{
    let deleteCourse=async ()=>{
      let rp=await axios.get("/admin/deleteCourse?courseId="+record.courseId);
      if(rp.data.result===true){
        message.success("删除课程成功");
        let data=[...this.state.data];
        data=data.filter(v=>v.courseId!==record.courseId);
        this.setState({data});
      }else{
        message.success("删除课程失败");
      }
    }
    deleteCourse();
  }

  componentDidMount(){
    this.getData();
  }

  submitCallback=(data)=>{
      (async()=>{
        let {id,name,credit,dateStart,dateEnd,teacher,title}=data;
        let time=dateStart+"~"+dateEnd;
        let rp=await axios.get(
            `/admin/addCourse?courseId=${id}&name=${name}&credit=${credit}&time=${time}&teacher=${teacher}&title=${title}`
        );
        if(rp.data.result===true){
            message.success("添加课程成功");
            let listdata=[...this.state.data];
            listdata.push(data);
            this.setState({data:listdata});
        }else{
            message.error("添加课程失败");
        }
      })();
  }

  render() {
    return (
      <Container>
        <Table
          loading={this.state.loading}
          pagination={{ pageSize: 10 }}
          scroll={{ y: 440 }}
          columns={columns(this.deleteCourseEvent,'删除',false)}
          dataSource={this.state.data}
        />
        <AddCourseForm submitCallback={this.submitCallback}/>
      </Container>
    );
  }
}

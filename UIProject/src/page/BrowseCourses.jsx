import React, { Component } from 'react'
import styled from 'styled-components';
import { Table, Tag, Space, Button, Switch, message } from 'antd';
import axios from 'axios';
import columns from '../config/CoursesTableConfig';
import {History} from '../App';
const Container=styled.div`
    width:100%;
`;

export default class BrowseCourses extends Component {
    state={
        data:[],
        loading:true
    }
    getData=async()=>{
      let rp=await axios("/logined/getCourseList");
      if(rp.status!==302&&rp.data.status===200){
        this.setState({data:rp.data.courses,loading:false});
      }else{
        History.history.push("/loginPage");
      }
    }
    componentDidMount(){
      this.getData();
    }
    chooseCourseEvent=(record)=>{
      console.log(record.courseId);
      //发起选课请求
      let chooseCourse=async()=>{
        let rp=await axios("/logined/chooseCourse?courseId="+record.courseId);
        if(rp.status===200&&rp.data.result===true){
          message.success("选课成功");
          let {data}=this.state;
          let newdata=data.filter(v=>v.courseId!==record.courseId);
          this.setState({data:newdata});
        }else{
          message.error("选课出错:您已选过此课程");
        }
      }
      chooseCourse();
    }
    render() {
        return (
            <Container>
                <Table pagination={{ pageSize: 10 }}
                loading={this.state.loading}
                 scroll={{ y: 440 }}
                columns={columns(this.chooseCourseEvent,'选课',false)}
                dataSource={this.state.data} 
                />
            </Container>
        )
    }
}


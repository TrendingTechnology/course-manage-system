import React, { Component } from 'react'
import styled from 'styled-components';
import { Table, Tag, Space, Button, Switch, message } from 'antd';
import columns from '../config/CoursesTableConfig';
import axios from 'axios';

const Container=styled.div`
    width:100%;
`;
export default class MyCourses extends Component {
    state={
        data:[],
        loading:true
    }
    componentDidMount(){
      let getData=async()=>{
        let rp=await axios("/logined/getMyCourse");
        this.setState({data:rp.data.courses,loading:false});
      }
      getData();
    }
    deleteCourseEvent=(record)=>{
      console.log(record);
      let deleteCourse=async()=>{
        let rp=await axios("/logined/deleteMyCourse?courseId="+record.courseId);
        if(rp.data.result===true){
          message.success("退课成功");
          let {data}=this.state;
          let newdata=data.filter(v=>v.courseId!==record.courseId);
          this.setState({data:newdata});
        }else{
          message.error("退课失败");
        }
      }
      deleteCourse();
    }
    render() {
        return (
            <Container>
                <Table pagination={{ pageSize: 10 }}
                 loading={this.state.loading}
                 scroll={{ y: 440 }}
                 columns={columns(this.deleteCourseEvent,'退课',true)}
                 dataSource={this.state.data} 
                />
            </Container>
        )
    }
}


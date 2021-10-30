import React, { Component } from 'react'
import styled from 'styled-components';
import { Table, Tag, Space, Button, Switch, Card, Input, message } from 'antd';
import columns from '../config/StudentTableConfig';
import axios from 'axios';
import AddStudentForm from '../component/AddStudentForm';
const Container=styled.div`
    width:100%;
`;
export default class AddStudent extends Component {
    state={
        data:[],
        loading:true
    }
    deleteEvent=(record)=>{
      let deleteStudent=async()=>{
        let rp=await axios("/admin/deleteStudent?id="+record.stuId);
        if(rp.data.result===true){
          message.success("删除学生成功");
          let data=[...this.state.data];
          let newData=data.filter(v=>v.stuId!==record.stuId);
          this.setState({data:newData});
        }else{
          message.error("删除失败");
        }
      }
      deleteStudent();
    }
    componentDidMount(){
      let getData=async()=>{
        let rp=await axios("/admin/getStudentList");
        console.log(rp.data.students);
        this.setState({data:rp.data.students,loading:false});
      }
      getData();
    }
    addStudentEvent=(data)=>{
      /*age: ""
        department: ""
        id: ""
        major: ""
        name: ""
        password: ""
        sex: ""*/
      (async ()=>{
        let {id,password,sex,name,department,major,age}=data;
        let rp=await axios.get(`/admin/addStudent?id=${id}&password=${password}&sex=${sex}&name=${name}&age=${age}&department=${department}&major=${major}`);
        if(rp.data.result===true){
          message.success("添加成功");
          let data=[...this.state.data];
          data.push({
            age,
            department,
            major,
            name,
            password,
            sex,
            stuId: id
          });
          console.log(data);
          this.setState({data});
        }else{
          message.error("添加失败:请检查输入或学号已存在");
        }
      })();
    }
    render() {
        return (
            <Container>
                <Table pagination={{ pageSize: 10 }}
                 scroll={{ y: 440 }} 
                 loading={this.state.loading}
                 columns={columns(this.deleteEvent)} 
                 dataSource={this.state.data} />
                <AddStudentForm addStudentEvent={this.addStudentEvent}/>
            </Container>
        )
    }
}


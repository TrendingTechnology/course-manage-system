import React, { Component } from 'react'
import {
    Button,
    Card,
    Radio,
    Input,
    DatePicker
  } from "antd";
import { Select, message } from 'antd';
import { axios } from 'axios';
export default class AddCourseForm extends Component {
    state={
        name:'',
        id:'',
        credit:'',
        teacher:'',
        title:'线下',
        dateStart:'',
        dateEnd:''
    }
    dateChange=(e)=>{
        let dateStart=e[0]._d.toString().split(' ');
        let dateEnd=e[1]._d.toString().split(' ');
        dateStart=dateStart[3]+"-"+dateStart[2]+"-"+dateStart[1];
        dateEnd=dateEnd[3]+"-"+dateEnd[2]+"-"+dateEnd[1];
        this.setState({
            dateStart,
            dateEnd
        });
    }
    Submit=()=>{
        let {id,name,credit,dateStart,dateEnd,teacher,title}=this.state;
        this.props.submitCallback({id,name,credit,dateStart,dateEnd,teacher,title});
    }
    render() {
        return (
        <Card title="新增课程信息">
            <Input placeholder="课程名称" onChange={(e)=>{this.setState({name:e.target.value})}} style={{ marginTop: "20px" }}></Input>
            <Input placeholder="课程编号" onChange={(e)=>{this.setState({id:e.target.value})}} style={{ marginTop: "20px" }}></Input>
            <Input placeholder="学分" onChange={(e)=>{this.setState({credit:e.target.value})}}  style={{ marginTop: "20px" }}></Input>
            <Input placeholder="教师" onChange={(e)=>{this.setState({teacher:e.target.value})}}  style={{ marginTop: "20px",marginBottom:'20px' }}></Input>
            <div style={{ marginTop: "20px" }}></div>
            <Radio.Group onChange={(e)=>{this.setState({title:e.target.value})}} defaultValue={this.state.title} buttonStyle="solid">
              <Radio.Button value="线上">线上</Radio.Button>
              <Radio.Button value="线下">线下</Radio.Button>
            </Radio.Group>
            <div style={{ marginTop: "20px" }}></div>
            <DatePicker.RangePicker onChange={this.dateChange}/>
            <p style={{ textAlign: "right", marginTop: "20px" }}>
              <Button onClick={this.Submit} type="primary">确定</Button>
            </p>
        </Card>
        )
    }
}

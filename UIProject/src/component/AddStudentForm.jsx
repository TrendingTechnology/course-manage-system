import React, { Component,useState } from 'react';
import { Card,Input,Button } from 'antd';
function AddStudentForm (props){
    let [name,setName]=useState('');
    let [id,setId]=useState('');
    let [sex,setSex]=useState('');
    let [password,setPassword]=useState('');
    let [major,setMajor]=useState('');
    let [dep,setDep]=useState('');
    let [age,setAge]=useState('');
    let Submit=()=>{
      console.log("添加学生");
       props.addStudentEvent({
           id:id,
           password,
           sex,
           name,
           age,
           department:dep,
           major
       });
    }
    return (
        <Card title="新增学生信息"> 
        <Input placeholder="姓名" onChange={(e)=>setName(e.target.value)}  style={{marginTop:'20px'}}></Input>
        <Input placeholder="学号" onChange={(e)=>setId(e.target.value)}  style={{marginTop:'20px'}}></Input>
        <Input placeholder="性别" onChange={(e)=>setSex(e.target.value)}  style={{marginTop:'20px'}}></Input>
        <Input placeholder="密码" onChange={(e)=>setPassword(e.target.value)}  style={{marginTop:'20px'}}></Input>
        <Input placeholder="专业" onChange={(e)=>setMajor(e.target.value)}  style={{marginTop:'20px'}}></Input>
        <Input placeholder="学院" onChange={(e)=>setDep(e.target.value)}  style={{marginTop:'20px'}}></Input>
        <Input placeholder="年龄" onChange={(e)=>setAge(e.target.value)}  style={{marginTop:'20px'}}></Input>
        <p style={{textAlign:'right',marginTop:'20px'}}>
          <Button onClick={Submit} type="primary">确定</Button>                    
        </p>
      </Card>
    );
}

export default AddStudentForm;
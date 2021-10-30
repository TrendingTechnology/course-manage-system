import React, { Component,useState } from 'react';
import { Card, Input, Button, message } from 'antd';
export default function UpdateScoreForm(props) {
    let [studentId,setStudentId]=useState('');
    let [courseId,setCourseId]=useState('');
    let [score,setScore]=useState('');
    let Submit=()=>{
       console.log("修改成绩");
       if(studentId===''||courseId===''||score===''){
          message.error("输入不能为空");
          return;
       }
       props.updateEvent({
           studentId,
           courseId,
           score
       });
    }
    return (
        <Card title="成绩更新或添加"> 
        <Input placeholder="学号" onChange={(e)=>setStudentId(e.target.value)}  style={{marginTop:'20px'}}></Input>
        <Input placeholder="课号" onChange={(e)=>setCourseId(e.target.value)}  style={{marginTop:'20px'}}></Input>
        <Input placeholder="成绩" onChange={(e)=>setScore(e.target.value)}  style={{marginTop:'20px'}}></Input>
        <p style={{textAlign:'right',marginTop:'20px'}}>
          <Button onClick={Submit} type="primary">确定</Button>                    
        </p>
      </Card>
    );
}

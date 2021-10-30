import { Card } from 'antd';
import React, { Component } from 'react'
import styled  from 'styled-components';
import { PageHeader,Divider } from 'antd';
import { data } from 'autoprefixer';
import axios  from 'axios';
const Container=styled.div`
    width:100%;
`;

export default class UserData extends Component {
    state={
        data:{
            name:'',
            stuId:'',
            sex:'',
            age:'',
            department:'',
            major:''
        }
    }
    componentDidMount(){
        (async()=>{
            let rp=await axios.get("/logined/getMyData");
            if(rp.status===200){
                if(rp.data!==null){
                    this.setState({data:rp.data});
                }
            }
        })();
    }
    render() {
        return (
            <Container>
                <Card>
                <PageHeader
                    title="学生"
                    subTitle="我的个人信息"/>
                <Divider orientation="left"></Divider>
                <Card>
                    <p>姓名:{this.state.data.name}</p>
                    <p>学号:{this.state.data.stuId}</p>
                    <p>性别:{this.state.data.sex}</p>
                    <p>年龄:{this.state.data.age}</p>
                    <p>学院:{this.state.data.department}</p>
                    <p>专业:{this.state.data.major}</p>
                </Card>
                </Card>
            </Container>
        )
    }
}

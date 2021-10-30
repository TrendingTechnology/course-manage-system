import React, { Component } from 'react'
import styled from 'styled-components';
import { Card,Form,Input,Button,Checkbox,message } from 'antd';
import axios from 'axios';
export default class LoginPage extends Component {
    state={
      username:'',
      password:''
    }
    submit=()=>{
        console.log(this.state.username+" "+this.state.password);
        let login=async ()=>{
          let rp = await axios("/login/?id="+this.state.username+"&password="+this.state.password);
          if(rp.data.status===200&&rp.data.result===true){
            message.success('登陆成功');            
          }else{
            message.error('账号或密码错误');
          }
        }
        login();
    }
    inputOnChange=(e)=>{
      let name=e.target.name;
      if(name==="username"){
        this.setState({username:e.target.value});
      }else if(name==="password"){
        this.setState({password:e.target.value});
      }
    }
    render() {
        return (
            <Container>
                <FormContainer title="登录">
                <Form
      name="basic"
      labelCol={{
        span: 8,
      }}
      wrapperCol={{
        span: 16,
      }}
      initialValues={{
        remember: true,
      }}
      onFinish={null}
      onFinishFailed={null}
      autoComplete="off"
    >
      <Form.Item
        label="学号(账号)"
        name="username"
        rules={[
          {
            required: true,
            message: 'Please input your username!',
          },
        ]}
      >
        <Input name="username" onChange={this.inputOnChange} maxLength={20}/>
      </Form.Item>

      <Form.Item
        label="密码"
        name="password"
        rules={[
          {
            required: true,
            message: 'Please input your password!',
          },
        ]}
      >
        <Input.Password name="password" onChange={this.inputOnChange}  maxLength={20}/>
      </Form.Item>
      

        <Container>
            <Button onClick={this.submit} type="primary" color="blue" size="middle">
              登录
            </Button>
        </Container>

    </Form>
                </FormContainer>
            </Container>
        )
    }
}

const Container=styled.div`
    width:100%;
    display:flex;
    justify-content:center;
`;

const FormContainer=styled(Card)`
    width:90vw;
    max-width:400px;
`;

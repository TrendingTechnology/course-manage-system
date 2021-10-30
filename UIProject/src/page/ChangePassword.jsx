import React, { Component } from "react";
import styled from "styled-components";
import { Card, Form, Input, Button,message } from "antd";
import axios from "axios";
export default class ChangePassword extends Component {
  state={
    password:'',
    repassword:''
  }
  Submit=()=>{
    let {password,repassword}=this.state;
    if(password===''||repassword===''){
      message.error("密码不能为空");
    }else if(password!==repassword){
      message.error('两次密码不相同');
    }else if(password.length<6){
      message.error("密码不能小于6位");
    }else{
      //submit
      (async ()=>{
        let rp=await axios.get("/logined/changePassword?newPassword="+password);
        if(rp.data.result===true){
          message.success("密码修改成功");
        }else{
          message.success("密码修改失败");
        }
      })();
    }
  }
  render() {
    return (
      <Container>
        <FormContainer title="修改密码">
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
              label="新密码"
              name="newpassword"
              rules={[
                {
                  required: true,
                  message: "Please input your password!",
                },
              ]}
            >
              <Input.Password onChange={(e)=>this.setState({password:e.target.value})} maxLength={20} />
            </Form.Item>
            <Form.Item
              label="重复新密码"
              name="renewpassword"
              rules={[
                {
                  required: true,
                  message: "Please input your password!",
                },
              ]}
            >
              <Input.Password  onChange={(e)=>this.setState({repassword:e.target.value})}  maxLength={20} />
            </Form.Item>
            <Container>
              <Button onClick={this.Submit} type="primary" size="middle">
                提交
              </Button>
            </Container>
          </Form>
        </FormContainer>
      </Container>
    );
  }
}

const Container = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
`;

const FormContainer = styled(Card)`
  width: 90vw;
  max-width: 400px;
  height: 500px;
`;

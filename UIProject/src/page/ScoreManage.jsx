import React, { Component } from 'react'
import styled from 'styled-components';
import { Table, Tag, Space, Button, Switch, Card, Input, message } from 'antd';
import columns from '../config/ScoreTableConfig';
import axios from 'axios';
import AddStudentForm from '../component/UpdateScoreForm';
import UpdateScoreForm from '../component/UpdateScoreForm';
const Container=styled.div`
    width:100%;
`;
class ScoreManage extends Component {
    state={
        loading:true,
        data:[]
    }
    componentDidMount(){
        let getData=async()=>{
          let rp=await axios("/admin/getSCList");
          console.log(rp.data.scs);
          this.setState({data:rp.data.scs,loading:false});
        }
        getData();
    }
    updateEvent=(data)=>{
        (async()=>{
            let rp=await axios.get(`/admin/updateSC?courseId=${data.courseId}&userid=${data.studentId}&score=${data.score}`);
            if(rp.status===200&&rp.data.result===true){
                message.success("成绩更新成功");
            }else{
                message.error("成绩更新失败");
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
                <UpdateScoreForm updateEvent={this.updateEvent}/>
            </Container>
        )
    }
}

export default ScoreManage;
import React, { Component } from 'react'
import { Layout, Menu, Breadcrumb } from 'antd';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { History } from '../App';
import LeftSiderConfig from './../config/LeftSiderConfig';
export default class ContentHeader extends Component {
    render() {
        let list=LeftSiderConfig.fromUrlGetPath(History.history.location.pathname);
        console.log(list);
        return (
            <React.Fragment>
                <Breadcrumb style={{ margin: '16px 0' }}>
                    {
                        list.map((val,index)=>{
                            return(
                            <Breadcrumb.Item key={index}>
                                <span>{val}</span>
                            </Breadcrumb.Item>
                            )
                        })
                    }
                </Breadcrumb>
            </React.Fragment>
        )
    }
}


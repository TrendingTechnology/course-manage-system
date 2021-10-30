import React, { Component } from "react";
import { Layout, Menu } from "antd";
import LeftSiderConfig from '../config/LeftSiderConfig';
import { History } from "../App";

const { SubMenu } = Menu;
const { Sider } = Layout;

export default class LeftSider extends Component {
  state = {
    collapsed: false,
  };
  onCollapse = (collapsed) => {
    this.setState({ collapsed });
  };
  chooseBlock = (e) => {
    History.history.replace(e.key);
  };
  render() {
    return (
      <Sider
        collapsible
        collapsed={this.state.collapsed}
        onCollapse={this.onCollapse}
      >
        <Menu
          theme="dark"
          mode="inline"
          // defaultSelectedKeys={[LeftSiderConfig.mapper[4].children[0].url]}
          // defaultOpenKeys={[LeftSiderConfig.mapper[4].key]}
          style={{ height: "100%", borderRight: 0 }}
        >
            {
                LeftSiderConfig.mapper.map(item=>{
                    let Icon=item.icon;
                    return (<SubMenu key={item.key} icon={<Icon/>} title={item.title}>
                        {item.children.map(child=>{
                            return(
                                <Menu.Item onClick={this.chooseBlock} key={child.url} name="ref">
                                {child.content}
                              </Menu.Item>
                            )
                        })}
                    </SubMenu>)
                })
            }
        </Menu>
      </Sider>
    );
  }
}

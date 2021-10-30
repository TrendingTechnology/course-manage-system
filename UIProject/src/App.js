// import logo from './logo.svg';
import './App.css';
import { Layout } from 'antd';
import styled from 'styled-components';
import ContentHeader from './component/ContentHeader';
import LeftSider from './component/LeftSider';
import { Route } from 'react-router';
import LeftSiderConfig from './config/LeftSiderConfig';
const { Header} = Layout;
const History={history:{}};

function App(props) {
  History.history=props.history;
  return (
    <Layout  style={{ minHeight: '100vh'}}>
    <Header className="header">
      <LogoContainer>Course</LogoContainer>
    </Header>
    <Layout>
      <LeftSider/>
      <Layout style={{ padding: '0 24px 24px' }}>
        <ContentHeader/>
        <ContentContainer>
            <Route path="/" render={props=>LeftSiderConfig.fromUrlGetPage(History.history.location.pathname)}></Route>
        </ContentContainer>
      </Layout>
    </Layout>
  </Layout>
  );
}

const LogoContainer=styled.p`
  height:100%;
  color:#fafafa;
  font-size:1.3rem;
`;
const ContentContainer=styled(Layout.Content)`
  padding: 24px;  
  margin: 0px;
  min-height: 280px;
`;

export default App;
export {History};

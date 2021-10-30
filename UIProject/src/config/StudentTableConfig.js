import { Table, Tag, Space,Button ,Switch} from 'antd';
// age: "12"
// department: "123456"
// major: "ruanjiangongcheng"
// name: "gaowanlu"
// password: "123456"
// sex: "man"
// stuId: "1901420313"

function columns(event){
return [
    {
        title: '姓名',
        dataIndex: 'name',
        key: 'name',
        render: text => <a>{text}</a>,
      },
      {
        title: '学号',
        dataIndex: 'stuId',
        key: 'stuId',
      },
      {
        title: '性别',
        dataIndex: 'sex',
        key: 'sex',
      },
      {
        title: '密码',
        dataIndex: 'password',
        key: 'password',
      },
      {
        title: '专业',
        dataIndex: 'major',
        key: 'major',
      },
      {
        title: '学院',
        dataIndex: 'department',
        key: 'department',
      },
      {
        title: '年龄',
        dataIndex: 'age',
        key: 'age',
      },
      {
        title: '操作',
        key: 'action',
        render: (text, record) => (
          <Space size="middle">
            <Button onClick={()=>event(record)}>删除</Button>
          </Space>
        ),
      },
]};
export default columns;
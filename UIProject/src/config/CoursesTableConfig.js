import { Table, Tag, Space,Button ,Switch} from 'antd';
// courseId: "12345"
// credit: "4343"
// name: "ccfdcd"
// teacher: "vfdvfd"
// time: "43434"
// title: "fvfdv"
function columns(chooseCourseEvent,actionName,score){
let back= [
    {
      title: '课程名称',
      dataIndex: 'name',
      key: 'name',
      render: text => <a>{text}</a>,
    },
    {
      title: '课程编号',
      dataIndex: 'courseId',
      key: 'courseId',
    },
    {
        title: '学分',
        dataIndex: 'credit',
        key: 'credit',
      },
    {
      title:'时间',
      dataIndex:'time',
      key:'time'
    },
    {
      title:'教师',
      dataIndex:'teacher',
      key:'teacher'
    },
    {
      title:'描述',
      dataIndex:'title',
      key:'title'
    },
  ];

  if(score===true){
    back.push(
      {
        title:'成绩',
        dataIndex:'score',
        key:'score'
      }
    )
  }
  back.push(
    {
      title: '操作',
      key: 'action',
      render: (text, record) => (
        <Space size="middle">
          <Button onClick={()=>chooseCourseEvent(record)}>{actionName}</Button>
        </Space>
      ),
    });
    return back;
};
  export default columns;
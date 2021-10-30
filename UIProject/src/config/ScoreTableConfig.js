import { Table, Tag, Space,Button ,Switch} from 'antd';
// >
// >/admin/getSCList 
//{courseId: "12313", studentId: "3423432", score: "0"}
function columns(event){
return [
    {
        title: '学号',
        dataIndex: 'studentId',
        key: 'studentId',
        render: text => <a>{text}</a>,
      },
      {
        title: '课号',
        dataIndex: 'courseId',
        key: 'courseId',
      },
      {
        title: '成绩',
        dataIndex: 'score',
        key: 'score',
      }
]};
export default columns;
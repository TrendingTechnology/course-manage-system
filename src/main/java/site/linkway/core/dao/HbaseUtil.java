package site.linkway.core.dao;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HbaseUtil {
    public static Configuration configuration;
    public static Connection connection;
    public static Admin admin;

    public static String[] studentStruct={"SInfo:id","SInfo:name","SInfo:gender","SInfo:age","Studies:department","Studies:major","Studies:password"};
    public static String[] courseStruct={"CInfo:id","CInfo:name","CInfo:credit","CInfo:time","Teaching:teacher","Teaching:title"};
    public static String[] scStruct={"CourseID:id","StudentID:id","Score:score"};
    public static String[] tableNames={"Student","Course","SC"};
    public static String[][] colFmailys={{"SInfo","Studies"},{"CInfo","Teaching"},{"CourseID","StudentID","Score"}};
    public static void main (String[] args) {
        try{
            initConnection();//建立连接
            System.out.println("Init Table ...");
            initTable();
            System.out.println("Init Data ...");
        }catch (Exception e) {
            e.printStackTrace();
        }finally {close();};//关闭连接
    }
    //first init table struct
    public static void initTable() throws Exception{
        if(null==connection){initConnection();}
        for(int i=0;i<tableNames.length;i++){
            createTable(tableNames[i],colFmailys[i]);
        }
    }

    //建立连接
    public static void initConnection () {
        configuration  = HBaseConfiguration.create();
        configuration.set("hbase.master", "127.0.0.1:16000");
        try{
            System.out.println("---------------------尝试连接");
            connection = ConnectionFactory.createConnection(configuration);
            admin = connection.getAdmin();
            System.out.println("---------------------连接成功");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //关闭连接
    public static void close () {
        try{
            if(admin != null) {
                admin.close();
            }
            if (null != connection) {
                connection.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //addRecord
    public static void addRecord(String tableName,String rowKey,String[]fields,String[]value) throws IOException {
        if(null==connection){initConnection();}
        for(int i=0;i< fields.length;i++){
            //"columnFamily:column"
            String temp=fields[i];
            int index=temp.indexOf(":");
            String columnFamily=new StringBuilder(temp).substring(0,index);
            String column=new StringBuilder(temp).substring(index+1,temp.length());
            insertData(tableName,rowKey,columnFamily,column,value[i]);
        }
    }
    //创建表
    public static void createTable (String myTableName,String[] colFamily) throws IOException {
        if(null==connection){initConnection();}
        TableName tableName = TableName.valueOf(myTableName);
        //if exist ,will delete it
        if(admin.tableExists(tableName)){
            System.out.println("talbe "+myTableName+" is exists!");
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
        }
        System.out.println("table "+myTableName+" retry create");
        TableDescriptorBuilder tableDescriptor = TableDescriptorBuilder.newBuilder(tableName);
        for(String str:colFamily){
            ColumnFamilyDescriptor family = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(str)).build();
            tableDescriptor.setColumnFamily(family);
        }
        admin.createTable(tableDescriptor.build());
    }
    //添加
    public static void insertData (String tableName,String rowKey,String colFamily,String col,String val) throws IOException {
        if(null==connection){initConnection();}
        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(rowKey.getBytes());
        put.addColumn(colFamily.getBytes(),col.getBytes(), val.getBytes());
        table.put(put);
        table.close();
    }
    //浏览
    public static Result getData(String tableName,String rowKey) throws IOException {
        if(null==connection){initConnection();}
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(rowKey.getBytes());
        Result result = table.get(get);
        table.close();
        return result;
    }
    public static void modifyData(String tableName,String rowKey,String column,String value) throws Exception{
        //get column family and column
        if(null==connection){initConnection();}
        String[]cols=column.split(":");
        insertData(tableName,rowKey,cols[0],cols[1],value);
    }
    public static void deleteRow(String tableName,String rowKey) throws Exception{
        //from tablestruct ,use column family and column ,delete all
        if(null==connection){initConnection();}
        Table table=connection.getTable(TableName.valueOf(tableName));
        Delete delete=new Delete(Bytes.toBytes(rowKey));
        table.delete(delete);
        table.close();
    }
    public static List<Result> scan(String tableName) throws Exception{
        if(null==connection){initConnection();}
        List<Result> results=new ArrayList<>();
        Table table=connection.getTable(TableName.valueOf(tableName));
        Scan scan=new Scan();
        ResultScanner scanner=table.getScanner(scan);
        for(Result result:scanner){
            results.add(result);
        }
        return results;
    }
}

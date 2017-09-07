package com.hdos.rsyygh.testsqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button btnreateDatabase;//创建数据库
    private Button btnupdateDatabase;//更新数据库
    private Button btnInsert;//杀入数据库
    private Button btnUpdate;//更新数据
    private Button btnQuery;//查询数据
    private Button btnDelete;//删除数据
    private Button btnQueryNum;//查询有多少条记录
    private TextView tvStartTime;//开始时间
    private TextView tvEndTime;//结束时间
    private TextView tvDstatTime;//删除开始时间
    private TextView tvDEndTime;//结束时间
    private TextView tvTotal;//总过多少条数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initEvetn();
    }

    private void initEvetn() {
        /**
         * 创建数据库
         */
        btnreateDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建StuDBHelper对象
                StuDBHelper dbHelper = new StuDBHelper(MainActivity.this,"stu_db",null,1);
                //得到一个可读的SQLiteDatabase对象
                SQLiteDatabase db =dbHelper.getReadableDatabase();
            }
        });

        /**
         * 插入数据库
         */
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StuDBHelper dbHelper = new StuDBHelper(MainActivity.this,"stu_db",null,1);
                //得到一个可写的数据库
                SQLiteDatabase db =dbHelper.getWritableDatabase();
                Calendar now = Calendar.getInstance();
                System.out.println("开始时间：" + now.getTimeInMillis());
                tvStartTime.setText("插入数据开始时间"+now.getTimeInMillis());
                for(int i=0;i<5;i++){
                    //生成ContentValues对象 //key:列名，value:想插入的值
                    ContentValues cv = new ContentValues();
                    //往ContentValues对象存放数据，键-值对模式
                    cv.put("id", 1);
                    cv.put("sname", "xiaoming"+i);
                    cv.put("sage", 21+i);
                    cv.put("ssex", "male"+i);
                    cv.put("num",i);
                    //调用insert方法，将数据插入数据库
                    db.insert("stu_table", null, cv);
                }
                Calendar now1 = Calendar.getInstance();
                System.out.println("结束时间：" + now1.getTimeInMillis());
                tvEndTime.setText("插入数据结束时间：" + now1.getTimeInMillis());
                    //关闭数据库
                db.close();
            }
        });


        /**
         * 查询有多少条记录
         */

        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StuDBHelper dbHelper = new StuDBHelper(MainActivity.this,"stu_db",null,1);
                //得到一个可写的数据库
                SQLiteDatabase db =dbHelper.getReadableDatabase();
                //参数1：表名
                //参数2：要想显示的列
                //参数3：where子句
                //参数4：where子句对应的条件值
                //参数5：分组方式
                //参数6：having条件
                //参数7：排序方式
                Cursor cursor = db.query("stu_table", new String[]{"id","sname","sage","ssex"}, "id=?", new String[]{"1"}, null, null, null);
                while(cursor.moveToNext()){
                    String name = cursor.getString(cursor.getColumnIndex("sname"));
                    String age = cursor.getString(cursor.getColumnIndex("sage"));
                    String sex = cursor.getString(cursor.getColumnIndex("ssex"));
                    System.out.println("query------->" + "姓名："+name+" "+"年龄："+age+" "+"性别："+sex);
                }
                //关闭数据库
                db.close();
            }
        });

        /**
         * 查看数据库的总条数
         */
        btnQueryNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StuDBHelper dbHelper = new StuDBHelper(MainActivity.this,"stu_db",null,1);
                //得到一个可写的数据库
                SQLiteDatabase db =dbHelper.getReadableDatabase();
                //参数1：表名
                //参数2：要想显示的列
                //参数3：where子句
                //参数4：where子句对应的条件值
                //参数5：分组方式
                //参数6：having条件
                //参数7：排序方式
                String sql = "select count(*) from stu_table";
                Cursor cursor = db.rawQuery(sql, null);
                cursor.moveToFirst();
                long count = cursor.getLong(0);
                System.out.println("有多少条记录" +count);
                tvTotal.setText("有多少条记录" +count);
                cursor.close();

            }
        });


        /**
         * 删除数据
         */
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StuDBHelper dbHelper = new StuDBHelper(MainActivity.this,"stu_db",null,1);
                //得到一个可写的数据库
                SQLiteDatabase db =dbHelper.getReadableDatabase();
                Calendar now = Calendar.getInstance();
                System.out.println("开始时间：" + now.getTimeInMillis());
                tvDstatTime.setText("开始时间：" + now.getTimeInMillis());
                //for(int i=0;i<10000;i++){
                String whereClauses = "id=?";
                String [] whereArgs = {String.valueOf(1)};
                //调用delete方法，删除数据
                db.delete("stu_table", whereClauses, whereArgs);
                //}
                Calendar now1 = Calendar.getInstance();
                System.out.println("结束时间：" + now1.getTimeInMillis());
                tvDEndTime.setText("结束时间：" + now1.getTimeInMillis());

            }
        });

        /**
         * 更新数据库
         */
        btnupdateDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 数据库版本的更新,由原来的1变为2
                StuDBHelper dbHelper = new StuDBHelper(MainActivity.this,"stu_db",null,2);
                SQLiteDatabase db =dbHelper.getReadableDatabase();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StuDBHelper dbHelper = new StuDBHelper(MainActivity.this,"stu_db",null,1);
                //得到一个可写的数据库
                SQLiteDatabase db =dbHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("sage", "23");
                //where 子句 "?"是占位符号，对应后面的"1",
                String whereClause="id=?";
                String [] whereArgs = {String.valueOf(1)};
                //参数1 是要更新的表名
                //参数2 是一个ContentValeus对象
                //参数3 是where子句
                db.update("stu_table", cv, whereClause, whereArgs);
            }
        });

    }

    private void initData() {
        
    }

    private void initView() {
        btnreateDatabase = (Button) findViewById(R.id.createDatabase);
        btnupdateDatabase = (Button) findViewById(R.id.updateDatabase);
        btnInsert = (Button) findViewById(R.id.insert);
        btnUpdate = (Button) findViewById(R.id.update);
        btnQuery = (Button) findViewById(R.id.query);
        btnDelete = (Button) findViewById(R.id.delete);
        btnQueryNum = (Button) findViewById(R.id.query_num);

        tvStartTime = (TextView) findViewById(R.id.tv_start_insert);
        tvEndTime = (TextView) findViewById(R.id.tv_end_insert);
        tvDstatTime = (TextView) findViewById(R.id.tv_start_delet);
        tvDEndTime = (TextView) findViewById(R.id.tv_end_delete);
        tvTotal = (TextView) findViewById(R.id.total);
        
    }
}

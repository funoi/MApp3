package com.funoi.sevice;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.funoi.model.Student;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentDataBaseService {
    // 创建对person表的服务类
    private DBHelper dbHelper;

    public StudentDataBaseService(Context context) {
        this.dbHelper = new DBHelper(context);
    }


    /**
     * @param s 传递一个学生
     */
    public void saveStudent(Student s) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();  // 写入的方式打开数据库


        if (find(s.getId()) == null) {
            // 创建ContentValue对象
            ContentValues values = new ContentValues();
            // 将插入的参数放置在values对象中
            values.put("id", s.getId());
            values.put("name", s.getName());
            values.put("age", s.getAge());
            values.put("sex", s.getSex());
            values.put("edu", s.getEdu());
            values.put("hobby", s.getHobby().toString());


            // 调用db操作对象插入contentValues对象
            db.insert("student", null, values);
        }
    }

    //修改操作

    public void updateStudent(Student s) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", s.getName());
        values.put("age", s.getAge());
        values.put("sex", s.getSex());
        values.put("edu", s.getEdu());
        values.put("hobby", s.getHobby().toString());

        // 注意执行语句的书写
        db.update("student", values, "id=?", new String[]{String.valueOf(s.getId())});
    }

    public void deleteStudent(Integer id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("student", "id=?", new String[]{id.toString()});

    }

    public Student find(Integer id_) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // 面向对象的查询
        @SuppressLint("Recycle") Cursor cursor = db.query("student", new String[]{"id", "name", "age", "sex", "edu", "hobby"},
                "id=?", new String[]{id_.toString()}, null, null, null);

        if (cursor.moveToFirst()) { // 只能出现一个记录
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") int age = cursor.getInt(cursor.getColumnIndex("age"));
            @SuppressLint("Range") String sex = cursor.getString(cursor.getColumnIndex("sex"));
            @SuppressLint("Range") String edu = cursor.getString(cursor.getColumnIndex("edu"));
            @SuppressLint("Range") String hobbies = StringUtils.strip(cursor.getString(cursor.getColumnIndex("hobby")), "[]");
            List<String> hobby = Collections.singletonList(hobbies);

            return new Student(id, name, age, sex, edu, hobby);
        }
        return null;
    }

    // 查询分页
    public List<Student> findAll(Integer offset, Integer maxResult) { // 分页的技巧
        List<Student> ps = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor = db.query("student", null, null, null, null, null, null, offset + " , " + maxResult);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") int age = cursor.getInt(cursor.getColumnIndex("age"));
            @SuppressLint("Range") String sex = cursor.getString(cursor.getColumnIndex("sex"));
            @SuppressLint("Range") String edu = cursor.getString(cursor.getColumnIndex("edu"));
            @SuppressLint("Range") String hobbies = StringUtils.strip(cursor.getString(cursor.getColumnIndex("hobby")), "[]");
            List<String> hobby = Collections.singletonList(hobbies);

            ps.add(new Student(id, name, age, sex, edu, hobby));
        }
        return ps;
    }

    // 聚合函数的使用
    public Integer getCount() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query("student", new String[]{"count(*)"}, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor.getInt(0);

    }

}

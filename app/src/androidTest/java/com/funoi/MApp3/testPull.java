package com.funoi.myapp2;

import android.content.Context;
import android.util.Log;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.funoi.model.Student;
import com.funoi.sevice.Pull;
import com.funoi.sevice.StudentDataBaseService;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)

public class testPull {
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private static  final String  Tag="XmlTest";

    @Test
    public void testPullParse() throws Throwable{
        InputStream is=getClass().getClassLoader().getResourceAsStream("test.xml");
        List<Student> students= Pull.getStudents(is);
        for(Student student:students){
            Log.i(Tag,student.toString());
        }
    }

    @Test
    public void testPullSave() throws Exception{
        List<Student> students= new ArrayList<>();
        List<String> hobby=new ArrayList<>();
        hobby.add("c++");
        hobby.add("python");
        students.add(new Student(1, "芙宁娜", 18,"女","大学",hobby));
//        Log.i("student",students.toString());
        hobby=new ArrayList<>();
        hobby.add("c++");
        hobby.add("python");
        hobby.add("java");
        students.add(new Student(2, "泡芙", 17,"女","高中",hobby));
//        Log.i("student",students.toString());
        hobby=new ArrayList<>();
        hobby.add("c++");
        hobby.add("python");
        hobby.add("java");
        hobby.add("amd");
        students.add(new Student(3, "芙芙", 19,"女","初中",hobby));
        students.add(new Student(4, "小芙", 16,"女","小学",hobby));
//        Log.i("student",students.toString());
        //输出到手机
        File file=new File(appContext.getFilesDir(),"student.xml");

        FileOutputStream stream=appContext.openFileOutput("student.xml", Context.MODE_PRIVATE);
        BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(stream));

        //StringWriter writer=new StringWriter();
        Pull.saveXML(students, writer);
        Log.i(Tag, writer.toString());



    }

    @Test
    public void testSaveDB(){
        List<String> hobby=new ArrayList<>();
        hobby.add("python");
        hobby.add("c++");
        hobby.add("java");
        hobby.add("js");

        Student s=new Student(22, "fu", 16,"女","大学",hobby);
        StudentDataBaseService service=new StudentDataBaseService(appContext);
        service.saveStudent(s);

    }

    @Test
    public void testUpdateStudent() {
        List<String> hobby=new ArrayList<>();
        hobby.add("c++");
        hobby.add("utf-8");
        Student s = new Student(1, "小小", 18,"男","大学",hobby);
        StudentDataBaseService service = new StudentDataBaseService(appContext);
        service.updateStudent(s);
        List<Student> students=service.findAll(0,service.getCount());
        Log.i("test", String.valueOf(students));
    }

    @Test
    public void testDeleteStudent(){
        StudentDataBaseService service=new StudentDataBaseService(appContext);
        for (int i=1;i<=service.getCount();i++){
            service.deleteStudent(i);
        }
        List<Student> students=service.findAll(0,service.getCount());
        Log.i("test", String.valueOf(students));

    }

    @Test
    public void testFind(){
        StudentDataBaseService service=new StudentDataBaseService(appContext);
        Student s=service.find(1);
        Log.i("test", String.valueOf(s));
    }


    @Test
    public void testFindAll() {
        StudentDataBaseService service = new StudentDataBaseService(appContext);
        List<Student> ps = service.findAll(0, 10);
        for (Student s : ps) {
            Log.i("StudentService", s.toString());
        }

    }

    @Test
    // 测试聚合函数
    public void testGetCount() {
        StudentDataBaseService service = new StudentDataBaseService(appContext);

        Log.i("PersonService", " 共有 : " + service.getCount() + "  人");

    }
}

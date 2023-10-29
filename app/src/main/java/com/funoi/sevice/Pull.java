package com.funoi.sevice;

import android.util.Xml;
import com.funoi.model.Student;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class Pull {
    public static List<Student> getStudents(InputStream is) throws Exception {
        List<Student> students = null;
        Student student = null;
        List<String> hobby = null;
        XmlPullParser parser = Xml.newPullParser();

        // 创建pull方式的解析器
        parser.setInput(is, "utf-8");

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    students = new ArrayList<>();
                    break;

                case XmlPullParser.START_TAG:
                    String name = parser.getName();  //获取标签的名称
                    if ("student".equals(name)) {
                        student = new Student();
                        student.setId(Integer.valueOf(parser.getAttributeValue(0)));
                    }

                    if (student != null) {
                        if ("name".equals(name)) {
                            student.setName(parser.nextText());
                        }

                        if ("age".equals(name)) {
                            student.setAge(Integer.valueOf(parser.nextText()));
                        }

                        if ("sex".equals(name)) {
                            student.setSex(parser.nextText());
                        }

                        if ("edu".equals(name)) {
                            student.setEdu(parser.nextText());
                        }

                        if ("hobbies".equals(name)) {
                            hobby = new ArrayList<>();
                        }

                        if (hobby != null) {
                            if ("hobby".equals(name)) {
                                hobby.add(parser.nextText());
                            }
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("hobbies".equals(parser.getName())) {
                        student.setHobby(hobby);
                        hobby = null;
                    }

                    if ("student".equals(parser.getName())) {
                        students.add(student);
                        student = null;

                    }
                    break;
            }
            eventType = parser.next();  //读取下一行
        }

        return students;
    }

    public static void saveXML(List<Student> students, Writer writer) throws Exception{
        // 创建xml的生成器
        XmlSerializer serializer=Xml.newSerializer();  // pull方式的xml序列话对象
        serializer.setOutput(writer);  // 设置输出流,字符方式
        serializer.startDocument("utf-8",true);

        serializer.startTag(null,"students");
        //遍历链表
        for(Student student:students){
            serializer.startTag(null,"student");
            serializer.attribute(null,"id", String.valueOf(student.getId()));

            serializer.startTag(null,"name");
            serializer.text(student.getName());
            serializer.endTag(null,"name");

            serializer.startTag(null, "age");
            serializer.text(String.valueOf(student.getAge()));
            serializer.endTag(null, "age");

            serializer.startTag(null, "sex");
            serializer.text(student.getSex());
            serializer.endTag(null, "sex");

            serializer.startTag(null, "edu");
            serializer.text(student.getEdu());
            serializer.endTag(null, "edu");

            serializer.startTag(null, "hobbies");
            if(student.getHobby()!=null){
                for (String hobby:student.getHobby()){
                    serializer.startTag(null, "hobby");
                    serializer.text(hobby);
                    serializer.endTag(null, "hobby");
                }
            }
            serializer.endTag(null, "hobbies");

            serializer.endTag(null, "student");
        }
        serializer.endTag(null,"students");

        serializer.endDocument();

        writer.flush();
        writer.close();
    }
}

package com.funoi.MApp3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.funoi.model.Student;
import com.funoi.sevice.Pull;
import com.funoi.sevice.StudentDataBaseService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AddStuFragment extends Fragment {
    public static final String ARG_OBJECT = "object";
    private StudentDataBaseService service;
    private List<Student> students;
    private Integer id=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.addstu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        service=new StudentDataBaseService(getActivity());
        students=service.findAll(0,service.getCount());
        if(students.size()>0)
            id=students.get(students.size()-1).getId()+1;

        setEduSpinner(view);

        // 获取保存按钮
        Button save = view.findViewById(R.id.saveButton);
        setSaveButtonListener(save,view);

        // 获取读取按钮
        Button read = view.findViewById(R.id.readButton);
        setReadButtonListener(read,view);
    }

    public void setSaveButtonListener(Button button,View v) {
        button.setOnClickListener(view -> {
            // 获取姓名
            String userName = ((TextView) v.findViewById(R.id.userName)).getText().toString();
            // 获取年龄
            Integer userAge = Integer.valueOf(((TextView) v.findViewById(R.id.userAge)).getText().toString());

            // 获取性别
            RadioButton boy = v.findViewById(R.id.boy);
            RadioButton girl = v.findViewById(R.id.girl);
            String sex = boy.getText().toString();
            if (boy.isChecked()) {
                sex = boy.getText().toString();
            }
            if (girl.isChecked()) {
                sex = girl.getText().toString();
            }

            // 获取爱好
            List<String> hobby = new ArrayList<>();
            CheckBox hobby1 = v.findViewById(R.id.hobby1);
            CheckBox hobby2 = v.findViewById(R.id.hobby2);
            CheckBox hobby3 = v.findViewById(R.id.hobby3);
            CheckBox hobby4 = v.findViewById(R.id.hobby4);
            if (hobby1.isChecked()) {
                hobby.add(hobby1.getText().toString());
            }
            if (hobby2.isChecked()) {
                hobby.add(hobby2.getText().toString());
            }
            if (hobby3.isChecked()) {
                hobby.add(hobby3.getText().toString());
            }
            if (hobby4.isChecked()) {
                hobby.add(hobby4.getText().toString());
            }

            // 获取学历
            Spinner edu = v.findViewById(R.id.education);
            String selectEdu = edu.getSelectedItem().toString();


            // 封装获取的数据
            while (service.find(id)!=null){
                id++;
            }
            Student student = new Student(id,userName, userAge, sex, selectEdu, hobby);
            students.add(student);
//            System.out.println(student);



            // 输出到手机,以xml文件保存
            File file=new File(requireActivity().getFilesDir(),"students.xml");

            FileOutputStream stream= null;
            try {

                stream = requireActivity().openFileOutput("students.xml", Context.MODE_PRIVATE);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(stream));

            //StringWriter writer=new StringWriter();
            try {
                Pull.saveXML(students, writer);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // 保存到数据库
            for (Student s:students){
                service.saveStudent(s);
            }
            System.out.println(students.size()+"个已保存");

        });
    }

    public void setReadButtonListener(Button button,View v) {
        button.setOnClickListener(view -> {

            // 检查是否存在students.xml文件，不存在先创建并初始化文件内容
            if(students.size() == 0){
                File file=new File(requireActivity().getFilesDir(),"students.xml");
                FileOutputStream stream= null;
                try {
                    stream = requireActivity().openFileOutput("students.xml", Context.MODE_PRIVATE);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(stream));
                try {
                    Pull.saveXML(students, writer);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            // 读取xml文件内容
            InputStream is= null;
            try {
                is = requireActivity().openFileInput("students.xml");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            students= null;
            try {
                students = Pull.getStudents(is);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println(students);
        });
    }

    // 设置spinner下拉列表
    private void setEduSpinner(View view){
        //获取下拉菜单
        Spinner edu = view.findViewById(R.id.education);

        //创建适配器
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.education_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        //adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        //给下拉菜单绑定适配器
        edu.setAdapter(adapter);

        edu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}

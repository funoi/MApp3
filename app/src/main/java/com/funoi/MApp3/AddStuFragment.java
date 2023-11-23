package com.funoi.MApp3;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
    private static final String TAG = "AddStuFragment";  // 打印日志用
    private StudentDataBaseService service;  // 获取数据库 service
    private List<Student> students;  // 存储 students

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 返回 view ？
        return inflater.inflate(R.layout.add_stu, container, false);
    }

    /**
     * 暂时不太理解，先复制使用
     *
     * @param view 此处的 view 是由 onCreateView() 返回的 view ? R.layout.add_stu
     * @param savedInstanceState 如果 不是 null ，则重新构建 fragment ？
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated");
        // 获取数据库 service
        service = new StudentDataBaseService(getActivity());
        // 通过 findAll() 获取 students 集合
        students = service.findAll(0, service.getCount());


        // 设置 Edu 下拉列表
        setEduSpinner(view);

        // 获取保存按钮
        Button save = view.findViewById(R.id.saveButton);
        setSaveButtonListener(save, view);

    }

    public void setSaveButtonListener(Button button, View v) {
        button.setOnClickListener(view -> {
            Log.d(TAG, "setSaveButtonListener");

            // 获取输入的信息
            Student student = getStudent(v);
            // 添加到 students 集合
            if (service.find(student.getId()) == null) { // 检查 id 是否已存在
                students.add(getStudent(v));

                // 输出到手机,以xml文件保存
                File file = new File(requireActivity().getFilesDir(), "students.xml");

                FileOutputStream stream = null;
                try {

                    stream = requireActivity().openFileOutput("students.xml", Context.MODE_PRIVATE);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream));

                //StringWriter writer=new StringWriter();
                try {
                    Pull.saveXML(students, writer);
                    Log.i(TAG, "save " + students.size() + " to xml");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                // 保存到数据库
                if (students != null) {
                    for (Student s : students) {
                        service.saveStudent(s);
                    }
                    Log.i(TAG, "save " + students.size() + " to database");
                    Toast.makeText(getContext(), "student saved", Toast.LENGTH_LONG).show();
                }

            } else {
                Log.i(TAG, "student already exists");
                Toast.makeText(getContext(), "id already exists,please enter a new", Toast.LENGTH_LONG).show();
            }


        });
    }

    public Student getStudent(View v) {
        Log.d(TAG, "getStudent");

        // 获取姓名
        String userName = ((TextView) v.findViewById(R.id.userName)).getText().toString();
        // 获取id
        Integer userID = Integer.valueOf(((TextView) v.findViewById(R.id.userID)).getText().toString());
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


        // 封装并返回获取的数据
        return new Student(userID, userName, userAge, sex, selectEdu, hobby);
    }




    // 设置spinner下拉列表
    private void setEduSpinner(View view) {
        Log.d(TAG, "setEduSpinner");

        // 获取下拉菜单
        Spinner edu = view.findViewById(R.id.education);

        // 创建适配器
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.education_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        //adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        // 给下拉菜单绑定适配器
        edu.setAdapter(adapter);

        // 设置选择事件
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

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.funoi.model.Student;
import com.funoi.sevice.Pull;
import com.funoi.sevice.StudentDataBaseService;

import java.io.*;
import java.util.List;

public class ShowStuFragment extends Fragment {
    private static final String TAG = "ShowStuFragment";  // 打印日志用


    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;

    protected ShowStuFragAdapter mAdapter;


    // 获取数据库 service
    StudentDataBaseService service;
    // 获取 students 集合
    List<Student> students;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 返回 view ？
        View rootView = inflater.inflate(R.layout.show_stu, container, false);
        rootView.setTag(TAG);  // 设置Tag？暂时不知道啥用


        // 获取 recycleview
        mRecyclerView = rootView.findViewById(R.id.recView);

        // 设置 recycleview 显示的布局
        // LinearLayoutManager 线性布局
        // GridLayoutManager 网格布局
        // StaggeredGridLayoutManager 同行可不同高度的网格布局
        mLayoutManager = new LinearLayoutManager(getActivity());

        // 获取当前滚动位置
        int scrollPosition = 0;
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        // 将布局管理器和滚动位置添加到 recycleview
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);


        // 从数据库读取
        service = new StudentDataBaseService(getActivity());
        students = service.findAll(0, service.getCount());


        // 创建 recycleview 的适配器，将要显示的学生信息集合 students 传递给适配器
        mAdapter = new ShowStuFragAdapter(students);
        // 装载适配器
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }


    /**
     * 暂时不太理解，先复制使用
     * 应该是初始化方法 ？
     *
     * @param view               此处的 view 是由 onCreateView() 返回的 view ？ R.layout.show_stu
     * @param savedInstanceState 如果 不是 null ，则重新构建 fragment ？
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onViewCreated");

        // 获取读取按钮
        Button read = view.findViewById(R.id.readButton);
        Log.i(TAG,"read:"+read);
        setReadButtonListener(read);
    }

    public void setReadButtonListener(Button button) {
        button.setOnClickListener(view -> {
            Log.d(TAG,"setReadButtonListener");
/*  从 xml 读取
            // 检查是否存在 students.xml 文件，不存在先创建并初始化文件内容
            if (students.size() == 0) {
                File file = new File(requireActivity().getFilesDir(), "students.xml");
                FileOutputStream stream = null;
                try {
                    stream = requireActivity().openFileOutput("students.xml", Context.MODE_PRIVATE);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream));
                try {
                    Pull.saveXML(students, writer);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            // 读取xml文件内容
            InputStream is = null;
            try {
                is = requireActivity().openFileInput("students.xml");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            students = null;
            try {
                students = Pull.getStudents(is);
                Log.i(TAG,"read success");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

 */
            // 从数据库读取
            service = new StudentDataBaseService(getActivity());
            students = service.findAll(0, service.getCount());

            // 创建 recycleview 的适配器，将要显示的学生信息集合 students 传递给适配器
            mAdapter = new ShowStuFragAdapter(students);
            // 重新装载适配器
            mRecyclerView.setAdapter(mAdapter);
            Log.i(TAG, "read " + students.size() + " from database");
            Toast.makeText(getContext(), "read success", Toast.LENGTH_LONG).show();
        });
    }

}

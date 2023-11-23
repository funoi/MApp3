package com.funoi.MApp3;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.funoi.model.Student;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShowStuFragAdapter extends RecyclerView.Adapter<ShowStuFragAdapter.ViewHolder> {
    private static final String TAG = "ShowStuAdapter";  // 标识，用于Log
    private final List<Student> students;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * 设置要控制的组件
         */
        private final TextView show_title;
        private final TextView show_id;
        private final TextView show_age;
        private final TextView show_sex;
        private final TextView show_edu;
        private final TextView show_hobby;

        /**
         * 设置 recycle_view 的 view ？
         *
         * @param v recycle_view 的 view ？
         */
        public ViewHolder(View v) {
            super(v);
            // 设置 recycle_view 的 view ？ 的点击事件
            v.setOnClickListener(v1 -> {

            });

            // 获取要控制的组件
            show_title = v.findViewById(R.id.show_title);
            show_id = v.findViewById(R.id.show_id);
            show_age = v.findViewById(R.id.show_age);
            show_sex = v.findViewById(R.id.show_sex);
            show_edu = v.findViewById(R.id.show_edu);
            show_hobby = v.findViewById(R.id.show_hobby);

        }


        /**
         * 对外接口，获取对应的组件
         *
         * @return 相应的组件
         */
        public TextView getShow_title() {
            return show_title;
        }

        public TextView getShow_id() {
            return show_id;
        }

        public TextView getShow_age() {
            return show_age;
        }

        public TextView getShow_sex() {
            return show_sex;
        }

        public TextView getShow_edu() {
            return show_edu;
        }

        public TextView getShow_hobby() {
            return show_hobby;
        }
    }

    /**
     * 初始化 students
     *
     * @param students 要显示的对象集合
     */
    public ShowStuFragAdapter(List<Student> students) {
        this.students = students;
    }

    // 创建新的 view ，可直接复制使用
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // 创建一个新的 view
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stu_list, viewGroup, false);

        return new ViewHolder(v);
    }

    // 替换 view 的内容
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "View " + position + " set");

        // 用 students 的内容替换 view 的内容
        viewHolder.getShow_title().setText(students.get(position).getName());
        viewHolder.getShow_id().setText("学号： " + students.get(position).getId());
        viewHolder.getShow_age().setText("年龄： " + students.get(position).getAge());
        viewHolder.getShow_sex().setText("性别： " + students.get(position).getSex());
        viewHolder.getShow_edu().setText("学历： " + students.get(position).getEdu());
        viewHolder.getShow_hobby().setText("爱好： " + students.get(position).getHobby().toString());
    }

    // 返回 students 的数量
    @Override
    public int getItemCount() {
        return students.size();
    }
}

package com.funoi.MApp3;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // tab 标签
        String[] tabMenu = {"xml保存", "xml读取", "添加", "查找"};

        // 初始化控件
        ViewPager2 viewPager2 = findViewById(R.id.viewpager2);
        TabLayout tabLayout = findViewById(R.id.tab);

        // 页面
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new AddStuFragment());
        fragments.add(new ShowStuFragment());

        // 创建 viewpager2 适配器对象
        MyFragAdapter myFragAdapter = new MyFragAdapter(this, fragments);
        viewPager2.setAdapter(myFragAdapter);  // 给 viewpager2 设置适配器

        // 将 tab 标签与 viewpager2 绑定
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText(tabMenu[position])
        ).attach();


    }


}
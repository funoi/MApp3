package com.funoi.MApp3;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.funoi.MApp3.AddStuAdapter;

public class MainActivity extends AppCompatActivity {

    private String[] tabMenu={"添加","仓库"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AddStuAdapter addStuAdapter = new AddStuAdapter(this);
        ViewPager2 viewPager2 = findViewById(R.id.viewpager2);
        viewPager2.setAdapter(addStuAdapter);

        TabLayout tabLayout=findViewById(R.id.tab);
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText(tabMenu[position])
        ).attach();


    }





}
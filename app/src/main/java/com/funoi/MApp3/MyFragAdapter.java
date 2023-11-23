package com.funoi.MApp3;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;


public class MyFragAdapter extends FragmentStateAdapter {

    // 存储 viewpager2 的页面
    List<Fragment> fragments;

    /**
     * 适配器
     * @param fragment  要挂载的主页面
     * @param fragments  要显示的页面组
     */
    public MyFragAdapter(FragmentActivity fragment,List<Fragment> fragments) {
        super(fragment);
        this.fragments=fragments;
    }


    /**
     * 创建新页面
     * @param position 索引
     * @return 要创建的页面
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    /**
     *
     * @return 返回页面数量
     */
    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
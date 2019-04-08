package com.example.baitaplon.monan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baitaplon.PagerAdapter;
import com.example.baitaplon.R;

public class Fragment_Main_MonAn extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_monan,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.pager_monan);
        tabLayout = view.findViewById(R.id.tab_monan);
        PagerAdapter adapter = new PagerAdapter(getActivity().getSupportFragmentManager());
        adapter.Add_Fragment(new Fragment_ThemMonAn(),"THÊM MÓN ĂN");
        adapter.Add_Fragment(new Fragment_ListMonAn(),"DANH SÁCH MÓN ĂN");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        reload_fragment();

    }
    private void reload_fragment() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }
            @Override
            public void onPageSelected(int i) {
                viewPager.getAdapter().notifyDataSetChanged();
            }
            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }
}

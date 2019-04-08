package com.example.baitaplon.thongke;

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

public class Fragment_Main_thongke extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.thongke,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.pager_thongke);
        tabLayout = view.findViewById(R.id.tab_thongke);
        PagerAdapter adapter = new PagerAdapter(getActivity().getSupportFragmentManager());
        adapter.Add_Fragment(new Fragment_thongke_doanhthu(),"Doanh Thu");
        adapter.Add_Fragment(new Fragment_thongke_monan(),"Món Ăn");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}

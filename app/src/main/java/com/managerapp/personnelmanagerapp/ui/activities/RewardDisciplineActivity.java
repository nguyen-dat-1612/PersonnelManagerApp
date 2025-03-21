package com.managerapp.personnelmanagerapp.ui.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.tabs.TabLayout;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ActivityRewardDisciplineBinding;

public class RewardDisciplineActivity extends AppCompatActivity {

    private ActivityRewardDisciplineBinding binding;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRewardDisciplineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Khởi tạo NavController
        navController = Navigation.findNavController(this, R.id.fragmentContainer);

        // Thiết lập TabLayout với Navigation
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Khen thưởng"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Kỷ luật"));

        // Đồng bộ TabLayout với Fragment
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    navController.navigate(R.id.rewardFragment);
                } else if (tab.getPosition() == 1) {
                    navController.navigate(R.id.disciplineFragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // Nút Back
        binding.backBtn.setOnClickListener(v -> finish());

        // Nút Help (ví dụ: hiển thị Toast)
        binding.helpBtn.setOnClickListener(v -> {
            android.widget.Toast.makeText(this, "Hướng dẫn sử dụng", android.widget.Toast.LENGTH_SHORT).show();
        });
    }
}
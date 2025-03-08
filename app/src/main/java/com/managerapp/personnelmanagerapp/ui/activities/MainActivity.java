package com.managerapp.personnelmanagerapp.ui.activities;

import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private ActivityMainBinding binding;
    private ChipNavigationBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        // Lấy NavController từ NavHostFragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
        // Đặt mục mặc định cho bottom bar
        binding.bottomBar.setItemSelected(R.id.home_fragment, true);
        
        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        bottomBar = findViewById(R.id.bottomBar);

        if (bottomBar != null) {
            bottomBar.setOnItemSelectedListener(id -> {
                if (id == R.id.home_fragment) {
                    navController.navigate(R.id.home_fragment);
                } else if (id == R.id.notifications_fragment) {
                    navController.navigate(R.id.notifications_fragment);
                } else if (id == R.id.schedule_fragment) {
                    navController.navigate(R.id.schedule_fragment);
                } else if (id == R.id.profile_fragment) {
                    navController.navigate(R.id.profile_fragment);
                }
            });
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController != null && (navController.navigateUp() || super.onSupportNavigateUp());
    }

}

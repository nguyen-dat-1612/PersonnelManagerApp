package com.managerapp.personnelmanagerapp.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ActivityProfileInfoBinding;
import com.managerapp.personnelmanagerapp.domain.model.DataItem;
import com.managerapp.personnelmanagerapp.ui.adapters.ProfileInfoAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProfileInfoActivity extends AppCompatActivity {

    private ActivityProfileInfoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityProfileInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<DataItem> userInfoList = new ArrayList<>();
        userInfoList.add(new DataItem(getString(R.string.full_name), "Nguyễn Văn A"));
        userInfoList.add(new DataItem(getString(R.string.gender), "Male"));
        userInfoList.add(new DataItem(getString(R.string.date_of_birth), "01/01/1990"));
        userInfoList.add(new DataItem(getString(R.string.email), "nguyenvana@example.com"));
        userInfoList.add(new DataItem(getString(R.string.phone_number), "0123456789"));
        binding.recyclerViewInfomation.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.recyclerViewInfomation.setAdapter(new ProfileInfoAdapter(userInfoList));
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("key", "value"); // Thêm dữ liệu nếu cần
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
package com.managerapp.personnelmanagerapp.presentation.activities;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ActivityCollaborateBinding;
import com.managerapp.personnelmanagerapp.presentation.adapters.TimelineAdapter;
import com.managerapp.personnelmanagerapp.presentation.adapters.TimelineModel;
import java.util.ArrayList;
import java.util.List;

public class CollaborateActivity extends AppCompatActivity {

    private TimelineAdapter adapter;
    private ActivityCollaborateBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCollaborateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<TimelineModel> list = new ArrayList<>();
        list.add(new TimelineModel("Tháng 4/2024", "Trưởng phòng Nhân sự"));
        list.add(new TimelineModel("Tháng 10/2023", "Chính sách Nhà ở"));
        list.add(new TimelineModel("Tháng 3/2023", "Khen thưởng Workshop"));
        list.add(new TimelineModel("Tháng 9/2022", "Kết quả KPI xuất sắc"));

        adapter = new TimelineAdapter(this, list);
        binding.recyclerView.setAdapter(adapter);
    }
}
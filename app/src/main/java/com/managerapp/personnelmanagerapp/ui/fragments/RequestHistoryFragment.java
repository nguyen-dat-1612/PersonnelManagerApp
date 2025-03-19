package com.managerapp.personnelmanagerapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentRequestHistoryBinding;
import com.managerapp.personnelmanagerapp.domain.model.LeaveApplication;
import com.managerapp.personnelmanagerapp.ui.activities.MainActivity;
import com.managerapp.personnelmanagerapp.ui.activities.RequestActivity;
import com.managerapp.personnelmanagerapp.ui.adapters.LeaveApplicationAdapter;

import java.util.ArrayList;
import java.util.List;


public class RequestHistoryFragment extends Fragment {
    private FragmentRequestHistoryBinding binding;

    public RequestHistoryFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRequestHistoryBinding.inflate(inflater, container, false);

        List<LeaveApplication> list = new ArrayList<>();

        list.add(new LeaveApplication(1, "Nghỉ ốm", 1,
                "2024-03-10", "2024-03-12", "Bị sốt cao", "Chờ duyệt", true));

        list.add(new LeaveApplication(2, "Nghỉ thai sản", 1,
                "2024-04-01", "2024-10-01", "Sinh con", "Đã duyệt", true));

        list.add(new LeaveApplication(3, "Nghỉ phép", 1,
                "2024-05-15", "2024-05-20", "Đi du lịch", "Bị từ chối", false));

        list.add(new LeaveApplication(4, "Nghỉ công tác", 1,
                "2024-06-01", "2024-06-05", "Tham gia hội nghị", "Đã duyệt", true));

        list.add(new LeaveApplication(5, "Nghỉ không lương", 1,
                "2024-07-10", "2024-07-12", "Việc cá nhân", "Chờ duyệt", false));

        list.add(new LeaveApplication(6, "Nghỉ ốm", 1,
                "2024-08-20", "2024-08-25", "Phẫu thuật", "Đã duyệt", true));

        list.add(new LeaveApplication(7, "Nghỉ phép", 1,
                "2024-09-05", "2024-09-07", "Về quê", "Chờ duyệt", false));

        list.add(new LeaveApplication(8, "Nghỉ thai sản", 1,
                "2024-10-01", "2025-04-01", "Sinh con", "Đã duyệt", true));

        list.add(new LeaveApplication(9, "Nghỉ công tác", 1,
                "2024-11-15", "2024-11-20", "Hội thảo ngành", "Bị từ chối", true));

        list.add(new LeaveApplication(10, "Nghỉ không lương", 1,
                "2024-12-10", "2024-12-15", "Lý do cá nhân", "Chờ duyệt", false));

        binding.recyclerViewHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewHistory.setAdapter(new LeaveApplicationAdapter(list, leaveApplication -> {
            Toast.makeText(requireContext(),leaveApplication.getLeaveApplicationId() + " ", Toast.LENGTH_LONG ).show();
        }));

        binding.fabNewRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.request_fragment);
                navController.navigate(R.id.action_requestHistoryFragment_to_newRequestFragment);
            }
        });

        binding.backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        return binding.getRoot();
    }
}
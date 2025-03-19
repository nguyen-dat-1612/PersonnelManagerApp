package com.managerapp.personnelmanagerapp.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentNotificationsBinding;
import com.managerapp.personnelmanagerapp.domain.model.Notification;
import com.managerapp.personnelmanagerapp.ui.adapters.NotificationAdapter;

import java.util.ArrayList;
import java.util.List;


public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    public NotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(getLayoutInflater());

        binding.notificationList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.notificationList.setAdapter(new NotificationAdapter(getSampleNotifications(),notification -> {
            Toast.makeText(requireContext(), "Clicked: " + notification.getTitle(), Toast.LENGTH_SHORT).show();
        }));



        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    // Tạo danh sách thông báo giả lập
    private List<Notification> getSampleNotifications() {
        List<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification(1, "Xét duyệt yêu cầu", "Yêu cầu nghỉ phép của bạn đã được phê duyệt."));
        notifications.add(new Notification(2, "Cập nhật hệ thống", "Hệ thống sẽ bảo trì từ 1:00 AM đến 3:00 AM."));
        notifications.add(new Notification(3, "Thông báo mới", "Bạn có lịch họp vào 14:00 ngày mai."));
        notifications.add(new Notification(4, "Nhắc nhở", "Hãy hoàn thành báo cáo trước 17:00 hôm nay."));
        return notifications;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
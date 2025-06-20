package com.managerapp.personnelmanagerapp.presentation.sendNotification.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentSendNotificationBinding;
import com.managerapp.personnelmanagerapp.domain.model.UserSummary;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import com.managerapp.personnelmanagerapp.presentation.sendNotification.adapter.DepartmentAdapter;
import com.managerapp.personnelmanagerapp.presentation.sendNotification.adapter.SelectedUsersAdapter;
import com.managerapp.personnelmanagerapp.presentation.sendNotification.adapter.UserSearchRecyclerAdapter;
import com.managerapp.personnelmanagerapp.presentation.sendNotification.viewmodel.SendNotificationViewModel;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@AndroidEntryPoint
public class SendNotificationFragment extends Fragment {

    private final String TAG = "SendNotificationFragment";
    private FragmentSendNotificationBinding binding;
    private SendNotificationViewModel viewModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private PublishSubject<String> searchSubject = PublishSubject.create();
    private SelectedUsersAdapter selectedUsersAdapter;
    private List<UserSummary> selectedUsers = new ArrayList<>();
    private UserSearchRecyclerAdapter searchRecyclerAdapter;
    private ActivityResultLauncher<Intent> filePickerLauncher;
    private NavController navController;

    private boolean isUploading = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SendNotificationViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSendNotificationBinding.inflate(inflater, container, false);
        setupRecyclerView();
        setupDepartmentRecyclerView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_main);
        setOnListener();
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        observeViewModel();
        filePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri pdfUri = result.getData().getData();
                        if (pdfUri != null) {
                            uploadPdfToServer(pdfUri);
                        }
                    }
                }
        );
    }
    private void uploadPdfToServer(Uri pdfUri) {
        try {
            isUploading = true;
            InputStream inputStream = requireContext().getContentResolver().openInputStream(pdfUri);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            inputStream.close();

            RequestBody requestBody = RequestBody.create(bytes, MediaType.parse("application/pdf"));
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "document.pdf", requestBody);

            viewModel.uploadFile(filePart);

        } catch (IOException e) {
            e.printStackTrace();
            isUploading = false;
            Toast.makeText(getContext(), "Lỗi khi đọc file", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupRecyclerView() {

        searchRecyclerAdapter = new UserSearchRecyclerAdapter(new ArrayList<>(), user -> {
            viewModel.addSelectedUser(user);
        });
        binding.searchResultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.searchResultRecyclerView.setAdapter(searchRecyclerAdapter);

        selectedUsersAdapter = new SelectedUsersAdapter(selectedUsers, user -> {
            viewModel.removeSelectedUser(user);
        });

        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(requireContext());
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);

        binding.selectedUsersRecyclerView.setLayoutManager(flexboxLayoutManager);
        binding.selectedUsersRecyclerView.setAdapter(selectedUsersAdapter);

        binding.notificationTypeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.individualRadio) {
                binding.individualSelectionLayout.setVisibility(View.VISIBLE);
                binding.departmentSelectionLayout.setVisibility(View.GONE);
            } else if (checkedId == R.id.departmentRadio) {
                binding.individualSelectionLayout.setVisibility(View.GONE);
                binding.departmentSelectionLayout.setVisibility(View.VISIBLE);
                viewModel.getDepartments();
            } else if (checkedId == R.id.allUsersRadio) {
                binding.individualSelectionLayout.setVisibility(View.GONE);
                binding.departmentSelectionLayout.setVisibility(View.GONE);
            }
        });

        binding.notificationTypeRadioGroup.check(R.id.individualRadio);

        binding.uploadFileButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            filePickerLauncher.launch(Intent.createChooser(intent, "Chọn file PDF"));
        });

    }
    public void setOnListener() {
        binding.searchUserEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchSubject.onNext(s.toString());
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });

        viewModel.search(searchSubject);

        binding.backBtn.setOnClickListener(v-> {
            navController.popBackStack();
        });

        binding.historyBtn.setOnClickListener(v -> {
            navController.navigate(R.id.action_sendNotificationFragment_to_historyNotificationFragment);
        });

        binding.sendNotificationButton.setOnClickListener(v -> {
            if (isUploading) {
                Toast.makeText(getContext(), "Vui lòng chờ file tải lên hoàn tất", Toast.LENGTH_SHORT).show();
                return;
            }
            binding.progressBar.getRoot().setVisibility(View.VISIBLE);
            binding.sendNotificationButton.setEnabled(false);

            String title = binding.notificationTitleEditText.getText().toString();
            String content = binding.notificationContentEditText.getText().toString();
            List<String> attached = new ArrayList<>();
            List<Long> receiverIds = new ArrayList<>();
            List<String> positionIds = new ArrayList<>();
            List<String> departmentIds = new ArrayList<>();

            if (viewModel.getUploadResult().getValue() != null) {
                attached.add(((UiState.Success<String>) viewModel.getUploadResult().getValue()).getData());
            }

            if (binding.notificationTypeRadioGroup.getCheckedRadioButtonId() == R.id.individualRadio) {
                receiverIds = viewModel.getSelectedUserIds();
            } else if (binding.notificationTypeRadioGroup.getCheckedRadioButtonId() == R.id.departmentRadio) {
                departmentIds = viewModel.getSelectedDepartments();
            }

            viewModel.sendNotification(title, content, "", attached, receiverIds, positionIds, departmentIds);
        });
    }

    private void observeViewModel() {
        viewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            if (state.isLoading) {
                // Hiển thị loading nếu cần
            }

            if (state.userSummaryList != null && !state.userSummaryList.isEmpty()) {
                Log.d(TAG, "Danh sach nguoi dung: " + state.userSummaryList.toString());
                searchRecyclerAdapter.updateData(state.userSummaryList);
            } else {
                searchRecyclerAdapter.updateData(Collections.emptyList());
            }

            if (state.errorMessage != null) {
                Log.e(TAG, state.errorMessage);
                Toast.makeText(getContext(), state.errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getSelectedUsers().observe(getViewLifecycleOwner(), selectedList -> {
            selectedUsersAdapter.submitList(selectedList);
            updateSelectedCount(selectedList.size());
        });

        viewModel.getUploadResult().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);
            }
            else if (state instanceof UiState.Success<String>) {
                binding.selectedFileTextView.setText(((UiState.Success<String>) state).getData());
                isUploading = false;
                binding.progressBar.getRoot().setVisibility(View.GONE);
            } else if (state instanceof UiState.Error) {
                binding.progressBar.getRoot().setVisibility(View.GONE);
                Toast.makeText(getContext(), "Lỗi khi tải lên file", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getSendNotification().observe(getViewLifecycleOwner(), success -> {
            binding.progressBar.getRoot().setVisibility(View.GONE);
            binding.sendNotificationButton.setEnabled(true);

            if (success) {
                Toast.makeText(getContext(), "Gửi thông báo thành công!", Toast.LENGTH_SHORT).show();
                resetUI();
            } else {
                Toast.makeText(getContext(), "Gửi thất bại, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateSelectedCount(int count) {
        binding.selectedCountTextView.setText(String.format("(%d)", count));
        if (count > 0) {
            binding.selectedCountTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.red));
        } else {
            binding.selectedCountTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray));
        }
    }


    private void setupDepartmentRecyclerView() {
        DepartmentAdapter adapter = new DepartmentAdapter( department -> {
            viewModel.selectedDepartment(department);
        },  department -> {
            viewModel.removeSelectedDepartment(department);
        });

        binding.departmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.departmentRecyclerView.setAdapter(adapter);

        viewModel.getDepartment().observe(getViewLifecycleOwner(), departments -> {
            adapter.setDepartments(departments);
        });
    }

    private void resetUI() {
        binding.notificationTitleEditText.setText("");
        binding.notificationContentEditText.setText("");
        binding.selectedFileTextView.setText("");
        binding.notificationTypeRadioGroup.check(R.id.individualRadio);
        viewModel.clearSelectedUsers();  // cần thêm hàm này trong ViewModel
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.dispose();
    }
}
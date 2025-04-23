package com.managerapp.personnelmanagerapp.presentation.feedback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;

import com.managerapp.personnelmanagerapp.data.remote.request.FeedbackRequest;
import com.managerapp.personnelmanagerapp.presentation.base.BaseActivity;
import com.managerapp.personnelmanagerapp.databinding.ActivityFeedBackBinding;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;

public class FeedBackActivity extends BaseActivity {
    private final String TAG = "FeedBackActivity";
    private ActivityFeedBackBinding binding;
    private FeedbackViewModel feedbackViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityFeedBackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        feedbackViewModel = new ViewModelProvider(this).get(FeedbackViewModel.class);
        setOnListener();
        onFeedbackSent();
    }

    public void setOnListener() {

        binding.backBtn.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("key", "value"); // Thêm dữ liệu nếu cần
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });

        binding.btnSend.setOnClickListener(v -> {
            String title = binding.edtTitle.getText().toString();
            String reason = binding.edtReason.getText().toString();
            FeedbackRequest feedbackRequest = new FeedbackRequest(title, reason);
            feedbackViewModel.sendFeedback(title, reason);
        });
    }

    public void onFeedbackSent() {
        feedbackViewModel.getUiState().observe(this, state -> {
            if (state instanceof UiState.Loading) {
                binding.viewSendSuccess.getRoot().setVisibility(View.GONE);
                binding.progressOverlay.getRoot().setVisibility(View.VISIBLE);
            }  else if (state instanceof UiState.Success) {
                long startTime = System.currentTimeMillis();
                binding.progressOverlay.getRoot().setVisibility(View.GONE);
                String message = ((UiState.Success<String>) state).getData();
                Log.d(TAG, "Success: " + message);
                binding.viewSendSuccess.getRoot().setVisibility(View.VISIBLE);
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    long elapsed = System.currentTimeMillis() - startTime;
                    Log.d(TAG, ">>> Gọi finish() sau " + elapsed + "ms");
                    finish();
                }, 3000);
            } else if (state instanceof UiState.Error) {
                binding.progressOverlay.getRoot().setVisibility(View.GONE);
                binding.viewSendSuccess.getRoot().setVisibility(View.GONE);
                String message = ((UiState.Error) state).getErrorMessage();
                Log.e(TAG, "Error: " + message);
            }
        });
    }
}
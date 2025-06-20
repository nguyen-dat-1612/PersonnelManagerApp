package com.managerapp.personnelmanagerapp.presentation.decision.ui;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.databinding.DialogUploadSignedFileBinding;
import com.managerapp.personnelmanagerapp.databinding.FragmentDetailApproveBinding;
import com.managerapp.personnelmanagerapp.domain.model.Decision;
import com.managerapp.personnelmanagerapp.presentation.decision.viewmodel.DetailApproveViewModel;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import com.managerapp.personnelmanagerapp.utils.FileUtils;
import com.managerapp.personnelmanagerapp.utils.PdfUtils;
import com.managerapp.personnelmanagerapp.utils.WebViewUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@AndroidEntryPoint
public class DetailApproveFragment extends Fragment {

    private FragmentDetailApproveBinding binding;
    private DetailApproveViewModel viewModel;
    private final String TAG = "DetailApproveFragment";
    private NavController navController;
    private String htmlTemplate;

    private Decision decisionResponse;

    private String pdfUrl;
    private String pdfFileName;
    private ActivityResultLauncher<Intent> filePickerLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DecisionDetailFragmentArgs args = DecisionDetailFragmentArgs.fromBundle(getArguments());
        String decisionId = args.getDecisionId();

        Bundle bundle = new Bundle();
        bundle.putString("decision_id", decisionId);
        setArguments(bundle);

        viewModel = new ViewModelProvider(this).get(DetailApproveViewModel.class);

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailApproveBinding.inflate(inflater, container, false);
        loadData();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_main);
        onListener();
        binding.decisionWebView.getSettings().setLoadWithOverviewMode(true);
        binding.decisionWebView.getSettings().setUseWideViewPort(true);
        binding.decisionWebView.setInitialScale(100);

    }

    private void loadHtmlTemplate(Decision decision) {
        htmlTemplate = FileUtils.loadAssetTextAsString(requireContext(), "decision_template.html");
        if (htmlTemplate == null) {
            Log.e(TAG, "Không thể tải decision_template.html từ assets");
            return;
        }
        binding.decisionWebView.getSettings().setJavaScriptEnabled(true);
        binding.decisionWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                injectDecisionData(binding.decisionWebView, decisionResponse);
            }
        });
        binding.decisionWebView.loadDataWithBaseURL(null, htmlTemplate, "text/html", "UTF-8", null);
    }

    private void onListener() {
        binding.swipeRefresh.setOnRefreshListener(() -> {
            viewModel.refresh();
            binding.swipeRefresh.setRefreshing(false);
        });

        binding.backBtn.setOnClickListener(v -> navController.popBackStack());

        binding.btnDelete.setOnClickListener(v-> {
            viewModel.deleteDecision(decisionResponse.getId());
        });

        viewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Success<Decision>) {
                Decision decision = ((UiState.Success<Decision>) state).getData();

                binding.btnApprove.setOnClickListener(v ->
                        showSignDialog()
                );

                binding.btnPrint.setOnClickListener(v -> {
                    if (decision != null) {
                        Bitmap bitmap = captureWebView(binding.decisionWebView);
                        PdfUtils.saveBitmapToPdf(requireActivity(), bitmap, "quyet_dinh");
                    } else {
                        Toast.makeText(requireActivity(), "Không có dữ liệu quyết định để tải", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void loadData() {
        viewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                binding.progressOverlay.getRoot().setVisibility(VISIBLE);
            } else if (state instanceof UiState.Success) {
                binding.progressOverlay.getRoot().setVisibility(INVISIBLE);
                Decision decision = ((UiState.Success<Decision>) state).getData();
                if (decision != null) {
                    binding.setDecision(decision);
                    binding.setContext(requireContext());
                    decisionResponse = decision;
                    if (decision.getSigner() != null ) {
                        if (decision.getAttachment() != null) {
                            pdfFileName = "decision_" + decision.getId() + ".pdf";
                            pdfUrl = "https://www.googleapis.com/drive/v3/files/" + decision.getAttachment() +
                                    "?alt=media&key=AIzaSyB3sNzITjsRHLvRq68dECM-w8N2tS0ZznQ";
                            configureWebViewSettings();
                            loadPdf(pdfUrl);
                        }
                    } else {
                        loadHtmlTemplate(decision);
                    }
                    if (decision.getSalaryPromotion() != null) {
                        binding.salaryPromotionLayout.getRoot().setVisibility(VISIBLE);
                    }
                    if (decision.getSeniorityAllowanceRule() != null) {
                        binding.seniorityAllowanceCard.getRoot().setVisibility(VISIBLE);
                    }
                    if (decision.getPosition() != null) {
                        binding.positionCard.getRoot().setVisibility(VISIBLE);
                    }
                    if (decision.getAttachment() != null) {
                        binding.attachmentCard.getRoot().setVisibility(VISIBLE);
                    }
                }

            } else if (state instanceof UiState.Error) {
                binding.progressOverlay.getRoot().setVisibility(INVISIBLE);
                binding.errorView.getRoot().setVisibility(VISIBLE);
            }

        });

        viewModel.getUpdateUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                binding.progressBar.getRoot().setVisibility(VISIBLE);
            } else if (state instanceof UiState.Success) {
                Toast.makeText(requireContext(), "Phê duyệt thành công", Toast.LENGTH_SHORT).show();
                navController.popBackStack();
            } else if (state instanceof UiState.Error) {
                binding.progressBar.getRoot().setVisibility(GONE);
                Log.d(TAG, "loadData: " + ((UiState.Error) state).getErrorMessage());
            }
        });

        viewModel.getDeleteUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Success) {
                Toast.makeText(requireContext(), "Từ chối duyệt thành công", Toast.LENGTH_SHORT).show();
                navController.popBackStack();
            } else if (state instanceof UiState.Error) {
                Log.d(TAG, "loadData: " + ((UiState.Error) state).getErrorMessage());
            }
        });
    }

    private void injectDecisionData(WebView webView, Decision decision) {
        if (decisionResponse == null) {
            Log.d(TAG, decisionResponse == null ? "decision null" : "decision not null");
        } else {
            String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
            String[] parts = date.split("/");
            String day = parts[0];
            String month = parts[1];
            String year = parts[2];

            String decisionBody;
            switch (decisionResponse.getType()) {
                case AWARD:
                    decisionBody = String.format("Khen thưởng %s với nội dung: %s.",
                            getOrEmpty(decisionResponse.getUser().getFullName()),
                            getOrEmpty(decisionResponse.getContent()));
                    break;
                case SENIORITY_ALLOWANCE:
                    decisionBody = String.format("Áp dụng phụ cấp thâm niên cho %s với phần trăm %s%% và %s ngày nghỉ.",
                            getOrEmpty(decisionResponse.getUser().getFullName()),
                            getOrEmpty(decisionResponse.getSeniorityAllowanceRule().getSeniorityPercentage()),
                            getOrEmpty(decisionResponse.getSeniorityAllowanceRule().getSeniorityLeaveDay()));
                    break;
                case DISCIPLINE:
                    decisionBody = String.format("Kỷ luật %s với nội dung: %s.",
                            getOrEmpty(decisionResponse.getUser().getFullName()),
                            getOrEmpty(decisionResponse.getContent()));
                    break;
                case PROMOTION:
                    decisionBody = String.format("Bổ nhiệm %s vào chức vụ %s.",
                            getOrEmpty(decisionResponse.getUser().getFullName()),
                            getOrEmpty(decisionResponse.getPosition().getDescription()));
                    break;
                case INCREASE_SALARY:
                    decisionBody = String.format("Tăng lương cho %s từ ngạch %s lên ngạch %s với giá trị %s.",
                            getOrEmpty(decisionResponse.getUser().getFullName()),
                            getOrEmpty(decisionResponse.getSalaryPromotion().getCurrentJobGradeName()),
                            getOrEmpty(decisionResponse.getSalaryPromotion().getRequestJobGradeName()),
                            getOrEmpty(decisionResponse.getSalaryPromotion().getRequestJobGradeValue()));
                    break;
                case TERMINATION:
                    decisionBody = String.format("Chấm dứt hợp đồng lao động với %s.",
                            getOrEmpty(decisionResponse.getUser().getFullName()));
                    break;
                default:
                    decisionBody = getOrEmpty(decisionResponse.getContent());
                    break;
            }

            String escapedBody = decisionBody.replace("'", "\\'");
            String escapedContent = getOrEmpty(decisionResponse.getContent()).replace("'", "\\'");
//            String escapedSigner = getOrEmpty(decisionResponse.getSigner().getFullName()).replace("'", "\\'");

//            String js = String.format(
//                    "document.getElementById('decisionNumber').innerText = '%s/QĐ-...';" +
//                            "document.getElementById('day').innerText = '%s';" +
//                            "document.getElementById('month').innerText = '%s';" +
//                            "document.getElementById('year').innerText = '%s';" +
//                            "document.getElementById('decisionContentText').innerText = '%s';" +
//                            "document.getElementById('decisionBody1').innerText = '%s';"
//                            + "document.getElementById('signerName').innerText = '%s';",
//                    decisionResponse.getId(), day, month, year, escapedContent, escapedBody, escapedSigner
//            );

            String js = String.format(
                    "document.getElementById('decisionNumber').innerText = '%s/QĐ-...';" +
                            "document.getElementById('day').innerText = '%s';" +
                            "document.getElementById('month').innerText = '%s';" +
                            "document.getElementById('year').innerText = '%s';" +
                            "document.getElementById('decisionContentText').innerText = '%s';" +
                            "document.getElementById('decisionBody1').innerText = '%s';"
//                            + "document.getElementById('signerName').innerText = '%s';"
                    ,
                    decisionResponse.getId(), day, month, year, escapedContent, escapedBody
            );

            webView.evaluateJavascript(js, null);
        }

    }

    public Bitmap captureWebView(WebView webView) {
        int width = webView.getWidth();
        int height = (int) (webView.getContentHeight() * webView.getScale());

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        webView.draw(canvas);

        return bitmap;
    }


    private void uploadPdfToServer(Uri pdfUri) {
        try {
            InputStream inputStream = requireContext().getContentResolver().openInputStream(pdfUri);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            inputStream.close();

            RequestBody requestBody = RequestBody.create(bytes, MediaType.parse("application/pdf"));
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "document.pdf", requestBody);

            viewModel.uploadFile(filePart);

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Lỗi khi đọc file", Toast.LENGTH_SHORT).show();
        }
    }

    private void showSignDialog() {
        DialogUploadSignedFileBinding dialogBinding = DialogUploadSignedFileBinding.inflate(LayoutInflater.from(requireContext()));

        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(dialogBinding.getRoot())
                .setCancelable(false)
                .create();

        dialogBinding.uploadFileButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            filePickerLauncher.launch(Intent.createChooser(intent, "Chọn file PDF"));
        });

        dialogBinding.btnSign.setOnClickListener(v -> {
            dialog.dismiss();
            viewModel.updateDecision(decisionResponse.getId(), dialogBinding.selectedFileTextView.getText().toString());
        });

        dialogBinding.btnCancel.setOnClickListener(v -> dialog.dismiss());

        viewModel.getUploadResult().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                dialogBinding.progressBar.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof UiState.Success) {
                dialogBinding.selectedFileTextView.setText(((UiState.Success<String>) state).getData());
                dialogBinding.progressBar.getRoot().setVisibility(View.GONE);
                Toast.makeText(requireContext(), "Đã tải file lên thành công", Toast.LENGTH_SHORT).show();
            } else if (state instanceof UiState.Error) {
                Toast.makeText(requireContext(), ((UiState.Error) state).getErrorMessage(), Toast.LENGTH_SHORT).show();
                dialogBinding.progressBar.getRoot().setVisibility(View.GONE);
            }
        });

        dialog.show();
    }


    private String getOrEmpty(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    private void configureWebViewSettings() {
        WebViewUtils.configureWebViewForPdf(binding.decisionWebView, pdfUrl);
    }

    private void loadPdf(String pdfUrl) {
        try {
            WebViewUtils.loadPdf(binding.decisionWebView, pdfUrl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            showError("Lỗi tải PDF: " + e.getMessage());
        }
    }

    private void showError(@NonNull String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.managerapp.personnelmanagerapp.presentation.main.ui;

import static androidx.databinding.DataBindingUtil.bind;
import static androidx.databinding.DataBindingUtil.setContentView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ActivityMainBinding;
import com.managerapp.personnelmanagerapp.domain.repository.UserRepository;
import com.managerapp.personnelmanagerapp.domain.usecase.user.GetUserUseCase;
import com.managerapp.personnelmanagerapp.presentation.base.BaseActivity;
import com.managerapp.personnelmanagerapp.service.RxWebSocketClient;
import com.managerapp.personnelmanagerapp.utils.LocaleHelper;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class MainActivity extends BaseActivity {
    private NavController navController;
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private RxWebSocketClient webSocketClient;
    private Disposable messageDisposable;

    private static final String WS_URL = "ws://192.168.1.118:8080/ws/websocket";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission("android.permission.POST_NOTIFICATIONS") != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{"android.permission.POST_NOTIFICATIONS"}, 1);
                Log.d(TAG, "onCreate: Requesting POST_NOTIFICATIONS permission");
            }
        }

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_main);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            Log.d(TAG, "onCreate: NavController initialized");
        } else {
            Log.e(TAG, "onCreate: NavHostFragment is null");
        }

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            Log.d("Nav Controller", "Navigated to: " + destination.getLabel() + " | ID: " + destination.getId());
        });

        webSocketClient = new RxWebSocketClient();
        webSocketClient.connect(WS_URL);

        messageDisposable = webSocketClient.observeMessages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        message -> Log.d("MainActivityWebSocket", "Received message: " + message),
                        error -> Log.e("MainActivityWebSocket", "Error: " + error.getMessage()),
                        () -> Log.d("MainActivityWebSocket", "WebSocket closed")
                );

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        100
                );
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Đã cấp quyền tải file", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Không có quyền ghi file", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (messageDisposable != null && !messageDisposable.isDisposed()) {
            messageDisposable.dispose();
        }
        webSocketClient.disconnect();
    }
}
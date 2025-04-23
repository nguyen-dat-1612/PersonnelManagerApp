package com.managerapp.personnelmanagerapp.presentation.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ActivitySettingsBinding;
import com.managerapp.personnelmanagerapp.presentation.base.BaseActivity;
import com.managerapp.personnelmanagerapp.presentation.main.MainActivity;

public class SettingsActivity extends BaseActivity {
    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Thiết lập switch thông báo
        setupNotificationSwitch();

        // Thiết lập spinner ngôn ngữ
        setupLanguageSpinner();

        binding.backBtn.setOnClickListener(v -> finish());
    }

    private void setupNotificationSwitch() {
        // Đặt trạng thái ban đầu từ SharedPreferences
        boolean isNotificationEnabled = sharedPreferences.getBoolean("notifications", true);
        binding.notificationsSwitch.setChecked(isNotificationEnabled);

        binding.notificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("notifications", isChecked);
            editor.apply();

            if (isChecked) {
                enableNotifications();
            } else {
                disableNotifications();
            }
        });
    }

    private void setupLanguageSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.select_language, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.languageSpinner.setAdapter(adapter);

        // Đặt vị trí ban đầu
        String savedLanguage = sharedPreferences.getString("language", "vi");
        int position = getLanguagePosition(savedLanguage);
        binding.languageSpinner.setSelection(position);

        binding.languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] languageCodes = getResources().getStringArray(R.array.language_codes);
                String selectedLanguage = languageCodes[position];

                if (!sharedPreferences.getString("language", "vi").equals(selectedLanguage)) {
                    changeAppLanguage(selectedLanguage);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private int getLanguagePosition(String languageCode) {
        String[] codes = getResources().getStringArray(R.array.language_codes);
        for (int i = 0; i < codes.length; i++) {
            if (codes[i].equals(languageCode)) {
                return i;
            }
        }
        return 0;
    }

    private void changeAppLanguage(String languageCode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("language", languageCode);
        editor.apply();

        // Khởi động lại toàn bộ ứng dụng
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
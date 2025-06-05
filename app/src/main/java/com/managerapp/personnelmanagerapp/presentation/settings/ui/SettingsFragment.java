package com.managerapp.personnelmanagerapp.presentation.settings.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.managerapp.personnelmanagerapp.databinding.FragmentSettingsBinding;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.presentation.settings.viewmodel.SettingsViewModel;
import com.managerapp.personnelmanagerapp.utils.LocaleHelper;
import com.managerapp.personnelmanagerapp.manager.SettingsManager;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;
    @Inject
    SettingsManager settingsManager;
    private SettingsViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        setupNotificationSwitch();
        setupLanguageSpinner();
        setUpDarkMode();

        binding.backBtn.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_settingsFragment_to_mainFragment);
            navController.popBackStack();
        });
        return binding.getRoot();
    }

    private void setupNotificationSwitch() {
        boolean isNotificationEnabled = settingsManager.isNotificationEnabled();
        binding.notificationsSwitch.setChecked(isNotificationEnabled);

        binding.notificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            settingsManager.setNotificationEnabled(isChecked);
            if (isChecked) {
                viewModel.fetchFcmToken();
            } else {
                viewModel.offNotificationUiState();
            }
        });
    }

    private void setupLanguageSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.select_language,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.languageSpinner.setAdapter(adapter);

        String savedLanguage = settingsManager.getSelectedLanguage();
        Log.d("Language" , savedLanguage);
        int position = getLanguagePosition(savedLanguage);
        binding.languageSpinner.setSelection(position);

        binding.languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] languageCodes = getResources().getStringArray(R.array.language_codes);
                String selectedLanguage = languageCodes[position];

                if (!savedLanguage.equals(selectedLanguage)) {
                    settingsManager.setSelectedLanguage(selectedLanguage);
//                    LocaleListCompat appLocale = LocaleListCompat.forLanguageTags(selectedLanguage);
//                    AppCompatDelegate.setApplicationLocales(appLocale);
//                    requireActivity().recreate();
                    changeLanguage(selectedLanguage);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void changeLanguage(String languageCode) {
        LocaleHelper.setLocale(requireContext(), languageCode);
        if (getActivity() != null) {
            getActivity().recreate();
        }
    }

    private void setUpDarkMode() {
        boolean isDarkModeEnabled =settingsManager.isDarkModeEnabled();
        binding.darkModeSwitch.setChecked(isDarkModeEnabled);
        binding.darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            settingsManager.setDarkModeEnabled(isChecked);
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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

}
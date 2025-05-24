package com.managerapp.personnelmanagerapp.presentation.main.state;

import androidx.annotation.NonNull;

public sealed interface UiState<T> permits UiState.Loading, UiState.Success, UiState.Error {

    final class Loading<T> implements UiState<T> {
        private static final Loading<?> INSTANCE = new Loading<>();

        private Loading() {}

        @SuppressWarnings("unchecked")
        public static <T> Loading<T> getInstance() {
            return (Loading<T>) INSTANCE;
        }
    }

    final class Success<T> implements UiState<T> {
        private final T data;

        public Success(@NonNull T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }
    }

    final class Error<T> implements UiState<T> {
        private final String errorMessage;

        public Error(@NonNull String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}

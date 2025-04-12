package com.managerapp.personnelmanagerapp.ui.state;

import com.managerapp.personnelmanagerapp.data.remote.response.ContractResponse;
import com.managerapp.personnelmanagerapp.domain.model.Contract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public sealed interface ContractDetailState permits ContractDetailState.Loading, ContractDetailState.Success, ContractDetailState.Error {
    final class Loading implements ContractDetailState {
        private static final Loading INSTANCE = new Loading();
        private Loading() {}

        public static Loading getInstance() {
            return INSTANCE;
        }
    }

    final class Success implements ContractDetailState {
        @NonNull
        private final ContractResponse contract;

        public Success(@NonNull ContractResponse contract) {
            this.contract = contract;
        }

        @NonNull
        public ContractResponse getContract() {
            return contract;
        }
    }

    final class Error implements ContractDetailState {
        @NonNull
        private final String message;

        public Error(@NonNull String message) {
            this.message = message;
        }

        @NonNull
        public String getMessage() {
            return message;
        }
    }
}
package com.managerapp.personnelmanagerapp.ui.state;

import com.managerapp.personnelmanagerapp.data.remote.response.ContractResponse;
import com.managerapp.personnelmanagerapp.domain.model.Contract;

import java.util.List;

public sealed interface ContractListState {
    final class Loading implements ContractListState {}
    final class Success implements ContractListState {
        public final List<ContractResponse> contracts;

        public Success(List<ContractResponse> contracts) {
            this.contracts = contracts;
        }

        public List<ContractResponse> getContracts() {
            return contracts;
        }
    }

    final class Error implements ContractListState {
        public final String message;

        public Error(String message) {
            this.message = message;
        }
    }
    final class Empty implements ContractListState {}
}

package com.passbook.sparkeighteen.service;

import com.passbook.sparkeighteen.peristence.POJO.PaginatedResponse;
import com.passbook.sparkeighteen.peristence.POJO.TransactionRequest;
import com.passbook.sparkeighteen.peristence.POJO.TransactionResponse;
import com.passbook.sparkeighteen.peristence.entity.TransactionEntity;
import com.passbook.sparkeighteen.peristence.entity.UserEntity;
import com.passbook.sparkeighteen.peristence.repository.TransactionRepository;
import com.passbook.sparkeighteen.peristence.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import javax.validation.constraints.NotNull;
import java.util.Collections;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Transaction service helps to provide service to perform transactions.
 */
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final UserRepository userRepository;

    /**
     * Instantiates a new Transaction service.
     * @param transactionRepository the transaction repository
     * @param userRepository the user repository
     */
    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    /**
     * Create method to do the actual transaction containing the business logic to add deposit and withdraw and return response according.
     * @param userID  to make transaction for specific user.
     * @param request all fields required in transaction
     * @return the transaction response. (CREDIT OR DEBIT)
     * @throws Exception shows error messages.
     */
    public TransactionResponse transact(Integer userID, TransactionRequest request) throws Exception {

        if (request.getAmount() <= 0)
            return TransactionResponse.builder()
                    .message("Invalid Transaction amount")
                    .build();

        Optional<UserEntity> optionalUser = userRepository.findById(userID);
        if (optionalUser.isEmpty())
            return TransactionResponse.builder()
                    .message("User not found. Please use a registered user")
                    .build();

        UserEntity user = optionalUser.get();

        TransactionEntity transaction;
        switch (request.getTransactionType()) {
            case CREDIT:
                transaction = transactionRepository.save(TransactionEntity.builder()
                        .amount(request.getAmount())
                        .note(request.getNote())
                        .user(user)
                        .closingBalance(request.getAmount() + getUpdatedBalance(user))
                        .time(LocalDateTime.now())
                        .transactionType(request.getTransactionType())
                        .build());
                break;
            case DEBIT:
                if (getUpdatedBalance(user) < request.getAmount()) {
                    return TransactionResponse.builder()
                            .message(String.format("Insufficient balance to withdraw. Current Balance: %.2f", getUpdatedBalance(user)))
                            .build();
                }

                transaction = transactionRepository.save(TransactionEntity.builder()
                        .amount(request.getAmount())
                        .user(user)
                        .note(request.getNote())
                        .closingBalance(getUpdatedBalance(user) - request.getAmount())
                        .time(LocalDateTime.now())
                        .transactionType(request.getTransactionType())
                        .build());
                break;
            default:
                return TransactionResponse.builder()
                        .message(String.format("Unhandled transaction type %s", request.getTransactionType()))
                        .build();
        }

        return TransactionResponse.builder()
                .txnID(transaction.getId())
                .amount(transaction.getAmount())
                .note(transaction.getNote())
                .time(transaction.getTime())
                .closingBalance(transaction.getClosingBalance())
                .transactionType(transaction.getTransactionType())
                .message(String.format("Your A/C XXXXX has a %s by Rs %.2f on %s ", transaction.getTransactionType(), transaction.getAmount(), transaction.getTime()))
                .build();
    }

    /**
     * This method(getZeroOrLastBalance) is to get balance as zero if its user first transaction or the closing balance of the latest transaction.
     * first assign the balance 0.
     * @param user to get the balance of that specific user.
     * @return updated balance after transaction done.
     */
    private Float getUpdatedBalance(UserEntity user) {
        Float balance = 0f;
        Optional<List<TransactionEntity>> optionalTxns = transactionRepository.findByUser(user);
        if (optionalTxns.isPresent()) {
            List<TransactionEntity> txns = optionalTxns.get();
            if (txns.size() > 0) balance = txns.get(txns.size() - 1).getClosingBalance();
        }
        return balance;
    }

    /**
     * User gets all transaction list in paginated format.
     * @param userID is used to show all user performing transaction of that particular user.
     * @param pageSize is basically used to give the size of the page. user can set a page size to show how many transaction want to show you on a page.
     * @return the user transaction list by Paginated.
     */
    public PaginatedResponse getTransaction(String pageNo, String pageSize, @NotNull Integer userID) {

        final Optional<UserEntity> optionalUser = userRepository.findById(userID);
        if (optionalUser.isEmpty())
            return PaginatedResponse.builder()
                    .message("user not found")
                    .build();

        Pageable paging =
                PageRequest.of(
                        Integer.parseInt(pageNo),
                        Integer.parseInt(pageSize),
                        Sort.by("time").descending());
        Page<TransactionEntity> transactions =
                transactionRepository.findByUser(paging, optionalUser.get());

        if (transactions.isEmpty())
            return PaginatedResponse.builder()
                    .message("No transactions for this user.")
                    .build();

        List<TransactionResponse> response =
                transactions.getContent().stream()
                        .map(this::toTransactionResponse)
                        .collect(Collectors.toList());
        return PaginatedResponse.builder()
                .content(response)
                .totalPages(transactions.getTotalPages())
                .currentPage(Integer.parseInt(pageNo))
                .pageSize(Integer.parseInt(pageSize))
                .message("return list of transaction")
                .totalElements(transactions.getTotalElements())
                .build();

    }

    private TransactionResponse toTransactionResponse(TransactionEntity transaction) {

        return TransactionResponse.builder()
                .amount(transaction.getAmount())
                .time(transaction.getTime())
                .txnID(transaction.getId())
                .note(transaction.getNote())
                .closingBalance(transaction.getClosingBalance())
                .transactionType(transaction.getTransactionType())
                .build();
    }
    
    /**
     * Delete a specific transaction.
     * @param transactionID To delete specific transaction.
     * @return whether the transaction is deleted successfully or not.
     */
    public String deleteTransaction(Integer transactionID) {
        Optional<TransactionEntity> Transaction = transactionRepository.findById(transactionID);
        if (Transaction.isPresent()) {
            transactionRepository.deleteById(transactionID);
            return "Your TransactionID " + transactionID + " delete successfully.";
        }
        return "Entered Transaction ID does not Exists";
    }
}
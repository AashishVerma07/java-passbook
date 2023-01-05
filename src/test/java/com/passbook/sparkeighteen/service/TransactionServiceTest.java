package com.passbook.sparkeighteen.service;

import com.passbook.sparkeighteen.peristence.POJO.TransactionType;
import com.passbook.sparkeighteen.peristence.entity.TransactionEntity;
import com.passbook.sparkeighteen.peristence.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * The Transaction service test.
 */
@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    /**
     * If transaction id is right and exist then delete transaction is successful.
     */
    @Test
    public void deleteTransaction_transactionIdExist_deleteTransactionSuccessful() throws Exception {
        Integer transactionID = 1;

        TransactionEntity user = TransactionEntity.builder()
                .amount(100f)
                .note("Coffee")
                .transactionType(TransactionType.CREDIT)
                .closingBalance(100f)
                .build();

        when(transactionRepository.findById(transactionID)).thenReturn(Optional.ofNullable(user));

        String response = transactionService.deleteTransaction(transactionID);
        assertEquals("Your TransactionID " + transactionID + " delete successfully.", response);

    }

    /**
     * If transaction id does not exist then delete transaction is unsuccessful.
     * @throws Exception of entered transaction id does not exist.
     */
    @Test
    public void deleteTransaction_transactionIdNotExist_deleteTransactionUnSuccessful() throws Exception {
        Integer transactionID = 1;

        when(transactionRepository.findById(transactionID)).thenReturn(Optional.empty());

        String response = transactionService.deleteTransaction(transactionID);
        assertEquals("Entered Transaction ID does not Exists", response);

    }

}

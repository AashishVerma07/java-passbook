package com.passbook.sparkeighteen.controller;

import com.passbook.sparkeighteen.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

/**
 * The Transaction controller test.
 */
@ExtendWith({MockitoExtension.class})
public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    /**
     * if transaction id is valid then transaction is successfully deleted.
     */
    @Test
    public void deleteTransaction_validTransactionId_deleteTransactionSuccessful() throws Exception {

        Integer transactionID = 1;

        Mockito.when(transactionService.deleteTransaction(any())).thenReturn("Transaction deleted " + transactionID);

        ResponseEntity<String> response = transactionController.deleteTransaction(transactionID);
        assertEquals(response.getStatusCode(), HttpStatus.OK);

    }

    /**
     * If transaction id is invalid then delete transaction is unsuccessful.
     */
    @Test
    public void deleteTransaction_invalidTransactionId_deleteTransactionUnsuccessful() throws Exception {

        Integer transactionID = 1;

        Mockito.when(transactionService.deleteTransaction(any())).thenReturn("transaction id is not found");

        ResponseEntity<String> response = transactionController.deleteTransaction(transactionID);
        assertEquals(response.getStatusCode(), HttpStatus.OK);

    }
}

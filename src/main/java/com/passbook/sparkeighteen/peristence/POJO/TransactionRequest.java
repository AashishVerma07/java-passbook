package com.passbook.sparkeighteen.peristence.POJO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

/**
 * Transaction request where in request is initiated to perform transaction
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    @NotNull(message = "Transaction amount cannot be null")
    private Float amount;

    private String name;

    private String note;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
}
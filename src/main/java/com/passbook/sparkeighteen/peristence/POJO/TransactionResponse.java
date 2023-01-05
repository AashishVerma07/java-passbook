package com.passbook.sparkeighteen.peristence.POJO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Transaction response where in response is generated on performing a transaction.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private Integer txnID;

    private Float amount;

    private String note;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @NotBlank(message = "Transaction message cannot be blank")
    private String message;

    private Float closingBalance;

    @JsonFormat(pattern = "MMM dd, yyyy hh:mm:ss a", timezone = "Asia/Kolkata")
    @NotNull
    private LocalDateTime time;

}


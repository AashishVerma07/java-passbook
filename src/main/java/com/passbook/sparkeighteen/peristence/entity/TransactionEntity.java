package com.passbook.sparkeighteen.peristence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.passbook.sparkeighteen.peristence.POJO.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Transaction entity - All the fields in the transaction are listed in database
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction", schema = "public")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "closing_balance")
    private Float closingBalance;

    @Column(name = "note")
    private String note;

    @Column(name = "txt_date_time")
    @NotNull
    @JsonFormat(pattern = "MMM dd, yyyy hh:mm:ss a", timezone = "Asia/Kolkata")
    private LocalDateTime time;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

}
package br.com.exactaworks.desafio.dinamica.gabriel.api1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Builder;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("EXPENSE")
public class Expense {

    @Id
    private Integer id;
    private String name;
    private String description;
    @CreatedDate
    private LocalDateTime dateRegister;
    private BigDecimal amount;
    private String tags;
}
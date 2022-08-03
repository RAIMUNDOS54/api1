package br.com.exactaworks.desafio.dinamica.gabriel.api1.services;

import br.com.exactaworks.desafio.dinamica.gabriel.api1.models.Expense;
import br.com.exactaworks.desafio.dinamica.gabriel.api1.repositories.ExpenseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@Transactional
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public Mono<Expense> createExpense(Expense expense){
        return expenseRepository.save(expense);
    }

    public Flux<Expense> getAllExpenses(){
        return expenseRepository.findAll();
    }

    public Mono<Expense> findById(Integer expenseId){
        return expenseRepository.findById(expenseId);
    }
    
}

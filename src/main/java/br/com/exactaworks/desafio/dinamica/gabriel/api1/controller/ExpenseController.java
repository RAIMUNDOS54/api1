package br.com.exactaworks.desafio.dinamica.gabriel.api1.controller;

import br.com.exactaworks.desafio.dinamica.gabriel.api1.models.Expense;
import br.com.exactaworks.desafio.dinamica.gabriel.api1.services.ExpenseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public Flux<Expense> getAllExpenses(){
        return expenseService.getAllExpenses();
    }
    
    @PostMapping("/expense")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Expense> create(@RequestBody Expense expense){
        return expenseService.createExpense(expense);
    }

    @GetMapping("/{expenseId}")
    public Mono<ResponseEntity<Expense>> getExpenseById(@PathVariable Integer expenseId) {
        Mono<Expense> expense = expenseService.findById(expenseId);
        
        return expense.map( u -> ResponseEntity.ok(u))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}

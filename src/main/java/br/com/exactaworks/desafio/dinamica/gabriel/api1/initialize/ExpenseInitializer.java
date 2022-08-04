package br.com.exactaworks.desafio.dinamica.gabriel.api1.initialize;

import br.com.exactaworks.desafio.dinamica.gabriel.api1.models.Expense;
import br.com.exactaworks.desafio.dinamica.gabriel.api1.repositories.ExpenseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@Profile("!test")
@Slf4j
public class ExpenseInitializer implements CommandLineRunner {

    @Autowired
    private ExpenseRepository expenseRepository;

    Expense[] expenses = {new Expense(null,
									  "Gabriel","Livro de Spring Boot", 
									  LocalDateTime.now(), 
									  new BigDecimal("39.90"), 
									  "bom,legal"),
									  
                          new Expense(null,
									  "Veloso",
									  "Livro de Hibernate", 
									  LocalDateTime.now(), 
									  new BigDecimal("29.90"), 
									  "perfeito,ótimo")};

    @Override
    public void run(String... args) {
        initialDataSetup();
    }

    private List<Expense> getData(){
        return Arrays.asList(expenses);
    }

    private void initialDataSetup() {
        expenseRepository.deleteAll()
                .thenMany(Flux.fromIterable(getData()))
                .flatMap(expenseRepository::save)
                .thenMany(expenseRepository.findAll())
                .subscribe(expense -> {
                    log.info("Expense Inserted from CommandLineRunner " + expense);
                });
    }

}

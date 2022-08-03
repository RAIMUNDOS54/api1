package br.com.exactaworks.desafio.dinamica.gabriel.api1.controller;

import br.com.exactaworks.desafio.dinamica.gabriel.api1.models.Expense;
import br.com.exactaworks.desafio.dinamica.gabriel.api1.repositories.ExpenseRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@Slf4j
public class ExpenseControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ExpenseRepository expenseRepository;


    @Autowired
    private DatabaseClient databaseClient;

    private List<Expense> getData() {
        Expense[] expenses = {new Expense(null,"Gabriel","Livro de Spring Boot", LocalDateTime.now(), new BigDecimal("39.90"), "bom,legal"),
                              new Expense(null,"Veloso","Livro de Hibernate", LocalDateTime.now(), new BigDecimal("29.90"), "perfeito,ótimo")};

        return Arrays.asList(expenses);
    }

    @BeforeEach
    public  void setup() {
        List<String> statements = Arrays.asList("DROP TABLE IF EXISTS EXPENSE;",
                "CREATE TABLE EXPENSE (ID INT IDENTITY PRIMARY KEY, NAME VARCHAR(255) NOT NULL, DESCRIPTION VARCHAR(255) NOT NULL,DATE_REGISTER DATETIME,AMOUNT MONEY,TAGS VARCHAR(255));");

        statements.forEach(it -> databaseClient.sql(it)
                .fetch()
                .rowsUpdated()
                .block());

        expenseRepository.deleteAll()
                .thenMany(Flux.fromIterable(getData()))
                .flatMap(expenseRepository::save)
                .doOnNext(expense ->{
                    System.out.println("Expense Inserted from ExpenseControllerTest: " + expense);
                })
                .blockLast();
    }

    @Test
    public void getAllExpensesValidateCount() {
        webTestClient.get().uri("/api/expenses").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBodyList(Expense.class)
                .hasSize(2)
                .consumeWith(expense ->{
                    List<Expense> expenses = expense.getResponseBody();
                    expenses.forEach( u ->{
                        assertTrue(u.getId() != 0);
                    });
                });
    }

    @Test
    public void getAllExpensesValidateResponse() {
        Flux<Expense> expenseFlux = webTestClient.get().uri("/api/expenses").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .returnResult(Expense.class)
                .getResponseBody();

        StepVerifier.create(expenseFlux.log("Receiving values !!!"))
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    public void getExpenseById() {
        webTestClient.get().uri("/api/expenses".concat("/{expenseId}"),"1")
                            .exchange().expectStatus().isOk()
                            .expectBody()
                            .jsonPath("$.name","Gabriel");
    }

    @Test
    public void getExpenseById_NotFound() {
        webTestClient.get().uri("/api/expenses".concat("/{expenseId}"),"6")
                .exchange().expectStatus().isNotFound();
    }
    
    @Test
    public void createExpense() {
        Expense expense = new Expense(null,"Gabriel","Livro de Spring Boot", LocalDateTime.now(), new BigDecimal("319.90"), "perfeito,ótimo");

        webTestClient.post().uri("/api/expenses/expense").contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                            .body(Mono.just(expense),Expense.class)
                            .exchange()
                            .expectStatus().isCreated()
                            .expectBody()
                            .jsonPath("$.id").isNotEmpty()
                            .jsonPath("$.name").isEqualTo("Gabriel");
    }
}
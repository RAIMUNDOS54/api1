package br.com.exactaworks.desafio.dinamica.gabriel.api1.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import br.com.exactaworks.desafio.dinamica.gabriel.api1.models.Expense;

@Repository
public interface ExpenseRepository extends ReactiveCrudRepository<Expense, Integer> { }

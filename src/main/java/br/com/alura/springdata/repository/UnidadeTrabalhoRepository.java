package br.com.alura.springdata.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.alura.springdata.orm.UnidadeTrabalho;

public interface UnidadeTrabalhoRepository extends CrudRepository<UnidadeTrabalho, Integer> {

}

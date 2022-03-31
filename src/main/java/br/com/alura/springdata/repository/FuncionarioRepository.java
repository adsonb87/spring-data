package br.com.alura.springdata.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.alura.springdata.orm.Funcionario;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Integer>{

}

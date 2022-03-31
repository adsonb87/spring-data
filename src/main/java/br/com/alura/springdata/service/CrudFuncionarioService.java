package br.com.alura.springdata.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import br.com.alura.springdata.orm.Cargo;
import br.com.alura.springdata.orm.Funcionario;
import br.com.alura.springdata.orm.UnidadeTrabalho;
import br.com.alura.springdata.repository.CargoRepository;
import br.com.alura.springdata.repository.FuncionarioRepository;
import br.com.alura.springdata.repository.UnidadeTrabalhoRepository;

public class CrudFuncionarioService {
	
	private Boolean system = true;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private final FuncionarioRepository funcionarioRepository;
	private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;
	private final CargoRepository cargoRepository;
	
	
	
	public CrudFuncionarioService(FuncionarioRepository funcionarioRepository, 
				UnidadeTrabalhoRepository unidadeTrabalhoRepository, CargoRepository cargoRepository) {
		this.funcionarioRepository = funcionarioRepository;
		this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
		this.cargoRepository = cargoRepository;
	}
	
	public void inicial(Scanner scanner) {
		
		while(system) {
			System.out.println("Qual acao:");
			System.out.println("0 - Sair");
			System.out.println("1 - Salvar");
			System.out.println("2 - Atualizar");
			System.out.println("3 - Visualizar");
			System.out.println("4 - Deletar");
			
			int action = scanner.nextInt();
			
			switch (action) {
			case 1:
				salvar(scanner);
				break;
			case 2:
				atualizar(scanner);
				break;
			case 3:
				visualizar();
				break;
			case 4:
				deletar(scanner);
				break;
			default:
				system = false;
				break;
			}
		}
	}
	
	public void salvar(Scanner scanner) {
		System.out.println("Nome:");
		String nome = scanner.next();
		
		System.out.println("CPF:");
		String cpf = scanner.next();
		
		System.out.println("Data de contratação: ");
		String data = scanner.next();
		
		System.out.println("Salario: ");
		Double salario = scanner.nextDouble();
		
		System.out.println("Cargo Id: ");
		Integer cargoId = scanner.nextInt();
		
		Optional<Cargo> cargo = cargoRepository.findById(cargoId);

		Funcionario funcionario = new Funcionario();
		funcionario.setCargo(cargo.get());
		funcionario.setCpf(cpf);
		funcionario.setDataContratacao((LocalDate) formatter.parse(data));
		funcionario.setNome(nome);
		funcionario.setSalario(salario);
		
		List<UnidadeTrabalho> unidades = unidade(scanner);
		funcionario.setUnidadeTrabalho(unidades);
		
		funcionarioRepository.save(funcionario);
		System.out.println("Salvo");
	}
	
	private List<UnidadeTrabalho> unidade(Scanner scanner) {
		Boolean isTrue = true;
		
		List<UnidadeTrabalho> unidades = new ArrayList<>();
		
		while(isTrue) {
			System.out.println("Digite o unidadeId (Para sair digite 0)");
			Integer unidadeId = scanner.nextInt();
			
			if(unidadeId !=0) {
				Optional<UnidadeTrabalho> unidade = unidadeTrabalhoRepository.findById(unidadeId);
				unidades.add(unidade.get());
			}else {
				isTrue = false;
			}
		}
		
		return unidades;
	}

	public void atualizar(Scanner scanner) {
		System.out.println("ID: ");
		Integer id = scanner.nextInt();
		
		System.out.println("Nome:");
		String nome = scanner.next();
		
		System.out.println("CPF:");
		String cpf = scanner.next();
		
		System.out.println("Data de contratação: ");
		String data = scanner.next();
		
		System.out.println("Salario: ");
		Double salario = scanner.nextDouble();
		
		System.out.println("Cargo Id: ");
		Integer cargoId = scanner.nextInt();
		
		Optional<Cargo> cargo = cargoRepository.findById(cargoId);

		Funcionario funcionario = new Funcionario();
		funcionario.setId(id);
		funcionario.setCargo(cargo.get());
		funcionario.setCpf(cpf);
		funcionario.setDataContratacao(LocalDate.parse(data, formatter));
		funcionario.setNome(nome);
		funcionario.setSalario(salario);
		
		List<UnidadeTrabalho> unidades = unidade(scanner);
		funcionario.setUnidadeTrabalho(unidades);
		
		funcionarioRepository.save(funcionario);
		System.out.println("Atualizado");
	}
	
	public void visualizar() {
		Iterable<Funcionario> funcionarios = funcionarioRepository.findAll();
		
		funcionarios.forEach(funcionario -> System.out.println(funcionario.toString()));
		
		/*
		for (Cargo cargo : cargos) {
			System.out.println(cargo.toString());
		}
		*/
	}
	
	public void deletar(Scanner scanner) {
		System.out.println("ID do Funcionario");
		Integer id = scanner.nextInt();
		cargoRepository.deleteById(id);
		System.out.println("Deletado");
	}
	
	
}

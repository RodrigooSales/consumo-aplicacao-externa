# Desafio Rest com Consumo de Api Externa

## Objetivo do projeto

A ideia do desafio seria criar uma aplicação em spring boot, na qual, exisitira uma simulação de api externa localmente usando Json-Server ou Mockar a aplicação via Postman, onde, exisitiria uma entidade usuarios e departamentos.

O objetivo do projeto então seria criar um serviço REST que consumiria essa api externa criando assim, uma entidade Cargos e nessa entidade Cargos seria salvo os usuarios e para cada usuario seria associado um cargo.

## O que foi solicitado e requisitado

1. CRUD completo para recursos dos Cargos.
2. Realizar uma associação de um determinado cargo a um usuário.
3. Java 8, 11 ou 17.
4. Uso do Spring Boot.
5. Testes unitários e de integração.
6. Utilização de boas práticas de desenvolvimento e, se necessário, conceitos como SOLID e
padrões de projeto.
7. README contendo Documentação ou informações gerais e explicativas sobre o projeto e Passos de como executar o projeto.

## Funcionamento do projeto

O projeto foi feito usando java(Versão 17) com springboot na versão 2.7.8 salvando os usuarios em um banco de dados H2 em memoria usando o JPA e RestTemplate como ferramentas de contribuição para a criação do projeto.

### Divisão do projeto:

O projeto ele foi dividido da seguinte forma:
    
    Pacote Controller -> ele possui a classe CargoController, responsavel pelo CRUD dos recursos relacionados a entidade cargo.

    Pacote Entities -> ele possui as entidades relacionadas a Cargos e Usuarios.

    Pacote Repository -> ele possui a interface que implementa o JpaRepository.

    Pacote Services -> ele possui a classe responsavel pelo consumo de usuarios da api externa.

    Pacote Exeception -> ele possui as classes relacionadas ao tratamento de execeções do projeto.

    Arquivo de propiedades -> ele possui as configurações de banco de dados H2

### Entidades usadas

Foram usadas duas entidades na aplicação. Primeiro a Entidade Cargo, que recebe um id do tipo Long, um nome e uma lista de usuarios. A segunda Entidade Usuario ela possui um id e um nome que são recebidos da api externa e possui tambem um objeto cargo. Nas entidades Cargo e Usuario foi necessaria a utilização das anotações @OneToMany e @ManyToONe

### Funcionamento da classe CargoController:

A classe CargoController como dito anteriormente é responsavel pelo CRUD dos recursos relacionados aos cargos, ou seja, criação de cargos, leitura de cargo, atualização de cargos e remoção de cargos salvos no banco de dados.

O primeiro metodo da classe CargoController é o metodo que cria cargo, no qual, é passado as informações no body da requisição da aplicação

``` java
public Cargo criarCargo(@RequestBody Cargo cargo) {
		return cargoRepository.save(cargo);
	}    
```

O segundo metodo seria o de ler um cargo pelo id, no qual, o id é passado na url da chamada por exemplo "http://localhost:8080/cargos/{id}"

``` java
	@GetMapping("/{id}")
	public Cargo buscarCargoPorId(@PathVariable Long id) {
		return cargoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrado com id " + id));
	}
```

O terceiro metodo seria o de atualizar um cargo, no qual, passa-se o id do Cargo na url e um body na requisição por exemplo "http://localhost:8080/cargos/{id}"

``` java
    @PutMapping("/{id}")
	public Cargo atualizarCargo(@PathVariable Long id, @RequestBody Cargo cargoAtualizado) {
		Cargo cargo = cargoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrado com id " + id));
		cargo.setName(cargoAtualizado.getName());
		cargo.setUsuarios(cargoAtualizado.getUsuarios());
		return cargoRepository.save(cargo);
	}
```

O quarto metodo seria o de deletar um cargo, que segue a mesma ideia dos 2 anteriores recebendo somente o id na url "http://localhost:8080/cargos/{id}"

``` java
    @DeleteMapping("/{id}")
	public ResponseEntity<?> deletarCargo(@PathVariable Long id) {
		Cargo cargo = cargoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrado com id " + id));
		cargoRepository.delete(cargo);
		return ResponseEntity.ok().build();
	}
```

E por fim os 2 ultimos metodos que adcionam e deletam um usuario em relação a um cargo, nos quais, eles buscam os usuarios via id da api externa e adicionando ou deletando os mesmos

``` java
    @PostMapping("/{id}/usuarios/{usuarioId}")
	public Cargo adicionarUsuarioAoCargo(@PathVariable Long id, @PathVariable String usuarioId) {
		Cargo cargo = cargoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrado com id " + id));
		Usuario usuario = new Usuario();
		usuario = usuarioService.buscarUsuarioPorId(usuarioId);

		usuario.setCargo(cargo);
		cargo.getUsuarios().add(usuario);
		return cargoRepository.save(cargo);
	}

	@DeleteMapping("/{id}/usuarios/{usuarioId}")
	public Cargo removerUsuarioDoCargo(@PathVariable Long id, @PathVariable String usuarioId) {
		Cargo cargo = cargoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrado com id " + id));
		Usuario usuario = usuarioService.buscarUsuarioPorId(usuarioId);
		cargo.getUsuarios().remove(usuario);
		return cargoRepository.save(cargo);
	}
```
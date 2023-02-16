# Desafio Rest com Consumo de Api Externa

## Objetivo do projeto

A ideia do desafio seria criar uma aplicação em Spring Boot, na qual, existe uma simulação de api externa localmente usando Json-Server ou Mockar a aplicação via Postman, onde, existe uma entidade usuários e departamentos. 

O objetivo do projeto então seria criar um serviço REST que consumiria essa api externa criando assim, uma entidade Cargos e nessa entidade Cargos seria salvo os usuários e para cada usuário seria associado um cargo. 

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

O projeto foi feito usando Java(Versão 17) com Spring boot na versão 2.7.8 salvando os usuários em um banco de dados H2 em memória usando o JPA e RestTemplate como ferramentas de contribuição para a criação do projeto. 

### Divisão do projeto:
 
O projeto ele foi dividido da seguinte forma: 

    Pacote Controller -> ele possui a classe CargoController, responsável pelo CRUD dos recursos relacionados a entidade cargo. 

    Pacote Entities -> ele possui as entidades relacionadas a Cargos e Usuários. 

    Pacote Repository -> ele possui a interface que implementa o JpaRepository. 

    Pacote Services -> ele possui a classe responsável pelo consumo de usuários da api externa. 

    Pacote Exeception -> ele possui as classes relacionadas ao tratamento de exceções do projeto. 

    Arquivo de propriedades -> ele possui as configurações de banco de dados H2 

### Entidades usadas

Foram usadas duas entidades na aplicação. Primeiro a Entidade Cargo, que recebe um id do tipo Long, um nome e uma lista de usuários. A segunda Entidade Usuário ela possui um id e um nome que são recebidos da api externa e possui também um objeto cargo. Nas entidades Cargo e Usuário foi necessária a utilização das anotações @OneToMany e @ManyToONe usadas para mapear o relacionamento entre as duas entidades citadas no banco H2 em memória.

Toda via a Entidade Usuário recebe a anotação @JoinColumn para vincular a coluna de junção que associa à entidade Cargo, além disso, ela utiliza a anotação @JsonIgnore para evitar o erro de referência cíclica. 

Entidade Usuario:
``` java
@Entity
@Table
public class Usuario implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private String id;
	@Column
	private String name;	
	@ManyToOne
	@JoinColumn(name = "cargo_id")
	@JsonIgnore
	private Cargo cargo;
	//getters, setters e contructores
```

Entidade Cargo:
``` java
@Entity
@Table
public class Cargo implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private Long id;
	@Column
	private String name;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Usuario> usuarios;
	//getters, setters e contructores
```
### Funcionamento da classe CargoController:

A classe CargoController como dito anteriormente é responsável pelo CRUD dos recursos relacionados aos cargos, ou seja, criação de cargos, leitura de cargo, atualização de cargos e remoção de cargos salvos no banco de dados. 

O primeiro método da classe CargoController é o método que cria cargo, no qual, é passado as informações no body da requisição da aplicação. 

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

E por fim os 2 ultimos metodos que adcionam e deletam um usuario em relação a um cargo, nos quais, eles buscam os usuarios via id da api externa e adicionando ou deletando os mesmos. Porem eles tratam 2 exceções importantissimas para o funcionamento da aplicação, eles possuem a UserNotFoundException que trata o fato de o usuario existir ou não e possuem a ExternalApiUnavailableException que trata o fato da api externa estar indisponivel para a consulta.

``` java
    @PostMapping("/{id}/usuarios/{usuarioId}")
	public ResponseEntity<Cargo> adicionarUsuarioAoCargo(@PathVariable Long id, @PathVariable String usuarioId) {
		Cargo cargo = cargoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrado com id " + id));
		Usuario usuario;
		try {
			usuario = usuarioService.buscarUsuarioPorId(usuarioId);
		}catch (HttpServerErrorException | HttpClientErrorException ex){
			throw new ExternalApiUnavailableException("API externa indisponível");
		} catch (Exception ex) {
			throw new UserNotFoundException("Usuário não encontrado");
		}

		usuario.setCargo(cargo);
		cargo.getUsuarios().add(usuario);
		return ResponseEntity.ok(cargoRepository.save(cargo));
	}

	@DeleteMapping("/{id}/usuarios/{usuarioId}")
	public ResponseEntity<Cargo> removerUsuarioDoCargo(@PathVariable Long id, @PathVariable String usuarioId) {
		Cargo cargo = cargoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrado com id " + id));
		Usuario usuario;
		try {
			usuario = usuarioService.buscarUsuarioPorId(usuarioId);
		} catch (HttpServerErrorException | HttpClientErrorException ex) {
			throw new ExternalApiUnavailableException("API externa indisponível");
		} catch (Exception ex) {
			throw new UserNotFoundException("Usuário não encontrado");
		}

		cargo.getUsuarios().remove(usuario);
		return ResponseEntity.ok(cargoRepository.save(cargo));
	}
```

### Funcionamento da classe UsuarioService:

A classe usuario service foi implementada para buscar os usuarios da api externa e utiliza somente a dependencia RestTemplate para o mesmo, nela é passada a URL base da api e no metodo buscarUsuarioPorId é passado o id que foi passado no path da requisição. E aqui tambem são tratas as exceções que vimos no CargoController e instaciamos a nossa exceção usando a exceção HttpClientErrorException que é lançada quando uma chamada HTTP não for processada com sucesso pelo servidor da aplicação externa.

``` java
@Service
public class UsuarioService {

    private RestTemplate restTemplate = new RestTemplate();

    private final String BASE_URL = "http://localhost:3000/usuarios/";

    public Usuario buscarUsuarioPorId(String id) {
    	String url = BASE_URL + id;
        try {
            return restTemplate.getForObject(url, Usuario.class);
        } catch (RestClientException e){
            if (e instanceof HttpClientErrorException) {
                HttpClientErrorException ex = (HttpClientErrorException) e;
                if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                    throw new UserNotFoundException("Usuário não encontrado com id " + id);
                }
            }
            throw new ExternalApiUnavailableException("API externa indisponível");
        }

    }

}

```

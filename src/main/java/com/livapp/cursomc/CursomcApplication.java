package com.livapp.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.livapp.cursomc.domain.Categoria;
import com.livapp.cursomc.domain.Cidade;
import com.livapp.cursomc.domain.Cliente;
import com.livapp.cursomc.domain.Endereco;
import com.livapp.cursomc.domain.Estado;
import com.livapp.cursomc.domain.ItemPedido;
import com.livapp.cursomc.domain.Pagamento;
import com.livapp.cursomc.domain.PagamentoComBoleto;
import com.livapp.cursomc.domain.PagamentoComCartao;
import com.livapp.cursomc.domain.Pedido;
import com.livapp.cursomc.domain.Produto;
import com.livapp.cursomc.domain.enums.EstadoPagamento;
import com.livapp.cursomc.domain.enums.TipoCliente;
import com.livapp.cursomc.repositories.CategoriaRepository;
import com.livapp.cursomc.repositories.CidadeRepository;
import com.livapp.cursomc.repositories.ClienteRepository;
import com.livapp.cursomc.repositories.EnderecoRepository;
import com.livapp.cursomc.repositories.EstadoRepository;
import com.livapp.cursomc.repositories.ItemPedidoRepository;
import com.livapp.cursomc.repositories.PagamentoRepository;
import com.livapp.cursomc.repositories.PedidoRepository;
import com.livapp.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//Objetos
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
	
		//Objetos
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 20.00);
		
		//Objetos
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		//Objetos
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		//Objetos
		Cliente cli1 = new Cliente(null, "Nathalia Valentim", "nathaliavalentim@outlook,com", "10047684502", TipoCliente.PESSOAFISICA);
		
		//Objetos
		Endereco e1 = new Endereco(null, "Rua Arthur Bernardes", "150", "Apto 402", "Martins", "38400368", cli1, c1); 
		
		//Objetos
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("22/02/2021 18:20"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("21/02/2021 11:00"), cli1, e1);
		
		//Objetos
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("22/02/2020 12:00"), null);
		ped2.setPagamento(pagto2);
		
		//Objetos
		ItemPedido ip1 = new ItemPedido(ped1, p3, 0.00, 1, 20.00);
		ItemPedido ip2 = new ItemPedido(ped2, p2, 0.00, 2, 800.00);
		ItemPedido ip3 = new ItemPedido(ped2, p1, 0.00, 3, 2000.00);
		
		
		//Categorias associadas aos seus produtos
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		//Produtos associados as suas categorias
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		//Estado associados as suas cidades
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		//Telefones do cliente
		cli1.getTelefones().addAll(Arrays.asList("34991910795"));
		
		//Endereços do cliente
		cli1.getEnderecos().addAll(Arrays.asList(e1));
				
		//Associando os pedidos ao cliente
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		//Associando cada pedido com os itens dele
		ped1.getItens().addAll(Arrays.asList(ip1));
		ped2.getItens().addAll(Arrays.asList(ip2, ip3));
		
		//Associando cada item de pedido aos produtos
		p1.getItens().addAll(Arrays.asList(ip3));
		p2.getItens().addAll(Arrays.asList(ip2));
		p3.getItens().addAll(Arrays.asList(ip1));
		
	//salvando
	categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
	produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
	estadoRepository.saveAll(Arrays.asList(est1,est2));
	cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
	clienteRepository.saveAll(Arrays.asList(cli1));
	enderecoRepository.saveAll(Arrays.asList(e1));
	pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
	pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
	itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	
	
	
	}
	
	
	

}

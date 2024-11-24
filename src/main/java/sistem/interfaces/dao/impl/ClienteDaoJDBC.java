package sistem.interfaces.dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistem.db.DB;
import sistem.db.DbException;
import sistem.entities.Animal;
import sistem.entities.Cliente;
import sistem.exceptions.DomainException;
import sistem.interfaces.dao.ClienteDAO;
import sistem.vet.App;

public class ClienteDaoJDBC implements ClienteDAO {
	
	private Connection conn;
	private Scanner in;
	
	public ClienteDaoJDBC(Connection conn, Scanner in) {
		this.conn = conn;
		this.in = in;
	}
	
	public ClienteDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Cliente cli) throws DbException {
		PreparedStatement st = null;
		ResultSet rs = null; 
		
		int id = 0;
		
		try {
			conn.setAutoCommit(false);
			
			st = conn.prepareStatement(
					"INSERT INTO cliente (nome, cpfCnpj, telefone, email, qtdAnimal, orcamentoTotal,"
					+ " formaPagamento, parcelas, statusPagamento, situacao, dataCadastro, observacao)"
					+ "VALUES (?, ? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, cli.getNome()); 			//=> Nome
			st.setString(2, cli.getCpf()); 				//=> Cpf/Cnpj
			st.setString(3, cli.getTelefone()); 		//=> Telefone
			st.setString(4, cli.getEmail()); 			//=> Telefone
			st.setInt(5, cli.qtdAnimal); 	   			//=> QtdAnimal
			st.setDouble(6, cli.getOrcamentoTotal());	//=> OrçamentoTotal
			st.setInt(7, cli.getIntFormaPagamento());	//=> Forma de Pagamento
			st.setInt(8, cli.parcelaPagamento);			//=> Parcelas
			st.setInt(9, cli.getIntStatusPagamento());	//=> Status do Pagamento
			st.setInt(10, cli.getIntSituacao());		//=> Situação do Trabalho
			st.setTimestamp(11, Timestamp.valueOf(cli.getDataCadastro())); //=> Data de Cadastro
			st.setString(12, cli.getObservacao()); 		//=> Telefone
			
			int RowsAffected = st.executeUpdate();
			
			if(RowsAffected > 0) {
				rs = st.getGeneratedKeys();
				
				while(rs.next()) {
					id = rs.getInt(1);
					cli.setId(id);
				}
			}
			
			else {
				conn.rollback();
				throw new DbException("Erro Inesperado. Nenhuma linha adicionada.");
			}
			
			for(int i = 0; i < cli.qtdAnimal; i++) {
				st = conn.prepareStatement(
						"INSERT INTO animal (idCliente, nome, sexo, tipo, status, emergencia, orcamento, medicamentos, observacoes, servicos ) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				
				st.setInt	(1, id);								   		//=> IdCliente
				st.setString(2, cli.animal[i].getNome());			  	   //=> Nome Pet
				st.setInt	(3, cli.animal[i].getIntSexo());		 	  //=> Sexo Pet
				st.setInt	(4, cli.animal[i].getIntTipo());			 //=> TipoPet
				st.setInt	(5, cli.animal[i].getIntSituacao());		//=> TipoPet
				st.setInt	(6, cli.animal[i].getIntEmergencia()); 	   //=> É emergência
					  //=> Pet Internado
				st.setDouble(7, cli.animal[i].getOrcamento());	 	 //=> Orçamento pet
				st.setString(8, cli.animal[i].getMedicamentos());	//=> Medicamentos
				st.setString(9, cli.animal[i].getObservacoes());  //=> Observacoes
				
				String intString = Arrays.toString(cli.animal[i].getServicos());
				st.setString(10, intString);
				
				st.executeUpdate();
				conn.commit();
			}
		}
		catch(SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				
				System.out.println("Erro: Não foi possível realizar o RollBack.\n Stacktrace: " + e1.getMessage());
			}
			throw new DbException(e.getMessage());
		}
		finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}

	@Override
	public void update(Cliente cli) {
		PreparedStatement st = null;
		ResultSet rs = null; 
		
		try {
			
			conn.setAutoCommit(true);
			st = conn.prepareStatement(
					"UPDATE cliente SET nome = ?, cpfCnpj = ?, telefone = ?, email = ?, qtdAnimal = ?, orcamentoTotal = ?,"
					+ " formaPagamento = ?, parcelas = ?, statusPagamento = ?, situacao = ?, dataCadastro = ?, dataEncerramento = ?, "
					+ "observacao = ? "
					+ "WHERE id = ?");
			
			st.setString(1, cli.getNome()); 			//=> Nome
			st.setString(2, cli.getCpf()); 				//=> Cpf/Cnpj
			st.setString(3, cli.getTelefone()); 		//=> Telefone
			st.setString(4, cli.getEmail()); 		//=> Telefone
			st.setInt(5, cli.qtdAnimal); 	   			//=> QtdAnimal
			st.setDouble(6, cli.getOrcamentoTotal());	   	//=> OrçamentoTotal
			st.setInt(7, cli.getIntFormaPagamento());	//=> Forma de Pagamento
			st.setInt(8, cli.parcelaPagamento);			//=> Parcelas
			st.setInt(9, cli.getIntStatusPagamento());	//=> Status do Pagamento
			st.setInt(10, cli.getIntSituacao());			//=> Situação do Trabalho
			st.setTimestamp(11, Timestamp.valueOf(cli.getDataCadastro())); //=> Data de Cadastro
			
			Timestamp dataEnd = cli.getDataFinalizado() == null ? null : Timestamp.valueOf(cli.getDataFinalizado());
			st.setTimestamp(12, dataEnd); //=> Data de Cadastro
			
			st.setString(13, cli.getObservacao());
			st.setInt(14, cli.getId()); 				//=> Id do Cliente
			
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			conn.setAutoCommit(true);
			st = conn.prepareStatement("DELETE FROM cliente WHERE id = ?");
			st.setInt(1, id);
			st.executeUpdate();
			
			st = conn.prepareStatement("DELETE FROM animal WHERE idCliente = ?");
			st.setInt(1, id);
			st.executeUpdate();
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}

	@Override
	public Cliente findById(Integer id) throws DomainException {
		
		PreparedStatement st = null;
		ResultSet rs = null; 
		
		try {
			conn.setAutoCommit(true);
			st = conn.prepareStatement(
					"SELECT cliente.*, animal.* "
					+ "FROM cliente INNER JOIN animal "
					+ "ON cliente.id = animal.idCliente "
					+ "WHERE cliente.id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				
				Cliente cliente = instCliente(rs);		
				
				for(int i = 0; i < cliente.qtdAnimal; i++) {
					cliente.animal[i] = instAnimal(rs, cliente, i);
					
					if(!rs.next()) {
						break;
					}
				}
				
				return cliente;
				
			}
			
			return null;
					
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	@Override
	public List<Cliente> findAll() throws DomainException {
		PreparedStatement st = null;
		ResultSet rs = null; 
		
		try {
			st = conn.prepareStatement(
					"SELECT cliente.*, animal.* "
					+ "FROM cliente INNER JOIN animal "
					+ "ON cliente.id = animal.idCliente "
					+ "ORDER BY id DESC");
			
			rs = st.executeQuery();
			
			List<Cliente> listaClientes = new ArrayList<Cliente>();
			int rows = 0;
			
			while(rs.next()) {
				
				Cliente cliente = instCliente(rs);	
				rows++;
				
				for(int i = 0; i < cliente.qtdAnimal; i++) {
					cliente.animal[i] = instAnimal(rs, cliente, i);
					
					if(i == cliente.qtdAnimal - 1) {
						continue;
					}
					
					if(!rs.next()) {
						break;
					}
					

				}
				
				listaClientes.add(cliente);
			}
			
			//System.out.println("Clientes Encontrados: " + rows + "\n");
			return listaClientes;
					
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	public List<Cliente> findAll(int limit) throws DomainException {
		PreparedStatement st = null;
		ResultSet rs = null; 
		
		try {
			st = conn.prepareStatement(
					"SELECT cliente.*, animal.* "
					+ "FROM cliente INNER JOIN animal "
					+ "ON cliente.id = animal.idCliente "
					+ "ORDER BY id DESC LIMIT " + limit);
			
			rs = st.executeQuery();
			
			List<Cliente> listaClientes = new ArrayList<Cliente>();
			int rows = 0;
			
			while(rs.next()) {
				
				Cliente cliente = instCliente(rs);	
				rows++;
				
				for(int i = 0; i < cliente.qtdAnimal; i++) {
					cliente.animal[i] = instAnimal(rs, cliente, i);
					
					if(i == cliente.qtdAnimal - 1) {
						continue;
					}
					
					if(!rs.next()) {
						break;
					}
					

				}
				
				listaClientes.add(cliente);
			}
			
			//System.out.println("Clientes Encontrados: " + rows + "\n");
			return listaClientes;
					
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	
	@Override
	public List<Cliente> findActive() throws DomainException {
		PreparedStatement st = null;
		ResultSet rs = null; 
		
		try {
			st = conn.prepareStatement(
					"SELECT cliente.*, animal.* "
					+ "FROM cliente INNER JOIN animal "
					+ "ON cliente.id = animal.idCliente "
					+ "WHERE situacao = 0 "
					+ "ORDER BY id DESC");
			
			rs = st.executeQuery();
			
			List<Cliente> listaClientes = new ArrayList<Cliente>();
			int rows = 0;
			
			while(rs.next()) {
				
				Cliente cliente = instCliente(rs);	
				rows++;
				
				for(int i = 0; i < cliente.qtdAnimal; i++) {
					cliente.animal[i] = instAnimal(rs, cliente, i);
					
					if(i == cliente.qtdAnimal - 1) {
						continue;
					}
					
					if(!rs.next()) {
						break;
					}
					

				}
				
				listaClientes.add(cliente);
			}
			
			//System.out.println("Clientes Encontrados: " + rows + "\n");
			return listaClientes;
					
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	public List<Cliente> findByNameOrCpf(String text) throws DomainException{
		PreparedStatement st = null;
		ResultSet rs = null; 
		
		try {
			conn.setAutoCommit(true);
			st = conn.prepareStatement(
					"SELECT cliente.*, animal.* "
					+ "FROM cliente "
					+ "INNER JOIN animal "
					+ "ON cliente.id = animal.idCliente "
					+ "WHERE (cliente.nome LIKE '%"+ text +"%' OR cliente.cpfCnpj LIKE '%"+ text +"%') "
					+ "ORDER BY dataCadastro");
			
			rs = st.executeQuery();
			
			List<Cliente> listaClientes = new ArrayList<Cliente>();
			int rows = 0;
			
			while(rs.next()) {
	
				Cliente cliente = instCliente(rs);	
				rows++;
				
				for(int i = 0; i < cliente.qtdAnimal; i++) {
					cliente.animal[i] = instAnimal(rs, cliente, i);
					
					if(i == cliente.qtdAnimal - 1) {
						continue;
					}
					
					if(!rs.next()) {
						break;
					}
				}
				
				listaClientes.add(cliente);
			}
			

			if(rows == 0) {
				throw new DomainException("Nenhum cliente encontrado com os dados fornecidos!");
			}
			
			return listaClientes;
					
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Cliente instCliente(ResultSet rs) throws SQLException, DomainException {
		Cliente cliente = new Cliente();
		
		cliente.setId(rs.getInt("id"));
		cliente.setNome(rs.getString("nome"));
		cliente.setTelefone(rs.getString("telefone"));
		cliente.setEmail(rs.getString("email"));
		cliente.setCpf(rs.getString("cpfCnpj"));
		cliente.setOrcamentoTotal(rs.getDouble("orcamentoTotal"));
		cliente.setParcela(rs.getInt("parcelas"));
		cliente.setFormaPagamento(rs.getInt("formaPagamento"));
		cliente.setStatusPagamento(rs.getInt("statusPagamento"));
		cliente.setSituacao(rs.getInt("situacao"));
		cliente.setObservacao(rs.getString("observacao"));
		cliente.setDataCadastro(rs.getObject("dataCadastro", LocalDateTime.class));
		cliente.setDataFinalizado(rs.getObject("dataEncerramento", LocalDateTime.class));
		
		cliente.qtdAnimal = rs.getInt("qtdAnimal");
		cliente.animal = new Animal[cliente.qtdAnimal];
		
		return cliente;
	}
	
	private Animal instAnimal(ResultSet rs, Cliente cliente, int i) throws SQLException, DomainException {
		Animal animal = new Animal();
		animal.setId(rs.getInt("animal.idAnimal"));
		animal.setIdCliente(rs.getInt("animal.idCliente"));
		animal.setNome(rs.getString("animal.nome"));
		animal.setSexo(rs.getInt("animal.sexo"));
		animal.setTipo(rs.getInt("animal.tipo"));
		animal.setSituacao(rs.getInt("animal.status"));
		animal.setEmergencia(rs.getInt("animal.emergencia"));
		animal.setInternado(rs.getInt("animal.internado"));
		animal.setOrcamento(rs.getDouble("animal.orcamento"));
		animal.setObservacoes(rs.getString("animal.observacoes"));
		animal.setMedicamentos(rs.getString("animal.medicamentos"));
		animal.setDiagnostico(rs.getString("animal.diagnostico"));
		animal.setDataAlta(rs.getObject("animal.data_alta", LocalDateTime.class));
		animal.setDataObito(rs.getObject("animal.data_obito", LocalDateTime.class));
		String intStr = rs.getString("animal.servicos");
		
		intStr = intStr.replaceAll(" ", "");
		intStr = intStr.substring(1, intStr.length() - 1);

		String[] parts = intStr.split(",");
		int[] arr = new int[parts.length];
		
		int j = 0;
		for (String part : parts) {
			
		    part = part.trim();
		    
		    if (part.startsWith("-")) {
		        arr[j] = -1;
		        j++;
		        continue;
		    }
		    
		    arr[j] = Integer.parseInt(part);
		    j++;
		}
		
		animal.servicos = arr;
		
		return animal;
	}

}

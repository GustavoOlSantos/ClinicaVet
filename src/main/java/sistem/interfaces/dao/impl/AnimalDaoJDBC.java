package sistem.interfaces.dao.impl;

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

import sistem.db.DB;
import sistem.db.DbException;
import sistem.entities.Animal;
import sistem.entities.Cliente;
import sistem.exceptions.DomainException;
import sistem.interfaces.dao.AnimalDAO;

public class AnimalDaoJDBC implements AnimalDAO {
	
	private Connection conn;
	private Scanner in;
	
	public AnimalDaoJDBC(Connection conn, Scanner in) {
		this.conn = conn;
		this.in = in;
	}
	
	public AnimalDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Animal pet) {
		PreparedStatement st = null;
		ResultSet rs = null; 
		
		try {
			conn.setAutoCommit(false);
				st = conn.prepareStatement(
						"INSERT INTO animal (idCliente, nome, sexo, tipo, status, emergencia, internado, orcamento, medicamentos, observacoes servicos ) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				
				st.setInt	(1, pet.getIdCliente());				   //=> IdCliente
				st.setString(2, pet.getNome());			  //=> Nome Pet
				st.setInt	(3, pet.getIntSexo());		 //=> Sexo Pet
				st.setInt	(4, pet.getIntTipo());		//=> TipoPet
				st.setInt	(5, pet.getIntSituacao());	//=> SituacaoPet
				st.setInt	(6, pet.getIntEmergencia()); //=> É emergência
				st.setInt	(7, pet.getIntInternado()); //=> Pet Internado
				st.setDouble(8, pet.getOrcamento());	 //=> Orçamento pet
				st.setString(9, pet.getMedicamentos());	 //=> Orçamento pet
				st.setString(10, pet.getObservacoes());	 //=> Orçamento pet
				String intString = Arrays.toString(pet.getServicos());
				st.setString(11, intString);
				
				st.executeUpdate();
				conn.commit();
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
	public void update(Animal pet) {
		PreparedStatement st = null;
		ResultSet rs = null; 
		
		try {
			
			conn.setAutoCommit(true);
			st = conn.prepareStatement(
					"UPDATE animal SET nome = ?, sexo = ?, tipo = ?, emergencia = ?, status = ?, orcamento = ?, "
					+ "observacoes = ?, medicamentos = ?, servicos = ?, data_alta = ?, data_obito = ? "
					+ "WHERE idCliente = ? AND idAnimal = ?");
			
			st.setString(1, pet.getNome());			 //=> Nome Pet
			st.setInt	(2, pet.getIntSexo());		 //=> Sexo Pet
			st.setInt	(3, pet.getIntTipo());		 //=> TipoPet
			st.setInt	(4, pet.getIntEmergencia()); //=> É emergência
			st.setInt	(5, pet.getIntSituacao());
			st.setDouble(6, pet.getOrcamento());	//=> Orçamento pet
			st.setString(7, pet.getObservacoes());
			st.setString(8, pet.getMedicamentos());
			
			String intString = Arrays.toString(pet.getServicos());
			st.setString(9, intString);
			
			Timestamp dataAlta = pet.getDataAlta() == null ? null : Timestamp.valueOf(pet.getDataAlta());
			st.setTimestamp(10, dataAlta);
			
			Timestamp dataObito = pet.getDataObito() == null ? null : Timestamp.valueOf(pet.getDataObito());
			st.setTimestamp(11, dataObito);
			
			st.setInt	(12, pet.getIdCliente());	//=> IdCliente
			st.setInt	(13, pet.getId());			//=> IdPet
			
			st.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new DbException(e.getMessage());
		}
		finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}

	@Override
	public void deleteById(Integer id, Integer idCliente) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			conn.setAutoCommit(true);
			st = conn.prepareStatement("DELETE FROM animal WHERE idAnimal = ? AND idCliente = ?");
			st.setInt(1, id);
			st.setInt(2, idCliente);
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
	public Animal findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null; 
		
		try {
			conn.setAutoCommit(true);
			st = conn.prepareStatement(
					"SELECT * "
					+ "FROM animal "
					+ "WHERE idAnimal = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				
				Animal animal = instAnimal(rs);		
				return animal;
				
			}
			
			return null;
					
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		} catch (DomainException e) {
			throw new DbException(e.getMessage());
		}
		finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
        
         @Override
    public List<Animal> findAll() throws DomainException{
            PreparedStatement st = null;
		ResultSet rs = null; 

		try {
			st = conn.prepareStatement(
					"SELECT * "
					+ "FROM animal ");



			rs = st.executeQuery();

			List<Animal> listaAnimal = new ArrayList<>();
			int rows = 0;

			while(rs.next()) {

				Animal animal = instAnimal(rs);	

				listaAnimal.add(animal);
			}

			//System.out.println("Clientes Encontrados: " + rows + "\n");
			return listaAnimal;

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
	public List<Animal> findInternados() throws DomainException {
		PreparedStatement st = null;
		ResultSet rs = null; 
		
		try {
			st = conn.prepareStatement(
					"SELECT * "
					+ "FROM animal "
					+ "WHERE status = 7");
			
			rs = st.executeQuery();
			
			List<Animal> listaAnimal = new ArrayList<>();
			int rows = 0;
			
			while(rs.next()) {
				
				Animal animal = instAnimal(rs);	
							
				listaAnimal.add(animal);
			}
			
			//System.out.println("Clientes Encontrados: " + rows + "\n");
			return listaAnimal;
					
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
	public List<Animal> findByName(String nome) throws DomainException {
      PreparedStatement st = null;
      ResultSet rs = null; 
		
      try {
		conn.setAutoCommit(true);
		st = conn.prepareStatement(
				"SELECT * "
				+ "FROM animal "
				+ "WHERE (animal.nome LIKE '%"+ nome +"%') "
				+ "ORDER BY idAnimal");
		
		rs = st.executeQuery();
                    
                    List<Animal> listaAnimal = new ArrayList<>();
		int rows = 0;
		
		while(rs.next()) {
			
			Animal animal = instAnimal(rs);	
			rows++;
						
			listaAnimal.add(animal);
		}
		
		if(rows == 0) {
			throw new DomainException("Nenhum animal com o nome fornecido encontrado.");
		}
		
		return listaAnimal;
					
      }
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	public List<Animal> findByNameInternado(String nome) throws DomainException {
	      PreparedStatement st = null;
	      ResultSet rs = null; 
			
	      try {
			conn.setAutoCommit(true);
			st = conn.prepareStatement(
					"SELECT * "
					+ "FROM animal "
					+ "WHERE (animal.nome LIKE '%"+ nome +"%') AND status = 7"
					+ "ORDER BY idAnimal");
			
			rs = st.executeQuery();
	                    
	                    List<Animal> listaAnimal = new ArrayList<>();
			int rows = 0;
			
			while(rs.next()) {
				
				Animal animal = instAnimal(rs);	
				rows++;
							
				listaAnimal.add(animal);
			}
			
			if(rows == 0) {
				throw new DomainException("Nenhum animal com o nome fornecido encontrado.");
			}
			
			return listaAnimal;
						
	      }
			catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
			finally{
				DB.closeStatement(st);
				DB.closeResultSet(rs);
			}
		}

	public List<Animal> findByCliente(int id) throws DomainException {
	      PreparedStatement st = null;
	      ResultSet rs = null; 
			
	      try {
			conn.setAutoCommit(true);
			st = conn.prepareStatement(
					"SELECT * "
					+ "FROM animal "
					+ "WHERE idCliente = " + id
					+ " ORDER BY idAnimal");
			
			rs = st.executeQuery();
	                    
            List<Animal> listaAnimal = new ArrayList<>();
			int rows = 0;
			
			while(rs.next()) {
				
				Animal animal = instAnimal(rs);	
				rows++;
							
				listaAnimal.add(animal);
			}
			
			if(rows == 0) {
				throw new DomainException("Nenhum animal encontrado para esse cliente.");
			}
			
			return listaAnimal;
						
	      }
			catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
			finally{
				DB.closeStatement(st);
				DB.closeResultSet(rs);
			}
		}

	
	private Animal instAnimal(ResultSet rs) throws SQLException, DomainException {
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

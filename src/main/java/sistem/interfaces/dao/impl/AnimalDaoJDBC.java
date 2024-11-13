package sistem.interfaces.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Animal pet) {
		PreparedStatement st = null;
		ResultSet rs = null; 
		
		try {
			
			conn.setAutoCommit(true);
			st = conn.prepareStatement(
					"UPDATE animal SET nome = ?, sexo = ?, tipo = ?, emergencia = ?, status = ?, orcamento = ?, "
					+ "observacoes = ? "
					+ "WHERE idCliente = ? AND idAnimal = ?");
			
			st.setString(1, pet.getNome());			 //=> Nome Pet
			st.setInt	(2, pet.getIntSexo());		 //=> Sexo Pet
			st.setInt	(3, pet.getIntTipo());		 //=> TipoPet
			st.setInt	(4, pet.getIntEmergencia()); //=> É emergência
			st.setInt	(5, pet.getIntSituacao());
			st.setDouble(6, pet.getOrcamento());	//=> Orçamento pet
			st.setString(7, pet.getObservacoes());
			st.setInt	(8, pet.getIdCliente());	//=> IdCliente
			st.setInt	(9, pet.getId());			//=> IdPet
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
		// TODO Auto-generated method stub
		return null;
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
      public List<Animal> findByName(String nome) throws DomainException{
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

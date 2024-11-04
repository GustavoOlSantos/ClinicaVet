package sistem.interfaces.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	public void insert(Animal cli) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Animal cli) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Animal findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override	
	public List<Animal> findAll() throws DomainException {
		PreparedStatement st = null;
		ResultSet rs = null; 
		
		try {
			st = conn.prepareStatement(
					"SELECT * "
					+ "FROM animal "
					+ "WHERE internado = 0");
			
			rs = st.executeQuery();
			
			List<Animal> listaAnimal = new ArrayList<Animal>();
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
		animal.setNome(rs.getString("animal.nome"));
		animal.setSexo(rs.getInt("animal.sexo"));
		animal.setTipo(rs.getInt("animal.tipo"));
		animal.setEmergencia(rs.getInt("animal.emergencia"));
		animal.setInternado(rs.getInt("animal.internado"));
		animal.setOrcamento(rs.getDouble("animal.orcamento"));
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

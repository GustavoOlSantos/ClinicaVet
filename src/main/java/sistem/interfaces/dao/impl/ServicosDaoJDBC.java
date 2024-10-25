package sistem.interfaces.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import sistem.db.DB;
import sistem.db.DbException;
import sistem.entities.Animal;
import sistem.entities.Cliente;
import sistem.entities.Servicos;
import sistem.exceptions.DomainException;
import sistem.interfaces.dao.ServicosDAO;

public class ServicosDaoJDBC implements ServicosDAO {
	
	private Connection conn;
	
	public ServicosDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Servicos> findAll() throws DomainException {
		PreparedStatement st = null;
		ResultSet rs = null; 
		
		try {
			st = conn.prepareStatement(
					"SELECT * FROM servicos ORDER BY id");
			
			rs = st.executeQuery();
			
			List<Servicos> listaServ = new ArrayList<Servicos>();
			int rows = 0;
			
			while(rs.next()) {
				
				Servicos serv = instServico(rs);	
				rows++;
				
				listaServ.add(serv);
			}
			
			return listaServ;
					
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Servicos instServico(ResultSet rs) throws SQLException, DomainException {
		Servicos servico = new Servicos(
		rs.getInt("id"),
		rs.getString("nome"),
		rs.getDouble("valor")
		);
		
		return servico;
	}

}

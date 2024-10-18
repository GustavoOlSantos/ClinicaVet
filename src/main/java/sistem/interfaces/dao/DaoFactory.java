package sistem.interfaces.dao;

import java.util.Scanner;

import sistem.db.DB;
import sistem.interfaces.dao.impl.AnimalDaoJDBC;
import sistem.interfaces.dao.impl.ClienteDaoJDBC;

public class DaoFactory {
	
	//=> Cria as implementações DAO expondo somente a interface
	public static ClienteDAO createClienteDao() {
		return new ClienteDaoJDBC(DB.getConnection());
	}
	
	public static AnimalDAO createAnimalDao() {
		return new AnimalDaoJDBC();
	}
}

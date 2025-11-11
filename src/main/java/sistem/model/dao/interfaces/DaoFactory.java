package sistem.model.dao.interfaces;

import sistem.model.db.DB;
import sistem.model.dao.impl.AnimalDaoJDBC;
import sistem.model.dao.impl.ClienteDaoJDBC;
import sistem.model.dao.impl.ServicosDaoJDBC;

public class DaoFactory {
	
	//=> Cria as implementações DAO expondo somente a interface
	public static ClienteDAO createClienteDao() {
		return new ClienteDaoJDBC(DB.getConnection());
	}
	
	public static AnimalDAO createAnimalDao() {
		return new AnimalDaoJDBC(DB.getConnection());
	}
	
	public static ServicosDAO createServicosDao() {
		return new ServicosDaoJDBC(DB.getConnection());
	}
}

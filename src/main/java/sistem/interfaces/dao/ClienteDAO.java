package sistem.interfaces.dao;

import java.io.IOException;
import java.util.List;

import sistem.entities.Cliente;
import sistem.exceptions.DomainException;

public interface ClienteDAO {
	
	void insert(Cliente cli) throws IOException;
	void update(Cliente cli);
	void deleteById(Integer id);
	Cliente findById(Integer id) throws DomainException;
	List<Cliente> findAll() throws DomainException;
}

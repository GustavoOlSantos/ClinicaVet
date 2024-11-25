package sistem.interfaces.dao;

import java.io.IOException;
import java.util.List;

import sistem.db.DbException;
import sistem.entities.Cliente;
import sistem.exceptions.DomainException;

public interface ClienteDAO {
	
	void insert(Cliente cli) throws DbException;
	void update(Cliente cli);
	void deleteById(Integer id);
	Cliente findById(Integer id) throws DomainException;
	List<Cliente> findAll() throws DomainException;
	List<Cliente> findAll(int limit) throws DomainException;
	List<Cliente> findActive() throws DomainException;
	List<Cliente> findToday() throws DomainException;
	List<Cliente> findInHome(String nomeTutor, String cpf, String tel, int code) throws DomainException;
	List<Cliente> findInHome(String nomeTutor, String cpf, String tel, String pet, int code) throws DomainException;
	List<Cliente> findByNameOrCpf(String text) throws DomainException;
}

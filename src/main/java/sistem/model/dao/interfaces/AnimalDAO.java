package sistem.model.dao.interfaces;

import java.util.List;

import sistem.model.entities.Animal;
import sistem.model.exceptions.DomainException;

public interface AnimalDAO {
	void insert(Animal cli);
	void update(Animal cli);
	void deleteById(Integer id, Integer idCliente);
	Animal findById(Integer id);
    List<Animal> findAll() throws DomainException; 
	List<Animal> findInternados() throws DomainException;      
    List<Animal> findByName(String nome) throws DomainException;
    List<Animal> findByNameInternado(String nome) throws DomainException;
    List<Animal> findByCliente(int id) throws DomainException;
                
}


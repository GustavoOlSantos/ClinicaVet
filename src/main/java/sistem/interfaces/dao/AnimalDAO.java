package sistem.interfaces.dao;

import java.util.List;

import sistem.entities.Animal;
import sistem.exceptions.DomainException;

public interface AnimalDAO {
	void insert(Animal cli);
	void update(Animal cli);
	void deleteById(Integer id);
	Animal findById(Integer id);
	List<Animal> findAll() throws DomainException;
}

package sistem.interfaces.dao;

import java.util.List;
import sistem.entities.Servicos;
import sistem.exceptions.DomainException;

public interface ServicosDAO {

	List<Servicos> findAll() throws DomainException;
}
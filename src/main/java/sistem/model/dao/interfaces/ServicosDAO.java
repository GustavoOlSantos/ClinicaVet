package sistem.model.dao.interfaces;

import java.util.List;
import sistem.model.entities.Servicos;
import sistem.model.exceptions.DomainException;

public interface ServicosDAO {

	List<Servicos> findAll() throws DomainException;
}
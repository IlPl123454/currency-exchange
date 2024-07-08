package org.plenkovii.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudDao<T, ID> {
    Optional<T> findById(ID id) throws SQLException, ClassNotFoundException;

    List<T> findAll() throws ClassNotFoundException, SQLException;

    T save(T entity) throws SQLException, ClassNotFoundException ;

    Optional<T> update(T entity) throws SQLException, ClassNotFoundException;

    void delete(ID id);
}

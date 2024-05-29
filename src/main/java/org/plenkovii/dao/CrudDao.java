package org.plenkovii.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDao <T>{
    void create(T object) throws ClassNotFoundException, SQLException;
    List<T> readAll() throws ClassNotFoundException, SQLException;
    void update();
    void delete();

}

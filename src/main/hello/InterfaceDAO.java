package hello;

import java.util.List;
public abstract class InterfaceDAO {
    public abstract void save(BaseModele B) throws Exception;
    public abstract List<BaseModele> findAll() throws Exception;
    public abstract BaseModele findById(int id) throws Exception;
    public abstract void delete(int id) throws Exception ;
    public abstract void update(BaseModele m) throws Exception ;
}

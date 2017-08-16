package dao;

import models.Store;
import models.Weed;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

/**
 * Created by Guest on 8/16/17.
 */
public class Sql2oStoreDao implements StoreDao{

    private final Sql2o sql2o;

    public Sql2oStoreDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
    @Override
    public void add(Store store) {
        String sql = "INSERT INTO stores (storeName) VALUES (:storeName)"; //raw sql
        try(Connection con = sql2o.open()){ //try to open a connection
            int id = (int) con.createQuery(sql) //make a new variable
                    .bind(store) //map my argument onto the query so we can use information from it
                    .executeUpdate() //run it all
                    .getKey(); //int id is now the row number (row “key”) of db
            store.setId(id); //update object to set id now from database
        } catch (Sql2oException ex) {
            System.out.println(ex); //oops we have an error!
        }
    }

    @Override
    public List<Store> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM store") //raw sql
                    .executeAndFetch(Store.class); //fetch a list
        }
    }
    ;
    @Override
    public Store findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM store WHERE id = :id")
                    .addParameter("id", id) //key/value pair, key must match above
                    .executeAndFetchFirst(Store.class); //fetch an individual item
        }
    }
    @Override
    public void update(int id, String newStoreName){
        String sql = "UPDATE store SET name = :name WHERE id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("storeName", newStoreName)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteStoreById(int storeId) {
        String sql = "DELETE from store WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", storeId)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
    @Override
    public void clearAllStore() {
        String sql = "DELETE from store";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }

    }
    @Override
    public List<Weed> getAllWeedByStore(int storeId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM weed WHERE storeId = :storeId")
                    .addParameter("storeId", storeId)//raw sql
                    .executeAndFetch(Weed.class); //fetch a list
        }
    }


}

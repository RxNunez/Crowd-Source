package dao;

import models.Weed;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

/**
 * Created by Guest on 8/16/17.
 */

public class Sql2oWeedDao implements WeedDao {

    private final Sql2o sql2o;

    public Sql2oWeedDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Weed weed) {
        String sql = "INSERT INTO weed (weedname, strain, origin, description, storeid) VALUES (:weedname, :strain, :origin, :description, :storeid)"; //raw sql
        try(Connection con = sql2o.open()){ //try to open a connection
            int id = (int) con.createQuery(sql) //make a new variable
                    .addParameter("weedname", weed.getWeedName())
                    .addParameter("strain", weed.getStrain())
                    .addParameter("origin", weed.getOrigin())
                    .addParameter("description", weed.getDescription())
                    .addParameter("storeid", weed.getStoreId())
                    .addColumnMapping("WEEDNAME", "weedName")
                    .addColumnMapping("STRAIN", "strain")
                    .addColumnMapping("ORIGIN", "origin")
                    .addColumnMapping("DESCRIPTION", "description")
                    .addColumnMapping("STOREID", "storeId")
                    .executeUpdate() //run it all
                    .getKey(); //int id is now the row number (row “key”) of db
            weed.setId(id); //update object to set id now from database
        } catch (Sql2oException ex) {
            System.out.println(ex); //oops we have an error!
        }
    }

    @Override
    public List<Weed> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM weed") //raw sql
                    .executeAndFetch(Weed.class); //fetch a list
        }
        }
    @Override
    public Weed findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM weed WHERE id = :id")
                    .addParameter("id", id) //key/value pair, key must match above
                    .executeAndFetchFirst(Weed.class); //fetch an individual item
        }
    }

    @Override
    public void update(int id, String newWeedName, String newDescription, String newStrain, String newOrigin, int newStoreId){
        String sql = "UPDATE weed SET (weedname, strain, origin, description, storeid) = (:weedname, :strain, :origin, :description, :storeid) WHERE id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("weedname", newWeedName)
                    .addParameter("strain", newStrain)
                    .addParameter("origin", newOrigin)
                    .addParameter("description", newDescription)
                    .addParameter("storeid", newStoreId)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from weed WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
    @Override
    public void clearAllWeed() {
        String sql = "DELETE from weed";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}

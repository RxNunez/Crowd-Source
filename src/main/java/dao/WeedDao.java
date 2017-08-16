package dao;

import models.Weed;

import java.util.List;

/**
 * Created by Guest on 8/16/17.
 */
public interface WeedDao {

   //create
    void add (Weed weed);
    //read
    List<Weed> getAll();

    //find

    Weed findById(int id);

    //update
    void update(int id, String weedName, String description, String strain, String origin, int storeId);
    //delete
    void deleteById(int id);

    void clearAllWeed();

}


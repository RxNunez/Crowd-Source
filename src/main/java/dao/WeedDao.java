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

    Weed findById(int id);
    //update
    void update(int id, String content, int storeId);
    //delete
    void deleteById(int id);

    void clearAllWeed();

}


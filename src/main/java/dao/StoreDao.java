package dao;

import models.Store;
import models.Weed;

import java.util.List;

/**
 * Created by Guest on 8/16/17.
 */
public interface StoreDao {

    //create
    void add (Store store);

    //read
    List<Store> getAll();
    List<Weed> getAllWeedByStore(int storeId);

    Store findById(int id);

    //update
    void update(int id, String name);

    //delete
    void deleteById(int id);
    void deleteStoreById(int storeId);
    void clearAllStore();
}

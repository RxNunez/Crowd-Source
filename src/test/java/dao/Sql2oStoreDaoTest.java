package dao;

import models.Store;
import models.Weed;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;


import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

/**
 * Created by Guest on 8/16/17.
 */
public class Sql2oStoreDaoTest {

    private Sql2oStoreDao storeDao; //ignore me for now
    private Sql2oWeedDao weedDao;
    private Connection conn; //must be sql2o class conn

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        storeDao = new Sql2oStoreDao(sql2o); //ignore me for now
        weedDao = new Sql2oWeedDao(sql2o);
        //keep connection open through entire test so it does not get erased.
        conn = sql2o.open();

    }

    @After
    public void tearDown() throws Exception {
            conn.close();
    }

    @Test
    public void addingCourseSetsId() throws Exception {
        Store store = setupNewStore();
        int originalStoreId = store.getId();
        storeDao.add(store);
        assertNotEquals(originalStoreId, store.getId()); //how does this work?
    }

    @Test
    public void existingStoreCanBeFoundById() throws Exception {
        Store store = setupNewStore();
        storeDao.add(store); //add to dao (takes care of saving)
        Store foundStore = storeDao.findById(store.getId()); //retrieve
        assertEquals(store, foundStore); //should be the same
    }

    @Test
    public void addedStoreAreReturnedFromgetAll() throws Exception {
        Store store= setupNewStore();
        storeDao.add(store);
        assertEquals(1, storeDao.getAll().size());
    }

    @Test
    public void noStoreReturnsEmptyList() throws Exception {
        assertEquals(0, storeDao.getAll().size());
    }


    @Test
    public void deleteByIdDeletesCorrectStore() throws Exception {
        Store store = setupNewStore();
        storeDao.add(store);
        storeDao.deleteStoreById(store.getId());
        assertEquals(0, storeDao.getAll().size());
    }

    @Test
    public void clearAllClearsAll() throws Exception {
        Store store = setupNewStore();
        Store otherStore = new Store("Hogs");
        storeDao.add(store);
        storeDao.add(otherStore);
        int daoSize = storeDao.getAll().size();
        storeDao.clearAllStore();
        assertTrue(daoSize > 0 && daoSize > storeDao.getAll().size()); //this is a little overcomplicated, but illustrates well how we might use `assertTrue` in a different way.
    }

    @Test
    public void getAllWeedByStoreReturnsWeedsCorrectly() throws Exception {
        Store store = setupNewStore();
        storeDao.add(store);
        int storeId = store.getId();
        Weed newWeed = new Weed("Boss OG","","","", storeId);
        Weed otherWeed = new Weed("Jedi Kush","","","", storeId);
        Weed thirdWeed = new Weed("Cinderella","","","", storeId);
        weedDao.add(newWeed);
        weedDao.add(otherWeed); //we are not adding task 3 so we can test things precisely.


        assertTrue(storeDao.getAllWeedByStore(storeId).size() == 2);
        assertTrue(storeDao.getAllWeedByStore(storeId).contains(newWeed));
        assertTrue(storeDao.getAllWeedByStore(storeId).contains(otherWeed));
        assertFalse(storeDao.getAllWeedByStore(storeId).contains(thirdWeed)); //things are accurate!
    }



    public Store setupNewStore(){
        return new Store("Louigi's");
    }

}
package dao;

import models.Weed;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Guest on 8/16/17.
 */
public class Sql2oWeedDaoTest {

    private Sql2oWeedDao weedDao; //ignore me for now
    private Connection conn; //must be sql2o class conn

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        weedDao = new Sql2oWeedDao(sql2o); //ignore me for now

        //keep connection open through entire test so it does not get erased.
        conn = sql2o.open();

    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingCourseSetsId() throws Exception {
        Weed weed = new Weed("Boss OG","","","",1);
        int originalWeedId = weed.getId();
        weedDao.add(weed);
        assertNotEquals(originalWeedId, weed.getId()); //how does this work?
    }

    @Test
public void existingWeedCanBeFoundById() throws Exception {
    Weed weed = new Weed("Boss OG","","","",1);
    weedDao.add(weed); //add to dao (takes care of saving)
    Weed foundWeed = weedDao.findById(weed.getId()); //retrieve
    assertEquals(weed, foundWeed); //should be the same
    }

    @Test
    public void addedWeedAreReturnedFromgetAll() throws Exception {
        Weed weed= new Weed("Boss OG","","","",1);
        weedDao.add(weed);
        assertEquals(1, weedDao.getAll().size());
    }

    @Test
    public void noWeedReturnsEmptyList() throws Exception {
        assertEquals(0, weedDao.getAll().size());
    }
    @Test
    public void updateChangesWeedContent() throws Exception {
        String initialDescription = "smooth";
        Weed weed = new Weed ("Boss OG",initialDescription,"","",1);
        weedDao.add(weed);

        weedDao.update(1, "", "", "", "",1);
        Weed updatedWeed = weedDao.findById(weed.getId()); //why do I need to refind this?
        assertNotEquals(initialDescription, updatedWeed.getDescription());
    }

    @Test
    public void deleteByIdDeletesCorrectWeed() throws Exception {
        Weed weed = setupNewWeed();
        weedDao.add(weed);
        weedDao.deleteById(weed.getId());
        assertEquals(0, weedDao.getAll().size());
    }

    @Test
    public void clearAllClearsAll() throws Exception {
        Weed weed = setupNewWeed();
        Weed otherWeed = new Weed("Boss OG","","","",1);
        weedDao.add(weed);
        weedDao.add(otherWeed);
        int daoSize = weedDao.getAll().size();
        weedDao.clearAllWeed();
        assertTrue(daoSize > 0 && daoSize > weedDao.getAll().size()); //this is a little overcomplicated, but illustrates well how we might use `assertTrue` in a different way.
    }

    @Test
    public void storeIdIsReturnedCorrectly() throws Exception {
        Weed weed = setupNewWeed();
        int originalStoreId = weed.getStoreId();
        weedDao.add(weed);
        assertEquals(originalStoreId, weedDao.findById(weed.getId()).getStoreId());
    }

    public Weed setupNewWeed(){
        return new Weed("Boss OG","","","",1);
    }

}
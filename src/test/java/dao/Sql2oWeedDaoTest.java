package dao;

import models.Weed;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
    Weed weed = setupNewWeed();
    int originalWeedId = weed.getId();
    weedDao.add(weed);
    assertNotEquals(originalWeedId, weed.getId()); //how does this work?
}

    public Weed setupNewWeed(){
        return new Weed("Boss OG","","","",1);
    }

}
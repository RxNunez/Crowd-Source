import dao.Sql2oStoreDao;
import dao.Sql2oWeedDao;
import models.Store;
import models.Weed;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

/**
 * Created by Guest on 8/16/17.
 */
public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/crowdsource.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "","");
        Sql2oWeedDao weedDao = new Sql2oWeedDao(sql2o);
        Sql2oStoreDao storeDao = new Sql2oStoreDao(sql2o);

        //show new store form
        get("/stores/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Store> stores = storeDao.getAll(); //refresh list of links for navbar.
            model.put("stores", stores);
            return new ModelAndView(model, "store-form.hbs"); //new
        }, new HandlebarsTemplateEngine());

        //post: process new store form
        post("/stores", (request, response) -> { //new
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            Store newStore = new Store(name);
            storeDao.add(newStore);

            List<Store> stores = storeDao.getAll(); //refresh list of links for navbar.
            model.put("stores", stores);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show an individual store and weed it contains
        get("/stores/:stoId", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfStoreToFind = Integer.parseInt(req.params("stoId")); //new

            List<Store> stores = storeDao.getAll(); //refresh list of links for navbar.
            model.put("stores", stores);

            Store foundStore = storeDao.findById(idOfStoreToFind);
            model.put("store", foundStore);
            List<Weed> allWeedsByStore = storeDao.getAllWeedByStore(idOfStoreToFind);
            model.put("weeds", allWeedsByStore);

            return new ModelAndView(model, "store-details.hbs"); //new
        }, new HandlebarsTemplateEngine());



        // get: show all weeds in all stores
        get("/", (req,res)->{
            Map<String,Object> model = new HashMap<>();
            List<Store> allStores = storeDao.getAll();
            model.put("stores", allStores);
            List<Weed> weeds = weedDao.getAll();
            model.put("weeds",weeds);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //get: delete all weeds
        get("/weeds/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            weedDao.clearAllWeed();
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show new weed form
        get("/weeds/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Store> allStores = storeDao.getAll();
            model.put("stores", allStores);
            return new ModelAndView(model, "weed-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process new weed form
        post("/weeds/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Store> allStores = storeDao.getAll();
            model.put("stores", allStores);
            String weedName = request.queryParams("weedname");
            String description = request.queryParams("description");
            String strain = request.queryParams("strain");
            String origin = request.queryParams("origin");
            Weed newWeed = new Weed(weedName, description, strain, origin, 1); //ignore the hardcoded categoryId
            weedDao.add(newWeed);
            model.put("weed", newWeed);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a stores
        get("/stores/:store_id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int thisId = Integer.parseInt(req.params("store_id"));
            model.put("editCategory", true);
            List<Store> allStores = storeDao.getAll();
            model.put("stores", allStores);
            return new ModelAndView(model, "store-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a weeds and stores it contains
        post("/stores/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfStoreToEdit = Integer.parseInt(req.queryParams("editStoreId"));
            String newStoreName = req.queryParams("newStoreName");
            storeDao.update(storeDao.findById(idOfStoreToEdit).getId(), newStoreName);
            List<Store> stores = storeDao.getAll(); //refresh list of links for navbar.
            model.put("stores", stores);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: delete an individual stores
        get("stores/:store_id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfStoreToDelete = Integer.parseInt(req.params("store_id")); //pull id - must match route segment
            Store deleteStore = storeDao.findById(idOfStoreToDelete); //use it to find task
            storeDao.deleteStoreById(idOfStoreToDelete);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: delete all stores and all weed
        get("/stores/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            weedDao.clearAllWeed();
            storeDao.clearAllStore();

            List<Store> allStores = storeDao.getAll();
            model.put("stores", allStores);

            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show an individual weed
        get("stores/:store_id/weeds/:weeds_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfWeedToFind = Integer.parseInt(req.params("weeds_id")); //pull id - must match route segment
            Weed foundWeed = weedDao.findById(idOfWeedToFind); //use it to find task
            model.put("weed", foundWeed); //add it to model for template to display
            return new ModelAndView(model, "weed-detail.hbs"); //individual task page.
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a weed
        get("/weeds/:id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfWeedToEdit = Integer.parseInt(req.params("id"));
            Weed editWeed = weedDao.findById(idOfWeedToEdit);
            model.put("editWeed", editWeed);
            return new ModelAndView(model, "weed-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process a form to update a weed
        post("/weeds/:id/update", (request, response) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();
            String weedName = request.queryParams("weedname");
            String description = request.queryParams("description");
            String strain = request.queryParams("strain");
            String origin = request.queryParams("origin");
            int idOfWeedToEdit = Integer.parseInt(request.params("id"));
            Weed editWeed = weedDao.findById(idOfWeedToEdit);
            weedDao.update(idOfWeedToEdit,weedName,description,strain,origin,1);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: delete an individual weed
        get("stores/:store_id/weeds/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfWeedToDelete = Integer.parseInt(req.params("id")); //pull id - must match route segment
            Weed deleteWeed = weedDao.findById(idOfWeedToDelete); //use it to find task
            weedDao.deleteById(idOfWeedToDelete);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

    }
}

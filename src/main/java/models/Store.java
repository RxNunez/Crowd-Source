package models;

/**
 * Created by Guest on 8/16/17.
 */
public class Store {

    private String storeName;

    private int id;

    public Store (String storeName){
        this.storeName = storeName;

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Store store = (Store) o;

        if (id != store.id) return false;
        return storeName.equals(store.storeName);
    }

    @Override
    public int hashCode() {
        int result = storeName.hashCode();
        result = 31 * result + id;
        return result;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

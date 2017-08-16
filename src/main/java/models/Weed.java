package models;

/**
 * Created by Guest on 8/16/17.
 */
public class Weed {

    private String weedName;
    private String description;
    private String strain;
    private String origin;
    private int id;
    private int storeId;

    public Weed (String weedName, String description, String strain, String origin, int storeId) {
        this.weedName = weedName;
        this.description = description;
        this.strain = strain;
        this.origin = origin;
        this.storeId =  storeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Weed weed = (Weed) o;

        if (id != weed.id) return false;
        if (storeId != weed.storeId) return false;
        if (weedName != null ? !weedName.equals(weed.weedName) : weed.weedName != null) return false;
        if (description != null ? !description.equals(weed.description) : weed.description != null) return false;
        if (strain != null ? !strain.equals(weed.strain) : weed.strain != null) return false;
        return origin != null ? origin.equals(weed.origin) : weed.origin == null;
    }

    @Override
    public int hashCode() {
        int result = weedName != null ? weedName.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (strain != null ? strain.hashCode() : 0);
        result = 31 * result + (origin != null ? origin.hashCode() : 0);
        result = 31 * result + id;
        result = 31 * result + storeId;
        return result;
    }

    public String getWeedName() {
        return weedName;
    }

    public void setWeedName(String weedName) {
        this.weedName = weedName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStrain() {
        return strain;
    }

    public void setStrain(String strain) {
        this.strain = strain;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

}

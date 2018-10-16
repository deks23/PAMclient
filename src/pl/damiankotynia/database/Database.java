package pl.damiankotynia.database;

import pl.damiankotynia.model.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Database {

    private Database instance;
    private List<Service> data;
    private File databaseFile;

    private Database(){
        data = new ArrayList<>();
    }

    public Database getInstance(){
        if (instance==null) {
            instance = new Database();
        }
        return instance;
    }

    public boolean save(Service service){
        return false;
    }

    public List<String> getAll(){
        return null;
    }

    public List<String> find(String phrase){
        return null;
    }
    public boolean remove(Service service){
        return false;
    }
}

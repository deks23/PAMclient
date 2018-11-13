package pl.damiankotynia.database;

import pl.damiankotynia.exceptions.PermissionDeniedException;
import pl.damiankotynia.exceptions.TermNotAvalibleException;
import pl.damiankotynia.model.Request;
import pl.damiankotynia.model.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static Database instance;
    private List<Service> data;

    private Database(){
        data = new ArrayList<>();
    }

    public static Database getInstance(){
        if (instance==null) {
            instance = new Database();
        }
        return instance;
    }

    public synchronized boolean save(Request request) throws TermNotAvalibleException{

        if(checkIfAvalible(request.getService().getStartTime())){
            data.add(request.getService());
            return true;
        }else
            return false;
    }

    public synchronized List<Service> getAll(){
        return data;
    }

    public synchronized List<Service> find(String phrase){
        //TODO mozliwe ze metoda jest zbedna
        return null;
    }

    public synchronized boolean remove(Service serviceToRemove){
        return data.remove(serviceToRemove);
    }

    private boolean checkIfAvalible(LocalDateTime startTime) throws TermNotAvalibleException {
        for (Service service : data){
            if(service.getStartTime().equals(startTime))
                throw new TermNotAvalibleException();

            LocalDateTime endTime = startTime.plusHours(1L);

            if(endTime.getHour()>=18){
                if(endTime.getMinute()>0)
                    throw new TermNotAvalibleException();
            }
        }
        return true;
    }

    private boolean checkIfCanRemove(Request requestToRemove, int userId)throws PermissionDeniedException {

        return false;
    }
}

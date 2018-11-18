package pl.damiankotynia.database;

import org.omg.CORBA.DATA_CONVERSION;
import pl.damiankotynia.exceptions.PermissionDeniedException;
import pl.damiankotynia.exceptions.TermNotAvalibleException;
import pl.damiankotynia.model.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static pl.damiankotynia.service.Utils.DATABASE_LOGGER;

public class Database {

    private static Database instance;
    private List<Service> data;

    private Database(){
        data = new LinkedList<>();
    }

    public static Database getInstance(){
        if (instance==null) {
            instance = new Database();
        }
        return instance;
    }

    public synchronized boolean save(Service service){
        if(checkIfAvalible(service.getStartTime())){
            data.add(service);
            System.out.println(DATABASE_LOGGER + "dodano do bazy" + service.toString());
            return true;
        }else{
            System.out.println(DATABASE_LOGGER + " termin niedostepny " + service.toString());
            return false;
        }

    }

    public synchronized List<Service> findByCustomerName(String nick){
        List<Service> returnList = new LinkedList<>();
        for (Service service : data){
            if(service.getCustomerName().equals(nick))
                returnList.add(service);
        }
        return returnList;
    }

    public synchronized List<Service> getAll(){
        return data;
    }

    public synchronized boolean remove(Service serviceToRemove){
        return data.remove(serviceToRemove);
    }

    private boolean checkIfAvalible(LocalDateTime startTime){
        LocalDateTime endTime = startTime.plusHours(1L);
        if(START_TIME.isAfter(startTime.toLocalTime()))
            return false;
        if(FINISH_TIME.isBefore(endTime.toLocalTime()))
            return false;

        for (Service service : data){
            if(service.getStartTime().equals(startTime))
                return false;
        }
        return true;
    }

    private boolean checkIfCanRemove(Service serviceToRemove, int nickName)throws PermissionDeniedException {
        //TODO możliwe że do wywalenia
        return false;
    }

    private static final LocalTime START_TIME = LocalTime.of(10, 0);
    private static final LocalTime FINISH_TIME = LocalTime.of(18, 0);
}

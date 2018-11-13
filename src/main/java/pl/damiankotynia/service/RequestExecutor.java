package pl.damiankotynia.service;

import pl.damiankotynia.database.Database;
import pl.damiankotynia.exceptions.InvalidRequestFormatException;
import pl.damiankotynia.exceptions.TermNotAvalibleException;
import pl.damiankotynia.model.Request;

public class RequestExecutor {

    private Database database;

    public RequestExecutor() {
        database = Database.getInstance();
    }

    public boolean executeRequest(Object request) throws InvalidRequestFormatException {
        Request validatedRequest = getRequest(request);
        switch(validatedRequest.getRequestType()){
            case POST:
                System.out.println("post");
                post(validatedRequest);
                break;
            case DELETE:
                System.out.println("delete");
                break;
            case GET:
                System.out.println("get");
                break;
            default:
                System.out.println("default");
                break;
        }
        return false;
    }

    private Request getRequest(Object request) throws InvalidRequestFormatException {
        if(!(request instanceof Request))
            throw new InvalidRequestFormatException();
        else
            return (Request)request;
    }

    private boolean post(Request request){
        try {
            database.save(request);
        } catch (TermNotAvalibleException e) {
            e.printStackTrace();
        }

        return false;
    }

}

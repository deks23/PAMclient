package pl.damiankotynia.service;

import pl.damiankotynia.database.Database;
import pl.damiankotynia.exceptions.InvalidRequestFormatException;
import pl.damiankotynia.model.Request;

import static pl.damiankotynia.service.Utils.REQUEST_EXECUTOR_LOGGER;

public class RequestExecutor {

    private Database database;


    public boolean executeRequest(Object request) throws InvalidRequestFormatException {
        Request validatedRequest = getRequest(request);
        switch (validatedRequest.getRequestType()) {
            case POST:
                System.out.println(REQUEST_EXECUTOR_LOGGER + "post " + validatedRequest.toString());
                if (!post(validatedRequest)) {
                    System.out.println(REQUEST_EXECUTOR_LOGGER + "NIEPOWODZENIE TODO");
                }
                //TODO info dla wszystkich o rezerwacji terminu
                //TODO zwrocenie listy rezerwacji rezerwujacego uzytkownika
                break;
            case DELETE:
                System.out.println(REQUEST_EXECUTOR_LOGGER + "delete " + validatedRequest.toString());

                break;
            case GET:
                System.out.println("GET: \t >>>>");
                break;
            case GET_ALL:
                System.out.println("GET_ALL: \t >>>>");
                break;
            default:
                System.out.println("DEFAULT: \t >>>>");
                break;
        }
        return false;
    }

    private Request getRequest(Object request) throws InvalidRequestFormatException {
        if (!(request instanceof Request))
            throw new InvalidRequestFormatException();
        else
            return (Request) request;
    }

    private boolean post(Request request) {
        database = Database.getInstance();
        if (database.save(request.getService()))
            return true;
        else
            return false;
    }

}

package pl.damiankotynia.service;

import pl.damiankotynia.connector.Connector;
import pl.damiankotynia.database.Database;
import pl.damiankotynia.exceptions.InvalidRequestFormatException;
import pl.damiankotynia.model.Request;
import pl.damiankotynia.model.Response;
import pl.damiankotynia.model.ResponseType;

import static pl.damiankotynia.service.Utils.REQUEST_EXECUTOR_LOGGER;

public class RequestExecutor {

    private Database database;


    public Response executeRequest(Object request) throws InvalidRequestFormatException {
        Request validatedRequest = getRequest(request);
        Response response = response = new Response();;
        switch (validatedRequest.getRequestType()) {
            case POST:
                System.out.println(REQUEST_EXECUTOR_LOGGER + "post " + validatedRequest.toString());
                if (!post(validatedRequest)) {
                    System.out.println(REQUEST_EXECUTOR_LOGGER + "NIEPOWODZENIE TODO");
                    response.setResponseType(ResponseType.RESERVATION_FAILED);
                    response.setMessage("Niepowodzenie rezerwacji");
                }else{
                    response.setResponseType(ResponseType.RESERVATION_COMPLETE);
                    response.setMessage("Rezerwacja przebiegla pomyslnie");
                    response.setServiceList(database.findByCustomerName(validatedRequest.getService().getCustomerName()));
                }
                break;


            case DELETE:
                System.out.println(REQUEST_EXECUTOR_LOGGER + "delete " + validatedRequest.toString());
                if(!delete(validatedRequest)){
                    System.out.println(REQUEST_EXECUTOR_LOGGER + "NIEPOWODZENIE TODO");
                    response.setResponseType(ResponseType.RESERVATION_REMOVING_FAILED);
                    response.setMessage("Niepowodzenie usuwania rezerwacji");
                }else{
                    response.setResponseType(ResponseType.RESERVATION_REMOVING_FAILED);
                    response.setMessage("Pomyslnie usunięto reserwację");
                    response.setServiceList(database.findByCustomerName(validatedRequest.getService().getCustomerName()));
                }
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
        return response;
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

    private boolean delete(Request request){
        database.remove(request.getService());
        return false;
    }

}

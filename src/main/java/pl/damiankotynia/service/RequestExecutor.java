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

    public RequestExecutor() {
        this.database = Database.getInstance();
    }

    public Response executeRequest(Object request) throws InvalidRequestFormatException {
        Request validatedRequest = getRequest(request);
        Response response = response = new Response();;
        switch (validatedRequest.getRequestType()) {
            case POST:
                saveReservation(validatedRequest, response);
                break;
            case DELETE:
                deleteService(validatedRequest, response);
                break;
            case GET:
                getOwnReservations(validatedRequest, response);
                break;
            case GET_ALL:
                getAllReservations(response, validatedRequest);
                break;
            default:
                System.out.println("DEFAULT: \t >>>>");
                break;
        }
        return response;
    }

    private void getOwnReservations(Request validatedRequest, Response response) {
        System.out.println(REQUEST_EXECUTOR_LOGGER + "get " + validatedRequest.toString());
        response.setServiceList(database.findByCustomerName(validatedRequest.getNickName()));
        response.setResponseType(ResponseType.GET_OWN_RESERVATIONS);
    }

    private void getAllReservations(Response response, Request validatedRequest) {
        System.out.println(REQUEST_EXECUTOR_LOGGER + "get_all " + validatedRequest.toString());
        response.setServiceList(database.getAll());
        response.setResponseType(ResponseType.GET_RESERVATIONS);
    }

    private void saveReservation(Request validatedRequest, Response response) {
        System.out.println(REQUEST_EXECUTOR_LOGGER + "post " + validatedRequest.toString());
        if (!database.save(validatedRequest.getService())) {
            System.out.println(REQUEST_EXECUTOR_LOGGER + "NIEPOWODZENIE TODO");
            response.setResponseType(ResponseType.RESERVATION_FAILED);
            response.setMessage("Niepowodzenie rezerwacji");
        }else{
            response.setResponseType(ResponseType.RESERVATION_COMPLETE);
            response.setMessage(validatedRequest.getService().getStartTime().toString() + "termin został zarezerwowany");
            response.setServiceList(database.findByCustomerName(validatedRequest.getService().getCustomerName()));
        }
    }

    private void deleteService(Request validatedRequest, Response response) {
        System.out.println(REQUEST_EXECUTOR_LOGGER + "delete " + validatedRequest.toString());
        if(!database.remove(validatedRequest.getService())){
            System.out.println(REQUEST_EXECUTOR_LOGGER + "NIEPOWODZENIE TODO");
            response.setResponseType(ResponseType.RESERVATION_REMOVING_FAILED);
            response.setMessage("Niepowodzenie usuwania rezerwacji");
        }else{
            response.setResponseType(ResponseType.DELETED_RESERVATION);
            response.setMessage(validatedRequest.getService().getStartTime().toString() + "termin został zwolniony");
            response.setServiceList(database.findByCustomerName(validatedRequest.getService().getCustomerName()));
        }
    }

    private Request getRequest(Object request) throws InvalidRequestFormatException {
        if (!(request instanceof Request))
            throw new InvalidRequestFormatException();
        else
            return (Request) request;
    }




}

package pl.damiankotynia.service;

import org.junit.Before;
import org.junit.Test;
import pl.damiankotynia.exceptions.InvalidRequestFormatException;
import pl.damiankotynia.model.Request;
import pl.damiankotynia.model.RequestType;
import pl.damiankotynia.model.Service;

import java.time.LocalDateTime;

public class RequestExecutorTest {
    @Before
    public void init(){
        requestChecker = new RequestExecutor();
    }
    @Test
    public void executeRequestTest(){
        Object request = new Request();
        ((Request) request).setRequestType(RequestType.POST);
        Service service = new Service();
        service.setCustomerName("qwe");
        service.setId("qwe");
        service.setStartTime(LocalDateTime.now());
        ((Request) request).setService(service);
        try {
            requestChecker.executeRequest(request);
        } catch (InvalidRequestFormatException invalidRequestFormat) {
            invalidRequestFormat.printStackTrace();
        }
        try {
            requestChecker.executeRequest(request);
        } catch (InvalidRequestFormatException invalidRequestFormat) {
            invalidRequestFormat.printStackTrace();
        }
    }

    private RequestExecutor requestChecker;
}

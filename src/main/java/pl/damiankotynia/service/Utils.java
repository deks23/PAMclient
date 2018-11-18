package pl.damiankotynia.service;

public class Utils {
    public static boolean isEmpty(Object object){
        if(object==null){
            return true;
        }
        return false;
    }

    public static final String DATABASE_LOGGER = "DATABASE \t\t >>>> ";
    public static final String CONNECTION_LOGGER = "CONNECTION \t\t >>>> ";
    public static final String REQUEST_EXECUTOR_LOGGER = "REQUEST_EXE \t >>>> ";
}

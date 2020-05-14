package Server;

import ObjectTransferProtocol.IRequest;
import ObjectTransferProtocol.IResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.jmx.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Worker implements Runnable{
    private ServerImplementation server;
    private Socket connection;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    private static final Logger logger = LogManager.getLogger();

    public Worker(ServerImplementation server, Socket connection){
        this.server = server;
        this.connection = connection;
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while(connected){
            try{
                Object request = input.readObject();
                Object response = handleRequest((IRequest) request);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private IResponse handleRequest(IRequest request){
        IResponse response = null;


        return response;
    }
}

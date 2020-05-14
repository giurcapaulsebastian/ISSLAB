package Server;

import Utils.AbsConcurentServer;

import java.net.Socket;

public class ConcurentServer extends AbsConcurentServer {
    private ServerImplementation server;

    public ConcurentServer(int port, ServerImplementation server){
        super(port);
        this.server = server;
    }

    @Override
    protected Thread createWorker(Socket client) {
        Worker worker = new Worker(server,client);
        Thread tw = new Thread(worker);
        return tw;
    }
}
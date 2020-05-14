package Utils;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IObserver extends Remote {

    void refreshTableView() throws RemoteException;
}

package Server;

import Repository.RepositoryCustomer;
import Repository.RepositoryProduct;
import Repository.RepositorySubscription;
import Repository.RepositoryUser;
import Utils.IObserver;
import Utils.IServer;
import domainDTO.Customer;
import domainDTO.ListDTO;
import domainDTO.Product;
import domainDTO.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerImplementation implements IServer {
    public ServerSocket server = null;
    public RepositoryCustomer repositoryCustomer;
    public RepositorySubscription repositorySubscription;
    public RepositoryProduct repositoryProduct;
    public RepositoryUser repositoryUser;
    public Map<String, IObserver> loggedClients;
    public Integer defaultThreadsNo = 5;

    public ServerImplementation(Properties props){
        this.repositoryCustomer = new RepositoryCustomer(props);
        this.repositorySubscription = new RepositorySubscription(props);
        this.repositoryUser = new RepositoryUser(props);
        this.repositoryProduct = new RepositoryProduct(props);
        this.loggedClients = new ConcurrentHashMap<>();
    }

    public synchronized boolean login(User user, IObserver observer){
        User userInDatabase = repositoryUser.findByUsername(user.getUsername());
        if(userInDatabase!=null && userInDatabase.getPassword().equals(user.getPassword())){
            loggedClients.put(user.getUsername(),observer);
            return true;
        }
        return false;
    }


    public synchronized ListDTO<Product> getProductList(){
        ListDTO<Product> all = new ListDTO<>();
        for(Product c : repositoryProduct.findAll()){
            all.add(c);
        }
        return all;
    }

    @Override
    public boolean makeOrder(Product p, Integer quantity) {
        for(Product c : repositoryProduct.findAll()){
            if(c.getId().equals(p.getId())){
                if(p.getQuantity()-quantity>0){
                    p.setQuantity(p.getQuantity()-quantity);
                    repositoryProduct.update(p.getId(),p);
                    refreshClients();
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }

    public void refreshClients(){
        ExecutorService executorService = Executors.newFixedThreadPool(defaultThreadsNo);
        for(IObserver observer : loggedClients.values()){
            executorService.execute(()-> {
                try {
                    observer.refreshTableView();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }
}

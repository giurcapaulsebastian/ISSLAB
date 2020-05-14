package Utils;

import domainDTO.Customer;
import domainDTO.ListDTO;
import domainDTO.Product;
import domainDTO.User;

import java.io.Serializable;
import java.util.Date;

public interface IServer extends Serializable {
    boolean login(User user, IObserver observer);
    ListDTO<Product> getProductList();

    boolean makeOrder(Product p, Integer quantity);
}

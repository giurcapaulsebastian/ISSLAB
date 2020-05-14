package View;

import Utils.IObserver;
import Utils.IServer;
import domainDTO.Customer;
import domainDTO.Product;
import domainDTO.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MenuController extends UnicastRemoteObject implements IObserver {
    private IServer server;
    private List<Product> all;
    private ObservableList<Product> model;

    @FXML
    private TableView<Product> tableView;

    @FXML
    private TableColumn<String,Product> nameColumn;
    @FXML
    private TableColumn<String,Product> manufacturerColumn;

    @FXML
    private TableColumn<Integer,Product> priceColumn;
    @FXML
    private TableColumn<Integer,Product> quantityColumn;

    @FXML
    private TextField quantityTextField;

    public MenuController() throws RemoteException{

    }

    public void setServer(IServer server) {
        this.server=server;
    }

    public void initTableView(){
        all = server.getProductList().getTransferList();
        model = FXCollections.observableArrayList(all);
        tableView.setItems(model);
        nameColumn.setCellValueFactory(new PropertyValueFactory<String,Product>("name"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<String,Product>("manufacturer"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Integer,Product>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Integer,Product>("quantity"));
    }

    @Override
    public void refreshTableView() throws RemoteException{
        initTableView();
    }

    public void doOrder(ActionEvent actionEvent) {
        Product p = tableView.getSelectionModel().getSelectedItem();
        Integer quantity = Integer.parseInt(quantityTextField.getText());
        if(server.makeOrder(p,quantity)==false){
            MessageAlert.showErrorMessage("Cantitate indisponibila!");
        }
    }
}

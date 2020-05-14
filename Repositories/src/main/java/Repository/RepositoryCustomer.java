package Repository;

import Utils.JdbcUtils;
import domainDTO.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class RepositoryCustomer implements IRepository<Integer, Customer> {
    private JdbcUtils jdbcUtils;
    private static final Logger logger = Logger.getLogger(RepositoryUser.class.getName());

    public RepositoryCustomer(Properties props){
        jdbcUtils = new JdbcUtils(props);
    }

    @Override
    public int size() {
        Connection conn = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("select count(*) as dim from Customer")){
            try(ResultSet rs = preStmt.executeQuery()){
                if(rs.next()){
                    return rs.getInt("dim");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database ERROR" + e);
        }
        return 0;
    }

    @Override
    public void save(Customer entity) {
        Connection conn = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("insert into Customer values(?,?,?,?,?,?,?)")){
            //preStmt.setInt(1,entity.getId());
            preStmt.setString(2,entity.getFirstname());
            preStmt.setString(3,entity.getLastname());
            Date date = entity.getBornDate();
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String forDate = format.format(date);
            preStmt.setString(4,forDate);
            preStmt.setString(5,entity.getEmail());
            preStmt.setString(6,entity.getPhoneNumber());
            preStmt.setString(7,entity.getImageURL());
            int result = preStmt.executeUpdate();
            logger.info("Saved Subscription for cl");
        } catch (SQLException e) {
            logger.info(e.toString());
        }
    }

    @Override
    public void delete(Integer integer) {
        logger.info("Deleting Customer with id="+integer);
        Connection conn = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("delete from Customer where idCustomer=?")){
            preStmt.setInt(1,integer);
            int result = preStmt.executeUpdate();
        } catch (SQLException e) {
            logger.info(e.toString());
        }
    }

    @Override
    public void update(Integer integer, Customer entity) {
        Connection conn = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("UPDATE User SET firstname=?,lastname=?,email=?,phoneNumber=? WHERE id=?")) {
            preStmt.setString(1,entity.getFirstname());
            preStmt.setString(2,entity.getLastname());
            preStmt.setString(3,entity.getEmail());
            preStmt.setString(4,entity.getPhoneNumber());
            int result = preStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info("Updated Custome with name="+entity.getFirstname());
    }

    @Override
    public Customer findOne(Integer integer) {
        Connection con = jdbcUtils.getConnection();
        Customer customer = null;
        try(PreparedStatement preStmt = con.prepareStatement("select * from Customer where idCustomer=?")){
            preStmt.setInt(1,integer);
            ResultSet resultSet = preStmt.executeQuery();
            if(resultSet.next()){
                Integer idCustomer = resultSet.getInt("idSubscription");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String forDate = resultSet.getString("date");
                Date dateStarted= new SimpleDateFormat("dd/MM/yyyy").parse(forDate);
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phoneNumber");
                String imageURL = resultSet.getString("profilePicture");
                customer = new Customer(idCustomer,firstname,lastname,dateStarted,email,phoneNumber,imageURL);
                logger.info("Am gasit clientul cu numele: " + customer.getFirstname());
            }
        } catch (SQLException | ParseException e) {
            logger.info(e.toString());
        }
        return customer;

    }

    @Override
    public Iterable<Customer> findAll() {
        Connection con = jdbcUtils.getConnection();
        List<Customer> customers = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Customer")){
            ResultSet resultSet = preStmt.executeQuery();
            while(resultSet.next()){
                Integer idCustomer = resultSet.getInt("idCustomer");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String forDate = resultSet.getString("date");
                Date dateStarted= new SimpleDateFormat("dd/MM/yyyy").parse(forDate);
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phoneNumber");
                String imageURL = resultSet.getString("profilePicture");
                Customer customer = new Customer(idCustomer,firstname,lastname,dateStarted,email,phoneNumber,imageURL);
                customers.add(customer);
            }
        } catch (SQLException | ParseException e) {
            logger.info(e.toString());
        }
        return customers;
    }
}

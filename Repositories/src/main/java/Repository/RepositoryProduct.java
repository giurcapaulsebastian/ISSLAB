package Repository;

import Utils.JdbcUtils;
import domainDTO.Customer;
import domainDTO.Product;

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

public class RepositoryProduct implements IRepository<Integer, Product> {
    private JdbcUtils jdbcUtils;
    private static final Logger logger = Logger.getLogger(RepositoryUser.class.getName());

    public RepositoryProduct(Properties props){
        jdbcUtils = new JdbcUtils(props);
    }

    @Override
    public int size() {
        Connection conn = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("select count(*) as dim from Product")){
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
    public void save(Product entity) {
        Connection conn = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("insert into Product values(?,?,?,?,?)")){
            //preStmt.setInt(1,entity.getId());
            preStmt.setString(2,entity.getName());
            preStmt.setString(3,entity.getManufacturer());
            preStmt.setInt(4,entity.getPrice());
            preStmt.setInt(5,entity.getQuantity());
            int result = preStmt.executeUpdate();
            logger.info("Saved Subscription for cl");
        } catch (SQLException e) {
            logger.info(e.toString());
        }
    }

    @Override
    public void delete(Integer integer) {
        logger.info("Deleting Product with id="+integer);
        Connection conn = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("delete from Product where idProduct=?")){
            preStmt.setInt(1,integer);
            int result = preStmt.executeUpdate();
        } catch (SQLException e) {
            logger.info(e.toString());
        }
    }

    @Override
    public void update(Integer integer, Product entity) {
        Connection conn = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("UPDATE Product SET name=?,manufacturer=?,price=?,quantity=? WHERE idProduct=?")) {
            preStmt.setString(1,entity.getName());
            preStmt.setString(2,entity.getManufacturer());
            preStmt.setInt(3,entity.getPrice());
            preStmt.setInt(4,entity.getQuantity());
            preStmt.setInt(5,entity.getId());
            int result = preStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info("Updated Product with name="+entity.getName());
    }

    @Override
    public Product findOne(Integer integer) {
        Connection con = jdbcUtils.getConnection();
        Product product = null;
        try(PreparedStatement preStmt = con.prepareStatement("select * from Product where idProduct=?")){
            preStmt.setInt(1,integer);
            ResultSet resultSet = preStmt.executeQuery();
            if(resultSet.next()){
                Integer idProduct= resultSet.getInt("idProduct");
                String name = resultSet.getString("name");
                String manufacturer = resultSet.getString("manufacturer");
                Integer price = resultSet.getInt("price");
                Integer quantity = resultSet.getInt("quantity");
                product = new Product(idProduct,name,manufacturer,price,quantity);
                logger.info("Am gasit clientul cu numele: " + product.getName());
            }
        } catch (SQLException e) {
            logger.info(e.toString());
        }
        return product;
    }

    @Override
    public Iterable<Product> findAll() {
        Connection con = jdbcUtils.getConnection();
        List<Product> products = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Product")){
            ResultSet resultSet = preStmt.executeQuery();
            while(resultSet.next()){
                Integer idProduct= resultSet.getInt("idProduct");
                String name = resultSet.getString("name");
                String manufacturer = resultSet.getString("manufacturer");
                Integer price = resultSet.getInt("price");
                Integer quantity = resultSet.getInt("quantity");
                Product product = new Product(idProduct,name,manufacturer,price,quantity);
                products.add(product);
            }
        } catch (SQLException e) {
            logger.info(e.toString());
        }
        return products;
    }
}

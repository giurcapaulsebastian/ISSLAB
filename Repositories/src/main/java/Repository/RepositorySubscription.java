package Repository;

import Utils.JdbcUtils;
import domainDTO.Subscription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class RepositorySubscription implements IRepository<Integer, Subscription> {
    private JdbcUtils jdbcUtils;
    private static final Logger logger = Logger.getLogger(RepositoryUser.class.getName());

    public RepositorySubscription(Properties props){
        jdbcUtils = new JdbcUtils(props);
    }

    @Override
    public int size() {
        Connection conn = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("select count(*) as dim from Subscription")){
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
    public void save(Subscription entity) {
        Connection conn = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("insert into Subscription values(?,?,?,?,?)")){
            //preStmt.setInt(1,entity.getId());
            preStmt.setInt(2,entity.getIdCustomer());
            preStmt.setString(3,entity.getDateStarted().toString());
            preStmt.setString(4,entity.getDateFinished().toString());
            preStmt.setInt(5,entity.getNumberOfMonths());
            int result = preStmt.executeUpdate();
            logger.info("Saved Subscription for cl");
        } catch (SQLException e) {
            logger.info(e.toString());
        }
    }

    @Override
    public void delete(Integer integer) {
        logger.info("Deleting Subscription with id="+integer);
        Connection conn = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("delete from Subscription where idSubscription=?")){
            preStmt.setInt(1,integer);
            int result = preStmt.executeUpdate();
        } catch (SQLException e) {
            logger.info(e.toString());
        }
    }

    @Override
    public void update(Integer integer, Subscription entity) {
        Connection conn = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("UPDATE User SET dateFinished=? WHERE id=?")) {
            preStmt.setString(1,entity.getDateFinished().toString());
            preStmt.setInt(2,entity.getId());
            int result = preStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info("Updated Subscription with id="+entity.getId());
    }

    @Override
    public Subscription findOne(Integer integer) {
        Connection con = jdbcUtils.getConnection();
        Subscription subscription = null;
        try(PreparedStatement preStmt = con.prepareStatement("select * from Subscription where idCustomer=?")){
            preStmt.setInt(1,integer);
            ResultSet resultSet = preStmt.executeQuery();
            if(resultSet.next()){
                Integer idSubscription = resultSet.getInt("idSubscription");
                Integer idCustomer = resultSet.getInt("idCustomer");
                String forDate = resultSet.getString("dateStarted");
                Date dateStarted= new SimpleDateFormat("dd/MM/yyyy").parse(forDate);
                forDate = resultSet.getString("dateFinished");
                Date dateFinished= new SimpleDateFormat("dd/MM/yyyy").parse(forDate);
                Integer numberOfMonths = resultSet.getInt("numberOfMonths");
                subscription = new Subscription(idSubscription,idCustomer,dateStarted,dateFinished,numberOfMonths);
                logger.info("Am gasit abonamentul-ul cu id-ul: " + subscription.getId());
            }
        } catch (SQLException | ParseException e) {
            logger.info(e.toString());
        }
        return subscription;
    }

    @Override
    public Iterable<Subscription> findAll() {
        Connection con = jdbcUtils.getConnection();
        List<Subscription> subscriptions = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Subscription")){
            ResultSet resultSet = preStmt.executeQuery();
            while(resultSet.next()){
                Integer idSubscription = resultSet.getInt("idSubscription");
                Integer idCustomer = resultSet.getInt("idCustomer");
                String forDate = resultSet.getString("dateStarted");
                Date dateStarted= new SimpleDateFormat("dd/MM/yyyy").parse(forDate);
                forDate = resultSet.getString("dateFinished");
                Date dateFinished= new SimpleDateFormat("dd/MM/yyyy").parse(forDate);
                Integer numberOfMonths = resultSet.getInt("numberOfMonths");
                Subscription subscription = new Subscription(idSubscription,idCustomer,dateStarted,dateFinished,numberOfMonths);
                subscriptions.add(subscription);
            }
        } catch (SQLException | ParseException e) {
            logger.info(e.toString());
        }
        return subscriptions;
    }
}

package Repository;

import Utils.JdbcUtils;
import domainDTO.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class RepositoryUser implements IRepository<Integer, User> {
    private JdbcUtils jdbcUtils;
    private static final Logger logger = Logger.getLogger(RepositoryUser.class.getName());

    public RepositoryUser(Properties props){
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
    public void save(User entity) {
        Connection conn = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("insert into User values(?,?,?)")){
            //preStmt.setInt(1,entity.getId());
            preStmt.setString(2,entity.getUsername());
            preStmt.setString(3,entity.getPassword());
            int result = preStmt.executeUpdate();
            logger.info("Saved user with name " + entity.getUsername());
        } catch (SQLException e) {
            logger.info(e.toString());
        }
    }

    @Override
    public void delete(Integer integer) {
        logger.info("Deleting User with id="+integer);
        Connection conn = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("delete from User where idUser=?")){
            preStmt.setInt(1,integer);
            int result = preStmt.executeUpdate();
        } catch (SQLException e) {
            logger.info(e.toString());
        }
    }

    @Override
    public void update(Integer integer, User entity) {
        Connection conn = jdbcUtils.getConnection();
        try(PreparedStatement preStmt = conn.prepareStatement("UPDATE User SET password=? WHERE id=?")) {
            preStmt.setString(1,entity.getPassword());
            preStmt.setInt(2,entity.getId());
            int result = preStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info("Updated spectacol with id="+entity.getId());
    }

    @Override
    public User findOne(Integer integer) {
        Connection con = jdbcUtils.getConnection();
        User user = null;
        try(PreparedStatement preStmt = con.prepareStatement("select * from User where idUser=?")){
            preStmt.setInt(1,integer);
            ResultSet resultSet = preStmt.executeQuery();
            if(resultSet.next()){
                Integer idUser = resultSet.getInt("idUser");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                user = new User(idUser,username,password);
                logger.info("Am gasit user-ul cu username-ul: " + user.getUsername());
            }
        } catch (SQLException e) {
            logger.info(e.toString());
        }
        return user;
    }

    public User findByUsername(String username){
        Connection con = jdbcUtils.getConnection();
        User user = null;
        try(PreparedStatement preStmt = con.prepareStatement("select * from User where username=?")){
            preStmt.setString(1,username);
            ResultSet resultSet = preStmt.executeQuery();
            if(resultSet.next()){
                Integer idUser = resultSet.getInt("idUser");
                String username2 = resultSet.getString("username");
                String password = resultSet.getString("password");
                user = new User(idUser,username2,password);
                logger.info("Am gasit user-ul cu username-ul: " + user.getUsername());
            }
        } catch (SQLException e) {
            logger.info(e.toString());
        }
        return user;
    }

    @Override
    public Iterable<User> findAll() {
        Connection con = jdbcUtils.getConnection();
        List<User> users = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from User")){
            ResultSet resultSet = preStmt.executeQuery();
            while(resultSet.next()){
                Integer idUser = resultSet.getInt("idUser");
                String username2 = resultSet.getString("username");
                String password = resultSet.getString("password");
                User user = new User(idUser,username2,password);
                users.add(user);
            }
        } catch (SQLException e) {
            logger.info(e.toString());
        }
        return users;
    }
}

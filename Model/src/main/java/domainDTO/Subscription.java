package domainDTO;

import java.io.Serializable;
import java.util.Date;

public class Subscription implements IHasId<Integer>, Serializable {
    private Integer idCustomer;
    private Integer idSubscription;
    private Date dateStarted;
    private Date dateFinished;
    private Integer numberOfMonths;

    public Subscription(Integer idSubscription,Integer idCustomer, Date dateStarted, Date dateFinished, Integer numberOfMonths) {
        this.idCustomer = idCustomer;
        this.idSubscription = idSubscription;
        this.dateStarted = dateStarted;
        this.dateFinished = dateFinished;
        this.numberOfMonths = numberOfMonths;
    }

    @Override
    public Integer getId() { return idSubscription; }

    @Override
    public void setId(Integer integer) { this.idSubscription = integer; }

    public Integer getIdCustomer() { return idCustomer; }

    public void setIdCustomer(Integer idCustomer) { this.idCustomer = idCustomer; }

    public Date getDateStarted() { return dateStarted; }

    public void setDateStarted(Date dateStarted) { this.dateStarted = dateStarted; }

    public Date getDateFinished() { return dateFinished; }

    public void setDateFinished(Date dateFinished) { this.dateFinished = dateFinished; }

    public Integer getNumberOfMonths() { return numberOfMonths; }

    public void setNumberOfMonths(Integer numberOfMonths) { this.numberOfMonths = numberOfMonths; }
}

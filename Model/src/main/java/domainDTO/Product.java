package domainDTO;

import java.io.Serializable;

public class Product implements IHasId<Integer>, Serializable {
    private Integer idProduct;
    private Integer quantity;
    private String manufacturer;
    private String name;
    private Integer price;

    public Product(Integer idProduct, String name, String manufacturer,Integer price,  Integer quantity) {
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.manufacturer = manufacturer;
        this.name = name;
        this.price = price;
    }

    @Override
    public Integer getId() { return this.idProduct; }

    @Override
    public void setId(Integer integer) { this.idProduct = integer; }

    public Integer getQuantity() { return quantity; }

    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Integer getPrice() { return price; }

    public void setPrice(Integer price) { this.price = price; }

    public String getManufacturer() { return manufacturer; }

    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}

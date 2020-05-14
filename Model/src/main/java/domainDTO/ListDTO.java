package domainDTO;

import java.io.Serializable;
import java.util.ArrayList;

public class ListDTO<E extends Serializable> implements Serializable {
    private ArrayList<E> transferList;

    public ListDTO() {
        transferList = new ArrayList<>();
    }

    public ListDTO(ArrayList<E> list){
        this.transferList = list;
    }

    public void add(E element) {
        transferList.add(element);
    }

    public ArrayList<E> toArray(){
        return transferList;
    }

    public int size(){
        return transferList.size();
    }

    public E get(int poz) { return transferList.get(poz); }

    public ArrayList<E> getTransferList() {
        return transferList;
    }
}


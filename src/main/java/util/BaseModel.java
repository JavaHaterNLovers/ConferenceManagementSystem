package util;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BaseModel<T>
{
    /**
     * Lista observabila.
     */
    private ObservableList<T> list;

    /**
     * Creeaza un nou model cu o lista data.
     *
     * @param list
     */
    public BaseModel(List<T> list) {
        this.list = FXCollections.observableArrayList(list);
    }

    /**
     * Updateaza elementele din lista.
     *
     * @param list
     *   Noile elemente.
     */
    public void update(List<T> list) {
        this.list.setAll(list);
    }

    /**
     * Returneaza lista de elemente observabile.
     *
     * @return
     *   Lista propriu-zisa.
     */
    public ObservableList<T> getList() {
        return list;
    }
}

package repository;

import java.util.ArrayList;
import java.util.List;
import model.BaseNumber;

public class BaseNumberRepository {

    // Lich su cac so da doi
    private List<BaseNumber> baseNumberList;

    // Constructor: tao danh sach rong
    public BaseNumberRepository() {
        baseNumberList = new ArrayList<>();
    }

    // Luu so vua nhap vao lich su
    public void save(BaseNumber baseNumber) {
        baseNumberList.add(baseNumber);
    }

    // Lay so vua luu gan nhat
    public BaseNumber findLast() {
        return baseNumberList.get(baseNumberList.size() - 1);
    }
}

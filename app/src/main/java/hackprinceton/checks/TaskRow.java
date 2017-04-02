package hackprinceton.checks;

/**
 * Created by Kentaro on 4/1/2017.
 */

public class TaskRow {
    private int _id;
    private String _name;
    private String _days;

    public TaskRow() {}

    public TaskRow (int id, String name, String days) {
        this._id = id;
        this._name = name;
        this._days = days;
    }

    public TaskRow(String name, String days) {
        this._name = name;
        this._days = days;
    }

    public int getID() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public String getDays() {
        return _days;
    }

    public void setID(int ID) {
        this._id = ID;
    }

    public void setName(String name) {
        this._name = name;
    }

    public void setDays(String days) {
        this._days = days;
    }
}

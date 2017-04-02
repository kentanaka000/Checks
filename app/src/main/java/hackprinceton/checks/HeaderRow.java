package hackprinceton.checks;

/**
 * Created by Kentaro on 4/1/2017.
 */

public class HeaderRow {
    //private variables
    private int _id;
    private String _name;
    private String _email;
    private int _interval;
    private int _db;
    private long _next;

    // Empty constructor
    public HeaderRow(){

    }
    // constructor
    public HeaderRow(int id, String name, String email, int interval, int data, long next){
        this._id = id;
        this._name = name;
        this._email = email;
        this._interval = interval;
        this._db = data;
        this._next = next;
    }

    public HeaderRow(String name, String email, int interval, int data, long next){
        this._name = name;
        this._email = email;
        this._interval = interval;
        this._db = data;
        this._next = next;
    }

    public int getID(){
        return this._id;
    }

    // getting name
    public String getName(){
        return this._name;
    }

    public String getEmail(){
        return this._email;
    }

    public int getInterval(){
        return this._interval;
    }

    public int getDb(){
        return this._db;
    }

    public long getNext() {
        return this._next;
    }


    public void setID(int id){
        this._id = id;
    }

    // getting name
    public void setName(String name){
        this._name = name;
    }

    public void setEmail(String email){
        this._email = email;
    }

    public void setInterval(int interval){
        this._interval = interval;
    }

    public void setDb(int db){
        this._db = db;
    }

    public void setNext(long next) {
        this._next = next;
    }
}

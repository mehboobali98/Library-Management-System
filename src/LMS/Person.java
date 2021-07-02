package LMS;

public class Person {

    protected int _id;
    protected String _name;
    protected String _password;
    protected String _phoneNumber;
    protected String _address;
    private static int currentPersonId = 0;

    Person(int id, String name, String password, String phoneNumber, String address) {
        if (id == -1) {
            currentPersonId++;
            this._id = currentPersonId;
        } else {
            this._id = id;
        }
        this._name = name;
        this._password = password;
        this._phoneNumber = phoneNumber;
        this._address = address;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_phoneNumber() {
        return _phoneNumber;
    }

    public void set_phoneNumber(String _phoneNumber) {
        this._phoneNumber = _phoneNumber;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "\nId            : " + _id +
                "\nName          : " + _name +
                "\nPassword      : " + _password +
                "\nPhoneNumber   : " + _phoneNumber +
                "\nAddress       : " + _address +
                "\n}";
    }

    void printInfo() {
        System.out.println(this);
    }
}



package LMS;

public class Staff extends Person{
    private double _salary;

    Staff(int id, String name, String password, String phoneNumber, String address,double salary) {
        super(id, name, password, phoneNumber, address);
        this._salary=salary;
    }
    public int getId(){
        return super._id;
    }

    public double get_salary() {
        return _salary;
    }

    public void set_salary(double _salary) {
        this._salary = _salary;
    }
}

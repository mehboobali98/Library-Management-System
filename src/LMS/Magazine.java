package LMS;

public class Magazine extends Item{
    String _company;
    Magazine(int ID, String name,String company) {
        super(ID, name);
        this._company = company;
    }

    public String get_company() {
        return _company;
    }

    public void set_company(String _company) {
        this._company = _company;
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "\nvompany='" + _company + '\'' +
                "\n} " + super.toString();
    }
}

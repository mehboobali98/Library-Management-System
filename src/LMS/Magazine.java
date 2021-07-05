package LMS;

public class Magazine extends Item {
    private String _company;

    public Magazine(int ID, String name, String company) {
        super(ID, name);
        this._company = company;
    }

    public String get_company() {
        return _company;
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "\ncompany='" + _company + '\'' +
                "\n} " + super.toString();
    }
}

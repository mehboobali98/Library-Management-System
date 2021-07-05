package LMS;

public class Item {
    protected int _ID;
    protected String _name;
    private static int currentItemId = 0;

    public Item(int ID, String name) {
        if (ID == -1) {
            currentItemId++;
            this._ID = currentItemId;
        } else {
            this._ID = ID;
        }
        this._name = name;
    }

    public int get_ID() {
        return _ID;
    }

    public String get_name() {
        return _name;
    }

    @Override
    public String toString() {
        return "Item{" +
                "\nID=" + _ID +
                "\nname='" + _name + '\'' +
                "\n}";
    }
}


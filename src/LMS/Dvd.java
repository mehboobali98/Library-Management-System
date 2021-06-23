package LMS;

public class Dvd extends Item{
    String _composer;

    Dvd(int ID, String name,String composer) {
        super(ID, name);
        this._composer=composer;
    }

    public String get_composer() {
        return _composer;
    }

    public void set_composer(String _composer) {
        this._composer = _composer;
    }

    @Override
    public String toString() {
        return "Dvd{" +
                "\ncomposer='" + _composer + '\'' +
                "} " + super.toString();
    }
}


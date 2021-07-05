package LMS;

public class Dvd extends Item {
    private final String _composer;

    public Dvd(int ID, String name, String composer) {
        super(ID, name);
        this._composer = composer;
    }

    public String get_composer() {
        return _composer;
    }

    @Override
    public String toString() {
        return "Dvd{" +
                "\ncomposer='" + _composer + '\'' +
                "} " + super.toString();
    }
}


package LMS;

import java.util.Date;

public class Reserve {
    private int _id;
    private Date _reserveDate;
    private Borrower _borrower;
    private Book _book;
    private static int currentReserveId = 0;

    public Reserve(int id, Date reserveDate, Borrower borrower, Book book) {
        if (id == -1) {
            currentReserveId++;
            this._id = currentReserveId;
        }
        this._id = id;
        this._book = book;
        this._borrower = borrower;
        this._reserveDate = reserveDate;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Date get_reserveDate() {
        return _reserveDate;
    }

    public void set_reserveDate(Date _reserveDate) {
        this._reserveDate = _reserveDate;
    }

    public Borrower get_borrower() {
        return _borrower;
    }

    public void set_borrower(Borrower _borrower) {
        this._borrower = _borrower;
    }

    public Book get_book() {
        return _book;
    }

    public void set_book(Book _book) {
        this._book = _book;
    }

    @Override
    public String toString() {
        return "Reserve{" +
                "\nReserve Date = " + _reserveDate +
                "\nBorrower = " + _borrower +
                "\nBook = " + _book +
                '}';
    }

    public String print() {
        return _book.get_name() + "\t\t" + _borrower.get_name() + "\t\t" + _reserveDate + "\n";
    }

    void printInfo() {
        System.out.println(this);
    }
}


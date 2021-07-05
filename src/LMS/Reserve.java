package LMS;

import java.util.Date;

public class Reserve {
    private int _id;
    private final Book _book;
    private final Borrower _borrower;
    private final Date _reserveDate;
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

    public Date get_reserveDate() {
        return _reserveDate;
    }

    public Borrower get_borrower() {
        return _borrower;
    }

    public Book get_book() {
        return _book;
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

    public void printInfo() {
        System.out.println(this);
    }
}


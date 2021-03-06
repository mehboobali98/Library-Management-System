package LMS;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Scanner;

public class Loan {
    private final int _loanID;
    private Date _issueDate;
    private Date _returnDate;
    private Staff _issuer;
    private Staff _receiver;
    private Borrower _borrower;
    private Book _book;
    private boolean _finePaid;
    private static int currentLoanId = 0;

    public Loan(int loanId, Date iDate, Date rDate, boolean fPaid, Staff i, Staff r, Borrower bor, Book b)  // Para cons.
    {
        if (loanId == -1) {
            currentLoanId++;
            _loanID = currentLoanId;
        } else {
            this._loanID = loanId;
        }
        _borrower = bor;
        _book = b;
        _issuer = i;
        _receiver = r;
        _issueDate = iDate;
        _returnDate = rDate;
        _finePaid = fPaid;
    }

    public Loan(int loanID, Date returnDate, Date issueDate, boolean finePaid) {
        this._loanID = loanID;
        this._issueDate = issueDate;
        this._returnDate = returnDate;
        this._finePaid = finePaid;
    }

    //Computes fine for a particular loan
    public double calculateFine() {
        double totalFine = 0;

        if (!_finePaid) {
            Date issueDate = _issueDate;
            Date returnDate = new Date(); // date on which the book was returned
            long daysBetween = ChronoUnit.DAYS.between(issueDate.toInstant(), returnDate.toInstant());
            if (daysBetween > 0)
                totalFine = daysBetween * Library.getInstance().fineAmount;
            else
                totalFine = 0;
        }
        return totalFine;
    }

    public void payFine() {

        double totalFine = calculateFine();
        if (totalFine > 0) {
            System.out.println("\nTotal Fine generated: Rs " + totalFine);
            System.out.println("Do you want to pay? (y/n)");
            Scanner input = new Scanner(System.in);
            String choice = input.next();
            if (choice.equalsIgnoreCase("y"))
                _finePaid = true;
            if (choice.equalsIgnoreCase("n"))
                _finePaid = false;
        } else {
            System.out.println("\nNo fine is generated.");
            _finePaid = true;
        }
    }

    // Extending issued Date
    public void renewIssuedBook(Date issueDate) {
        _issueDate = issueDate;
        System.out.println("\nThe deadline of the book " + this.get_book().get_name() + " has been extended.");
        System.out.println("Issued Book is successfully renewed!\n");
        int days = Library.getInstance().bookReturnDeadline;
        Date dueDate = Helper.addDays(_issueDate, days);
        System.out.println("The due date is: " + dueDate);
    }

    @Override
    public String toString() {
        return "Loan{" +
                "\nLoan Id       : " + _loanID +
                "\nissueDate     : " + _issueDate +
                "\nreturnDate    : " + _returnDate +
                "\nfinePaid      : " + _finePaid +
                "\nissuer        : " + _issuer +
                "\nreceiver      : " + _receiver +
                "\nborrower      : " + _borrower.print() +
                "\nbook          : " + _book.print() +
                '}';
    }

    public int get_loanID() {
        return _loanID;
    }

    public Date get_issueDate() {
        return _issueDate;
    }

    public Date get_returnDate() {
        return _returnDate;
    }

    public void set_returnDate(Date _returnDate) {
        this._returnDate = _returnDate;
    }

    public boolean is_finePaid() {
        return _finePaid;
    }

    public Staff get_issuer() {
        return _issuer;
    }

    public void set_receiver(Staff _receiver) {
        this._receiver = _receiver;
    }

    public Borrower get_borrower() {
        return _borrower;
    }

    public Book get_book() {
        return _book;
    }

}

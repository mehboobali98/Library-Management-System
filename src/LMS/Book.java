package LMS;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Book extends Item {
    private String _subject;
    boolean _isReferenced;
    boolean _isIssued;
    private List<String> _authorList;
    private final List<Loan> _loanList;
    private final List<Reserve> _reserveList;

    public Book(int ID, String name, boolean isIssued, String title, boolean isReferenced) {
        super(ID, name);
        _isIssued = isIssued;
        _subject = title;
        _isReferenced = isReferenced;
        _authorList = new ArrayList<>();
        _loanList = new ArrayList<>();
        _reserveList = new ArrayList<>();
    }

    public void reserveBook(Borrower bor) {
        boolean makeRequest = true;
        if (!is_isReferenced()) // referenced books cannot be reserved
        {
            if (bor.checkIfAlreadyBorrowed(this)) // book has already been borrowed by the user
            {
                System.out.println("\n" + "You have already borrowed " + this._name);
            } else {
                for (Reserve value : this._reserveList) {
                    if ((value.get_borrower() == bor)) {
                        makeRequest = false;
                        break;
                    }
                }
                if (makeRequest) {
                    Reserve reserve = new Reserve(-1, new Date(), bor, this);

                    addReserveRequest(reserve);
                    bor.addReserveBook(reserve);

                    System.out.println("\nThe book " + this._name + " has been successfully placed on hold by borrower " + bor.get_name() + ".\n");
                } else
                    System.out.println("\nYou already have one hold request for this book.\n");
            }
        }
    }

    public void addReserveRequest(Reserve r) {
        if (this._reserveList != null) {
            this._reserveList.add(r);
        }
    }

    public void issueBook(Borrower bor, Staff staff) {
        // TODO: 12/4/2019  name = title, subject = fiction etc
        if (bor.checkBorrowerEligibility()) {
            if (checkBookAvailability()) {
                set_isIssued(true);

                Loan l = new Loan(-1, new Date(), null, false, staff, null, bor, this);

                Library.getInstance().addLoanedItem(l); // getting loan object maintain loan history
                bor.addLoanedBook(l);  // adding it to borrower's loan list
                addLoan(l);
                System.out.println("\nThe book " + this._name + " is successfully issued to " + bor.get_name() + ".");
                System.out.println("\nIssued by: " + staff.get_name());
            } else {
                System.out.println("\nThe book " + this._name + " has already been issued or it is referenced.");
            }
        } else {
            System.out.println("\nThe borrower" + bor.get_name() + " is ineligible for borrowing a book");
        }
    }

    //return book
    public void checkInBook(Borrower bor, Staff staff, Loan l) {
        l.get_book().set_isIssued(false);
        l.set_returnDate(new Date());
        l.set_receiver(staff);

        bor.removeLoanedBook(l);  //removing references from loan list in borrower
        this.removeLoan(l);

        double fine = l.calculateFine();

        if (fine > 0) {
            l.payFine();  //pay fine extends check in book as per the use case
        }
        System.out.println("\nThe book " + l.get_book().get_name() + " is successfully returned by " + bor.get_name() + ".");
        System.out.println("\nReceived by: " + staff.get_name());
    }

    public boolean checkBookAvailability() {
        return !this.is_isIssued() && !this.is_isReferenced();
    }

    public boolean is_isIssued() {
        return _isIssued;
    }

    public void set_isIssued(boolean _isIssued) {
        this._isIssued = _isIssued;
    }

    public String get_subject() {
        return _subject;
    }

    public boolean is_isReferenced() {
        return _isReferenced;
    }

    public void set_isReferenced(boolean _isReferenced) {
        this._isReferenced = _isReferenced;
    }

    public List<String> get_authorList() {
        return _authorList;
    }

    public void set_authorList(List<String> _authorList) {
        this._authorList = _authorList;
    }

    public List<Reserve> get_reserveList() {
        return _reserveList;
    }

    public void removeReserve(Reserve reserve) {
        _reserveList.remove(reserve);
    }

    public void addLoan(Loan loan) {
        this._loanList.add(loan);
    }

    public void removeLoan(Loan loan) {
        this._loanList.remove(loan);
    }

    public void printInfo() {
        System.out.println("ID: " + this.get_ID());
        System.out.println("Subject: " + this._subject);
        System.out.println("Authors: ");
        for (int i = 0; i < this._authorList.size(); i++) {
            System.out.println(i + ":" + this._authorList.get(i));
        }

    }

    public void printReserveRequests() {
        if (this._reserveList != null) {
            System.out.println("\nReserve Requests are: ");

            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("No.\t\tBook's Title\t\t\tBorrower's Name\t\t\tRequest Date");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");

            for (int i = 0; i < this._reserveList.size(); i++) {
                System.out.print(i + "-" + "\t\t");
                this._reserveList.get(i).print();
            }
        } else {

            System.out.println("\nThere are no reservation requests for this book.");
        }
    }

    String print() {
        return "Book{" +
                "\nID              : " + _ID +
                "\nName            : " + _name +
                "\nIsIssued        : " + _isIssued +
                "\nTitle           : " + _subject +
                "\nIsReferenced    : " + _isReferenced +
                "\nAuthorList      : " + _authorList +
                "\n} ";
    }

    @Override
    public String toString() {
        return "Book{" +
                "\nID              : " + _ID +
                "\nName            : " + _name +
                "\nIsIssued        : " + _isIssued +
                "\nTitle           : " + _subject +
                "\nIsReferenced    : " + _isReferenced +
                "\nAuthorList      : " + _authorList +
                "\nLoanList        : " + _loanList +
                "\nReserveList     : " + _reserveList +
                "\n} ";
    }

    public void EditBook() {
        Scanner scanner = new Scanner(System.in);
        int option = -1;
        String optionMenu = "\n1 to edit ID" +
                "\n2 to edit Name" +
                "\n3 to edit Title" +
                "\n4 to edit AuthorList" +
                "\n0 to Exit";

        while (option != 0) {
            Color.Print(Color.ANSI_GREEN, optionMenu);
            option = Integer.parseInt(scanner.nextLine());
            if (option == 1) {
                System.out.println("Previous Name = " + this._name);
                System.out.print("Enter The New Name : ");
                this._name = scanner.nextLine();
            } else if (option == 2) {
                System.out.println("Previous Title = " + this._subject);
                System.out.print("Enter The New Title : ");
                this._subject = scanner.nextLine();
            } else if (option == 3) {
                System.out.println("Previous Authors = [" + _authorList + "]");
                System.out.print("Enter The New Author : ");
                this._authorList.add(scanner.nextLine());
            } else if (option == 4) {
                Color.Print(Color.ANSI_WHITE + Color.ANSI_RED_BACKGROUND, "Exiting Menu :)");
            } else {
                Color.Print(Color.ANSI_RED, "Option Dose not Exist");
            }
        }
    }
}

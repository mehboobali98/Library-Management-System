package LMS;

import java.util.ArrayList;
import java.util.Scanner;

public class Borrower extends Person {

    private ArrayList<Loan> loanedBooksList;
    private ArrayList<Reserve> reservedBookList;

    public Borrower(int id, String name, String password, String phoneNumber, String address) {
        super(id, name, password, phoneNumber, address);
        this.loanedBooksList = new ArrayList<>();
        this.reservedBookList = new ArrayList<>();
    }

    // remove loan object from loaned book list when the book has been returned
    public void removeLoanedBook(Loan l) {
        if (this.loanedBooksList != null) {
            this.loanedBooksList.remove(l);
        }
    }

    // Printing Book's Info Borrowed by Borrower
    public void printBorrowedBooks() {
        if (!this.loanedBooksList.isEmpty()) {
            System.out.println("\nBorrowed Books are: ");

            System.out.println("------------------------------------------------------------------------------");
            System.out.println("Loan id\t\tNo.\t\tTitle\t\t\tAuthor\t\t\tSubject");
            System.out.println("------------------------------------------------------------------------------");

            for (int i = 0; i < this.loanedBooksList.size(); i++) {
                System.out.print(i + "-" + "\t\t");
                System.out.println(this.loanedBooksList.get(i).get_loanID());
                this.loanedBooksList.get(i).get_book().printInfo();
                System.out.print("\n");
            }
        } else
            System.out.println("\nNo borrowed books.");
    }

    public void addLoanedBook(Loan l) {
        if (this.loanedBooksList != null) {
            this.loanedBooksList.add(l);
        }
    }

    public boolean checkBorrowerEligibility() {
        if (checkBorrowedBookCount() == true && checkFineStatus() == true) {
            return true;  // borrower will be eligible to issue a book if both are true
        } else {
            return false;
        }
    }

    public boolean checkBorrowedBookCount() {
        if (this.loanedBooksList != null) {
            if (this.loanedBooksList.size() > 4) {
                return false;  // cannot issue more than 4 books
            }
            return true;
        }
        return true;
    }

    public boolean checkFineStatus() {
        for (Loan l : loanedBooksList) {
            if (l.calculateFine() > 0) {
                return false;
            }
        }
        return true;
    }

    public boolean checkIfAlreadyBorrowed(Book b) {
        for (int i = 0; i < this.loanedBooksList.size(); i++) {
            if (this.loanedBooksList.get(i).get_book() == b) // book has already been loaned
            {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Reserve> get_reservedBooksList() {
        return reservedBookList;
    }

    public void set_reservedBooksList(ArrayList<Reserve> _reservedBooksList) {
        this.reservedBookList = _reservedBooksList;
    }

    public ArrayList<Loan> get_loanBooksList() {
        return loanedBooksList;
    }

    public void set_loanBooksList(ArrayList<Loan> _loanBooksList) {
        this.loanedBooksList = _loanBooksList;
    }

    public void addReserveBook(Reserve res) {
        this.reservedBookList.add(res);
    }

    public void removeReservedBook(Book book) {
        this.reservedBookList.remove(book);
    }

    @Override
    public String toString() {
        return "Borrower{" +
                "\nId                : " + _id +
                "\nName              : " + _name +
                "\nPassword          : " + _password +
                "\nPhoneNumber       : " + _phoneNumber +
                "\nAddress           : " + _address +
                "\nReservedBooksList : " + reservedBookList +
                "\nLoanBooksList     : " + loanedBooksList +
                "\n}";
    }

    public String print() {
        return super.toString();
    }

    public void EditBorrower() {
        Scanner scanner = new Scanner(System.in);
        int editOption = -1;

        String editMenu = "\n1 to edit id" +
                "\n2 to edit name" +
                "\n3 to edit password" +
                "\n4 to edit phoneNumber" +
                "\n5 to edit address" +
                "\n0 to exit";
        while (editOption != 0) {
            Color.Print(Color.ANSI_GREEN, editMenu);
            editOption = Integer.parseInt(scanner.nextLine());
            if (editOption == 1) {
                int id;
                System.out.println("Previous id = " + this.get_id());
                System.out.println("Enter New Borrower id ");
                id = Integer.parseInt(scanner.nextLine());
                this.set_id(id);
            } else if (editOption == 2) {
                String name;
                System.out.println("Previous name = " + this.get_name());
                System.out.println("Enter New Borrower Name ");
                name = scanner.nextLine();
                this.set_name(name);
            } else if (editOption == 3) {
                String password;
                System.out.println("Previous Password = " + this.get_password());
                System.out.println("Enter New Borrower Password ");
                password = scanner.nextLine();
                this.set_password(password);
            } else if (editOption == 4) {
                String phoneNumber;
                System.out.println("Previous Phone Number = " + this.get_phoneNumber());
                System.out.println("Enter New Borrower PhoneNumber ");
                phoneNumber = scanner.nextLine();
                this.set_phoneNumber(phoneNumber);
            } else if (editOption == 5) {
                String address;
                System.out.println("Previous address = " + this.get_address());
                System.out.println("Enter New Borrower address ");
                address = scanner.nextLine();
                this.set_address(address);
            } else {
                Color.Print(Color.ANSI_RED, "Option dose not exists");
            }
        }
    }
}

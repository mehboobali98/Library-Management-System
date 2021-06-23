package LMS;

import javax.swing.plaf.ColorUIResource;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Library {
    public static Library instance;
    public Person currentLogedIn = null;
    public double fineAmount;
    public int bookReturnDeadline; // number of days after which fine will be imposed
    public int reserve_request_expiry; //number of days after which a reserve request will expire
    public String libraryName;

    private Library() {
        this._db = DBAPI.getInstance();
        this.populateDB();
    }

    //TODO use ignoreCase in search functions
    private List<Librarian> _librarianList = new ArrayList<>();
    private List<DeskClerk> _deskClerkList = new ArrayList<>();
    private List<Borrower> _borrowerList = new ArrayList<>();
    private List<Book> _bookList = new ArrayList<>();
    private List<Loan> _loanList = new ArrayList<>();
    private List<Dvd> _dvdList = new ArrayList<>();
    private List<Magazine> _magazineList = new ArrayList<>();
    private List<Reserve> _reserveList = new ArrayList<>();
    private DBAPI _db;

    public static Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    void login() {
        System.out.println("---- Login ----");
        Scanner scanner = new Scanner(System.in);
        while (this.currentLogedIn == null) {
            System.out.print("Enter ID : ");
            int id = ExceptionInput.EInputInt("Please Enter Valid Id");
            System.out.print("Enter Password : ");
            String password = scanner.nextLine();

            if (password.equals("123456") && id == 7007) {
                Color.Print(Color.ANSI_GREEN, "Logged In as Admin");
                this.currentLogedIn = null;
                return;
            }

            for (Librarian librarian : _librarianList) {
                if (librarian.getId() == id && librarian.get_password().equals(password)) {
                    this.currentLogedIn = librarian;
                    System.out.println("Logged In as Librarian");
                    return;

                }
            }

            for (DeskClerk deskClerk : _deskClerkList) {
                if (deskClerk.get_id() == id && deskClerk.get_password().equals(password)) {
                    this.currentLogedIn = deskClerk;
                    System.out.println("Logged In as DeskClerk");
                    return;

                }
            }

            for (Borrower borrower : _borrowerList) {
                if (borrower.get_id() == id && borrower.get_password().equals(password)) {
                    this.currentLogedIn = borrower;
                    System.out.println("Logged in as Borrower");
                    return;
                }
            }

            Color.Print(Color.ANSI_RED, "Wrong username or password");
        }
    }

    public void populateDB() {
        this._librarianList = _db.getLibrarian();
        this._borrowerList = _db.getBorrower();
        this._deskClerkList = _db.getDeskClerk();
        this._bookList = _db.getBooks();
        this._dvdList = _db.getDvd();
        this._magazineList = _db.getMagazine();
        this.setLibraryName("Fast Library.");
        this.setBookReturnDeadline(7);
        this.setReserve_request_expiry(3);
        this.setFineAmount(1000);
    }

    public void populateLoan() {
        _loanList = new ArrayList<>();
        List<LoanDb> loanDbList = _db.getLoanDB();

        for (LoanDb ldb : loanDbList) {
            Loan l = new Loan(ldb._loanID, ldb._issueDate, ldb._returnDate, ldb._finePaid, getStaff(ldb._issuer), getStaff(ldb._receiver), getBorrower(ldb._borrower), getBook(ldb._book));
            System.out.println(l);
        }
    }

    // All Adding Function
    public void AddNewDeskClerk() {
        Scanner scanner = new Scanner(System.in);
        int id = 6000;
        System.out.println("Enter New Name");
        String name = scanner.nextLine();
        System.out.println("Enter New Password");
        String password = scanner.nextLine();
        System.out.println("Enter New Phone Number");
        String phoneNumber = scanner.nextLine();
        System.out.println("Enter New Address");
        String address = scanner.nextLine();
        System.out.println("Enter New Salary");
        double salary = ExceptionInput.EInputdouble("The Input is Not valid");
        System.out.println("Enter New DeskNumber");
        int deskNumber = ExceptionInput.EInputInt("The Input is Not valid");
        DeskClerk deskClerk = (DeskClerk) LibraryFactory.createPerson("desk clerk", id, name, password, phoneNumber, address, salary, deskNumber);
        _deskClerkList.add(deskClerk);
    }

    public void AddNewLibrarian() {
        Scanner scanner = new Scanner(System.in);
        int id = 6000;
        System.out.println("Enter New Name");
        String name = scanner.nextLine();
        System.out.println("Enter New Password");
        String password = scanner.nextLine();
        System.out.println("Enter New Phone Number");
        String phoneNumber = scanner.nextLine();
        System.out.println("Enter New Address");
        String address = scanner.nextLine();
        System.out.println("Enter New Salary");
        double salary = ExceptionInput.EInputdouble("The Input is Not valid");
        System.out.println("Enter New Office Number");
        int officeNumber = ExceptionInput.EInputInt("The Input is Not valid");
        Librarian librarian = (Librarian) LibraryFactory.createPerson("librarian", id, name, password, phoneNumber, address, salary, officeNumber);
        _librarianList.add(librarian);
    }

    public void AddNewBorrower() {
        Scanner scanner = new Scanner(System.in);
        int id = 6000;
        System.out.println("Enter New Name");
        String name = scanner.nextLine();
        System.out.println("Enter New Password");
        String password = scanner.nextLine();
        System.out.println("Enter New Phone Number");
        String phoneNumber = scanner.nextLine();
        System.out.println("Enter New Address");
        String address = scanner.nextLine();
        Borrower borrower = (Borrower) LibraryFactory.createPerson("borrower", id, name, password, phoneNumber, address, 0, 0);
        _borrowerList.add(borrower);
    }

    public void addNewBook() {
        Scanner scanner = new Scanner(System.in);
        int id = 698;
        System.out.println("Enter Book Name");
        String name = scanner.nextLine();
        System.out.println("Enter Book Subject");
        String subject = scanner.nextLine();
        Book book = (Book) LibraryFactory.createItem("book", id, name, subject);
        _bookList.add(book);
    }

    public void AddNewDvd() {
        Scanner scanner = new Scanner(System.in);
        int id = 56;
        System.out.println("Enter New Name");
        String name = scanner.nextLine();
        System.out.println("Enter New Composer Name");
        String composer = scanner.nextLine();
        Dvd dvd = (Dvd) LibraryFactory.createItem("dvd", id, name, composer);
        _dvdList.add(dvd);
    }

    public void AddNewMagazine() {
        Scanner scanner = new Scanner(System.in);
        int id = 56;
        System.out.println("Enter New Name");
        String name = scanner.nextLine();
        System.out.println("Enter New Company Name");
        String company = scanner.nextLine();
        Magazine magazine = (Magazine) LibraryFactory.createItem("magazine", id, name, company);
        _magazineList.add(magazine);

    }

    public void addLoanedItem(Loan l) {
        if (this._loanList != null) {
            this._loanList.add(l);
        }
    }

    void addNewReservation() {
        Scanner scanner = new Scanner(System.in);
        int id = 5;
        System.out.println("Enter Reservation Date");
        System.out.print("Enter Day   : ");
        int day = ExceptionInput.EInputInt("The Input is Not valid");
        System.out.print("Enter Month : ");
        int month = ExceptionInput.EInputInt("The Input is Not valid");
        System.out.print("Enter Year  : ");
        int year = ExceptionInput.EInputInt("The Input is Not valid");
        Date ReserveDate = new Date(year, month, day);


        Borrower borrower = getBorrower();
        Book book = getBook();
        if (borrower == null || book == null) {
            Color.Print(Color.ANSI_RED, "Operation Cancelled due to incomplete requirements");
            return;
        }

        Reserve reserve = new Reserve(id, ReserveDate, borrower, book);
        book.addReserveRequest(reserve);
    }

    // All Getters


    public int getReserve_request_expiry() {
        return reserve_request_expiry;
    }

    public void setReserve_request_expiry(int reserve_request_expiry) {
        this.reserve_request_expiry = reserve_request_expiry;
    }

    public int getBookReturnDeadline() {
        return bookReturnDeadline;
    }

    public void setBookReturnDeadline(int bookReturnDeadline) {
        this.bookReturnDeadline = bookReturnDeadline;
    }

    public Staff getStaff(int id) {
        for (DeskClerk deskClerk : _deskClerkList) {
            System.out.println(deskClerk);
            if (deskClerk.getId() == id) {
                return deskClerk;
            }
        }
        for (Librarian librarian : _librarianList) {
            System.out.println(librarian);
            if (librarian.getId() == id) {
                return librarian;
            }
        }
        return null;
    }

    public Book getBook(int id) {
        for (Book book : _bookList) {
            if (book.get_ID() == id)
                return book;
        }
        return null;
    }

    public Borrower getBorrower(int id) {
        for (Borrower borrower : _borrowerList) {
            if (borrower.get_id() == id)
                return borrower;
        }
        return null;
    }

    public Loan getLoan(int id) {
        for (Loan loan : _loanList) {
            if (loan.get_loanID() == id)
                return loan;
        }
        return null;
    }

    Book getBook() {
        Book book = null;
        Scanner scanner = new Scanner(System.in);
        String optionMenu = "\n1 to Print Book" +
                "\n2 to select Book by id";
        int option = -1;
        while (option != 0) {
            Color.Print(Color.ANSI_GREEN, optionMenu);
            option = ExceptionInput.EInputInt("The Input is Not valid");
            if (option == 1) {
                this.PrintAllBorrower();
            } else if (option == 2) {
                int id = ExceptionInput.EInputInt("The Input is Not valid");
                book = getBook(id);
                option = 0;
            }
        }
        if (book == null) {
            Color.Print(Color.ANSI_RED, "Id dont exit");
        }
        return book;
    }

    Borrower getBorrower() {
        Borrower borrower = null;
        Scanner scanner = new Scanner(System.in);
        String optionMenu = "\n1 to Print Borrower" +
                "\n2 to select borrower by id";
        int option = -1;
        while (option != 0) {
            Color.Print(Color.ANSI_GREEN, optionMenu);
            option = ExceptionInput.EInputInt("The Input is Not valid");
            if (option == 1) {
                this.PrintAllBorrower();
            } else if (option == 2) {
                int id = ExceptionInput.EInputInt("The Input is Not valid");
                borrower = getBorrower(id);
                option = 0;
            }
        }
        if (borrower == null) {
            Color.Print(Color.ANSI_RED, "Id dont exit");
        }
        return borrower;
    }

    // All PrintFunction
    void printAllMagazine() {
        for (Magazine magazine : _magazineList) {
            System.out.println("--------------------------");
            Color.Print(Color.ANSI_WHITE, magazine.toString());
        }
    }

    void printAllDvd() {
        for (Dvd dvd : _dvdList) {
            System.out.println("--------------------------");
            Color.Print(Color.ANSI_WHITE, dvd.toString());
        }
    }

    public void PrintAllLibrarians() {
        for (Librarian librarian : _librarianList) {
            Color.Print(Color.ANSI_BLUE, librarian.toString());
            System.out.println("------------------------------------------------");
        }
    }

    public void PrintAllDeskClerk() {
        for (DeskClerk deskClerk : _deskClerkList) {
            System.out.println("------------------------------------------------");
            Color.Print(Color.ANSI_CYAN, deskClerk.toString());
        }
    }

    public void PrintAllBooks() {
        for (Book book : _bookList) {
            System.out.println("------------------------------------------------");
            Color.Print(Color.ANSI_PURPLE, book.toString());

        }
    }

    public void PrintAllBorrower() {
        if (this._borrowerList != null) {
            for (Borrower borrower : _borrowerList) {
                Color.Print(Color.ANSI_YELLOW, borrower.print());
            }
        } else {
            Color.Print(Color.ANSI_RED, "There are no borrowers in the library.");

        }
    }

    public void PrintAllLoans() {
        if (this._loanList.size() > 0)
            for (Loan loan : _loanList) {
                Color.Print(Color.ANSI_CYAN, loan.toString());
            }
        else {
            Color.Print(Color.ANSI_RED, "Library has currently 0 books on loan.");
        }
    }

    void printAllReservations() {
        for (Reserve reserve : _reserveList) {
            System.out.println("--------------------------------");
            Color.Print(Color.ANSI_PURPLE, reserve.toString());
        }
    }

// Edit Functions

    public void EditDeskClerk() {
        Scanner scanner = new Scanner(System.in);
        DeskClerk deskClerk = null;
        System.out.println("Enter id");
        int dId = ExceptionInput.EInputInt("The Input is Not valid");
        for (DeskClerk deskClerk1 : _deskClerkList) {
            if (deskClerk1.getId() == dId) {
                deskClerk = deskClerk1;
            }
        }
        if (deskClerk == null) {
            Color.Print(Color.ANSI_RED, "DeskClerk dose not exists");
        } else {
            deskClerk.EditDeskClerk();
        }
    }

    public void EditBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter The ID to edit : ");
        int id = ExceptionInput.EInputInt("The Input is Not valid");
        for (Book book : _bookList) {
            if (book.get_ID() == id) {
                book.EditBook();
                return;
            }
        }
        Color.Print(Color.ANSI_RED, "No Book Found with " + id);
    }

    public void EditBorrower() {
        Scanner scanner = new Scanner(System.in);
        Borrower borrower = null;
        System.out.println("Enter id");
        int dId = ExceptionInput.EInputInt("The Input is Not valid");
        for (Borrower borrower1 : _borrowerList) {
            if (borrower1.get_id() == dId) {
                borrower = borrower1;
            }
        }
        if (borrower == null) {
            Color.Print(Color.ANSI_RED, "Librarian dose not exists");
        } else {
            borrower.EditBorrower();
        }

    }

    public void EditLibrarian() {
        Scanner scanner = new Scanner(System.in);
        Librarian librarian = null;
        System.out.println("Enter id");
        int dId = ExceptionInput.EInputInt("The Input is Not valid");
        for (Librarian librarian1 : _librarianList) {
            if (librarian1.getId() == dId) {
                librarian = librarian1;
            }
        }
        if (librarian == null) {
            Color.Print(Color.ANSI_RED, "Librarian dose not exists");
        } else {
            librarian.EditLibrarian();
        }


    }

    // All Delete Functions
    void DeleteDeskClerk() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter DeskClerk Id to delete");
        int id = ExceptionInput.EInputInt("The Input is Not valid");
        _deskClerkList.removeIf(deskClerk -> deskClerk.getId() == id);
    }

    void DeleteBorrower() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Borrower Id to delete");
        int id = ExceptionInput.EInputInt("The Input is Not valid");
        _borrowerList.removeIf(borrower -> borrower.get_id() == id);
    }

    void DeleteBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Librarian Id to delete");
        int id = ExceptionInput.EInputInt("The Input is Not valid");
        _bookList.removeIf(book -> book.get_ID() == id);
    }

    void DeleteLibrarian() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Librarian Id to delete");
        int id = ExceptionInput.EInputInt("The Input is Not valid");
        _librarianList.removeIf(librarian -> librarian.get_id() == id);
    }

//     All Menu Functions

    void deskClerkMenu() {
        Scanner scanner = new Scanner(System.in);
        int option = -1;
        String optionMenu =
                "\n1 to view DeskClerk" +
                        "\n2 to edit DeskClerk" +
                        "\n3 to delete DeskClerk" +
                        "\n4 to create DeskClerk" +
                        "\n0 to Exit(Previous Menu)";
        while (option != 0) {
            Color.Print(Color.ANSI_GREEN, optionMenu);
            System.out.print("Enter The Option : ");
            option = ExceptionInput.EInputInt("The Input is Not valid");
            if (option == 1) {
                this.PrintAllDeskClerk();
            } else if (option == 2) {
                this.EditDeskClerk();
            } else if (option == 3) {
                DeleteDeskClerk();
            } else if (option == 4) {
                this.AddNewDeskClerk();
            } else if (option == 0) {
                Color.Print(Color.ANSI_RED, "Exiting to Previous Menu");

            } else {
                Color.Print(Color.ANSI_RED, "Option dose not exists");

            }
        }
    }

    void librarianMenu() {
        Scanner scanner = new Scanner(System.in);
        int option = -1;
        String optionMenu =
                "\n1 to view Librarian" +
                        "\n2 to edit Librarian" +
                        "\n3 to delete Librarian" +
                        "\n4 to create Librarian" +
                        "\n0 to Exit(Previous Menu)";
        while (option != 0) {
            Color.Print(Color.ANSI_GREEN, optionMenu);
            System.out.print("Enter The Option : ");
            option = ExceptionInput.EInputInt("The Input is Not valid");
            if (option == 1) {
                this.PrintAllLibrarians();
            } else if (option == 2) {
                this.EditLibrarian();
            } else if (option == 3) {
                this.DeleteLibrarian();
            } else if (option == 4) {
                this.AddNewLibrarian();
            } else if (option == 0) {
                Color.Print(Color.ANSI_RED, "Exiting to Previous Menu");

            } else {
                Color.Print(Color.ANSI_RED, "Option dose not exists");

            }
        }
    }

    void loanMenu() {
        Scanner scanner = new Scanner(System.in);
        int option = -1;
        String optionMenu =
                "\n1 to view Loans" +
                        "\n2 to Renew" +
                        "\n3 to Create Loan" +
                        "\n0 to Exit(Previous Menu)";
        while (option != 0) {
            Color.Print(Color.ANSI_GREEN, optionMenu);
            System.out.print("Enter The Option : ");
            option = ExceptionInput.EInputInt("The Input is Not valid");
            if (option == 1) {
                this.PrintAllLoans();
            } else if (option == 2) {
                this.RenewLoan();
            } else if (option == 3) {
                this.GiveLoanToBorrower(this.currentLogedIn);
            } else if (option == 0) {
                Color.Print(Color.ANSI_RED, "Exiting to Previous Menu");

            } else {
                Color.Print(Color.ANSI_RED, "Option dose not exists");
            }
        }
    }

    void BookMenu() {
        Scanner scanner = new Scanner(System.in);
        int option = -1;
        String optionMenu =
                "\n1 to view Book" +
                        "\n2 to edit Book" +
                        "\n3 to delete Book" +
                        "\n4 to create Book" +
                        "\n0 to Exit(Previous Menu)";
        while (option != 0) {
            Color.Print(Color.ANSI_GREEN, optionMenu);
            System.out.print("Enter The Option : ");
            option = ExceptionInput.EInputInt("The Input is Not valid");
            if (option == 1) {
                this.PrintAllBooks();
            } else if (option == 2) {
                this.EditBook();
            } else if (option == 3) {
                this.DeleteBook();
            } else if (option == 4) {
                this.addNewBook();
            } else if (option == 0) {
                Color.Print(Color.ANSI_RED, "Exiting to Previous Menu");

            } else {
                Color.Print(Color.ANSI_RED, "Option dose not exists");

            }
        }
    }

    void borrowerMenu() {
        Scanner scanner = new Scanner(System.in);
        int option = -1;
        String optionMenu =
                "\n1 to view Borrower" +
                        "\n2 to edit Borrower" +
                        "\n3 to delete Borrower" +
                        "\n4 to create Borrower" +
                        "\n0 to Exit(Previous Menu)";
        while (option != 0) {
            Color.Print(Color.ANSI_GREEN, optionMenu);
            System.out.print("Enter The Option : ");
            option = ExceptionInput.EInputInt("The Input is Not valid");
            if (option == 1) {
                this.PrintAllBorrower();
            } else if (option == 2) {
                this.EditBorrower();
            } else if (option == 3) {
                this.DeleteBorrower();
            } else if (option == 4) {
                this.AddNewBorrower();
            } else if (option == 0) {
                Color.Print(Color.ANSI_RED, "Exiting to Previous Menu");

            } else {
                Color.Print(Color.ANSI_RED, "Option dose not exists");

            }
        }
    }

    void RemoveReservationMenu() {
        Scanner scanner = new Scanner(System.in);
        int option = -1;
        String optionMenu = "\n1 to view All reservation" +
                "\n2 to Enter Id to remove Reservation" +
                "\n0 to Exit";

        while (option != 0) {
            Color.Print(Color.ANSI_GREEN, optionMenu);
            option = ExceptionInput.EInputInt("The Input is Not valid");
            if (option == 1) {
                this.printAllReservations();
            } else if (option == 2) {
                this.removeReservation();
            } else if (option == 0) {
                Color.Print(Color.ANSI_RED, "Exiting to Previous Menu");

            } else {
                Color.Print(Color.ANSI_RED, "Option dose not exists");

            }
        }
    }

    void reserveMenu() {

        Scanner scanner = new Scanner(System.in);
        int option = -1;
        String optionMenu =
                "\n1 to view Reservations" +
                        "\n2 to Add new Reservation" +
                        "\n3 to Remove Reservation" +
                        "\n0 to Exit(Previous Menu)";
        while (option != 0) {
            Color.Print(Color.ANSI_GREEN, optionMenu);
            System.out.print("Enter The Option : ");
            option = ExceptionInput.EInputInt("The Input is Not valid");
            if (option == 1) {
                this.printAllReservations();
            } else if (option == 2) {
                this.addNewReservation();
            } else if (option == 3) {
                this.RemoveReservationMenu();
            } else if (option == 0) {
                Color.Print(Color.ANSI_RED, "Exiting to Previous Menu");

            } else {
                Color.Print(Color.ANSI_RED, "Option dose not exists");
            }
        }
    }

    void itemMenu() {
        login();
        if (currentLogedIn != null) {
            Scanner scanner = new Scanner(System.in);
            int option = -1;
            String optionMenu =
                    "\n1 to view Dvd" +
                            "\n2 to view Magazine" +
                            "\n3 to add DVD" +
                            "\n4 to add Magazine" +
                            "\n0 to Exit(Previous Menu)";
            while (option != 0) {
                Color.Print(Color.ANSI_GREEN, optionMenu);
                System.out.print("Enter The Option : ");
                option = ExceptionInput.EInputInt("The Input is Not valid");
                if (option == 1) {
                    this.printAllDvd();
                } else if (option == 2) {
                    this.printAllMagazine();
                } else if (option == 3) {
                    this.AddNewDvd();
                } else if (option == 4) {
                    this.AddNewMagazine();
                } else if (option == 0) {
                    Color.Print(Color.ANSI_RED, "Exiting to Previous Menu");

                } else {
                    Color.Print(Color.ANSI_RED, "Option dose not exists");
                }
            }
        }
    }

    // Role Based Menu()
    void AdminMenu() {
        Color.Print(Color.ANSI_CYAN, "WELCOME ADMIN");
        Scanner scanner = new Scanner(System.in);
        int option = -1;
        String options =
                "\n1 - for DeskClerk Options" +
                        "\n2 - Librarians Options" +
                        "\n3 - View All Books In Library" +
                        "\n4 - View Issued Items History" +
                        "\n0 to logout";

        while (option != 0) {
            Color.Print(Color.ANSI_GREEN, options);
            option = ExceptionInput.EInputInt("The Input is Not valid");
            if (option == 1) {
                this.deskClerkMenu();
            } else if (option == 2) {
                this.librarianMenu();
            } else if (option == 3) {
                this.PrintAllBooks();
            } else if (option == 4) {
                this.PrintAllLoans();
            } else if (option == 0) {
                Color.Print(Color.ANSI_RED, "Logging out");
                Logout();
            } else {
                Color.Print(Color.ANSI_RED, "Option dose not exists");
            }

        }
    }

    void LibrarianMenu() {
        Color.Print(Color.ANSI_CYAN, "WELCOME MR. " + this.currentLogedIn.get_name());
        Scanner scanner = new Scanner(System.in);
        int option = -1;
        String options =
                "\n1 - Item Options" + //add, delete, update operations
                        "\n2 - View Issued Items History" +
                        "\n3 - Borrower Options" +
                        "\n4 - Book Options " +
                        "\n5 - Check in a Book " + //return a book
                        "\n6 - Check out a Book" + //issue a book
                        "\n7 - Reserve a Book" +
                        "\n8 - Renew a Book" +
                        "\n9 - Search item(s) in library" +
                        "\n10 - Compute total fine of a borrower" +
                        "\n11 - Check reserve requests queue of a book" +
                        "\n0 to logout";

        while (option != 0) {
            Color.Print(Color.ANSI_GREEN, options);
            option = ExceptionInput.EInputInt("The Input is Not valid");
            if (option == 1) {
                this.itemMenu();
            } else if (option == 2) {
                this.PrintAllLoans();
            } else if (option == 3) {
                this.borrowerMenu();
            } else if (option == 4) {
                this.BookMenu();
            } else if (option == 5) {
                this.returnBook(this.currentLogedIn);
            } else if (option == 6) {
                this.GiveLoanToBorrower(this.currentLogedIn);
            } else if (option == 7) {
                this.reserveBook(this.currentLogedIn);
            } else if (option == 8) {
                this.RenewLoan();
            } else if (option == 9) {
                this.megaSearch();
            } else if (option == 10) {
                this.computeTotalFine(this.currentLogedIn);
            } else if (option == 11) {
                this.printReserveQueueOfABook();
            } else if (option == 0) {
                Color.Print(Color.ANSI_RED, "Logging out");
                Logout();
            } else {
                Color.Print(Color.ANSI_RED, "Option dose not exists");
            }
        }
    }

    void DeskClerkMenu() {
        Color.Print(Color.ANSI_CYAN, "WELCOME MR. " + this.currentLogedIn.get_name());
        Scanner scanner = new Scanner(System.in);
        int option = -1;
        String options =
                "\n1 - Borrower Options" + //add,delete, update
                        "\n2 - Check in a Book " + //return a book
                        "\n3 - Check out a Book" + //issue a book
                        "\n4 - Reserve a Book" +
                        "\n5 - Renew a Book" +
                        "\n6 - Search item(s) in library" +
                        "\n7 - Compute total fine of a borrower" +
                        "\n8 - Check reserve requests queue of a book" +
                        "\n0 to logout";

        while (option != 0) {
            Color.Print(Color.ANSI_GREEN, options);
            option = ExceptionInput.EInputInt("The Input is Not valid");
            if (option == 1) {
                this.borrowerMenu();
            } else if (option == 2) {
                this.returnBook(this.currentLogedIn);
            } else if (option == 3) {
                this.GiveLoanToBorrower(this.currentLogedIn);
            } else if (option == 4) {
                this.reserveBook(this.currentLogedIn);
            } else if (option == 5) {
                this.RenewLoan();
            } else if (option == 6) {
                this.megaSearch();
            } else if (option == 7) {
                this.computeTotalFine(this.currentLogedIn);
            } else if (option == 8) {
                this.printReserveQueueOfABook();
            } else if (option == 0) {
                Color.Print(Color.ANSI_RED, "Logging out");
                Logout();
            } else {
                Color.Print(Color.ANSI_RED, "Option dose not exists");
            }
        }
    }

    void BorrowerMenu() {
        Color.Print(Color.ANSI_CYAN, "WELCOME MR. " + this.currentLogedIn.get_name());
        Scanner scanner = new Scanner(System.in);
        int option = -1;
        String options =
                "\n1 - Search Books in Library" +
                        "\n2 - Search Items in Library" +
                        "\n3 - View Personal Information" +
                        "\n4 - Reserve a Book" +
                        "\n5 - Check Total Fine" +
                        "\n6 - View Borrowed Books Information" +
                        "\n0 to logout";

        while (option != 0) {
            Color.Print(Color.ANSI_GREEN, options);
            option = ExceptionInput.EInputInt("The Input is Not valid");
            if (option == 1) {
                this.bookSearch();
            } else if (option == 2) {
                this.megaSearch();
            } else if (option == 3) {
                this.viewBorrowersPersonalInfo(currentLogedIn);
            } else if (option == 4) {
                this.reserveBook(currentLogedIn);
            } else if (option == 5) {
                this.computeTotalFine(currentLogedIn);
            } else if (option == 6) {
                this.printBorrowedBooks(currentLogedIn);
            } else if (option == 0) {
                Color.Print(Color.ANSI_RED, "Logging out");
                Logout();
            } else {
                Color.Print(Color.ANSI_RED, "Option dose not exists");
            }
        }
    }

    public void printReserveQueueOfABook() {
        ArrayList<Book> books = this.bookSearch();
        Scanner scanner = new Scanner(System.in);

        if (books != null) {
            Book book = _bookList.get(2);
            int option1 = -1;
            while (option1 != 0) {
                System.out.println("1 - to Enter Id of Book");
                System.out.println("0 - to Exit");
                option1 = Integer.parseInt(scanner.nextLine());
                if (option1 == 1) {
                    int id;
                    Book temp = null;
                    while (temp == null) {
                        System.out.print("Enter The Id Of Book : ");
                        id = Integer.parseInt(scanner.nextLine());
                        temp = getBookFromMatchedBooks(books, id);
                        if (temp == null) {
                            Color.Print(Color.ANSI_RED, "Book with id " + id + " dose not exist");
                        } else {
                            if (temp._reserveList.size() > 0) {
                                Color.Print(Color.ANSI_BLUE, "Reservation Requests for this book are: ");
                                temp.printReserveRequests();
                            } else {
                                Color.Print(Color.ANSI_RED, "There are no reservation requests for this book.");
                            }
                        }
                    }
                    book = temp;
                    option1 = 0;
                } else if (option1 == 0) {
                    Color.Print(Color.ANSI_RED, "Operation Rejected");
                    return;
                }
            }
        }
    }


    public void returnBook(Person person) {
        Borrower borrower = this.findBorrowerInLibrary();

        if (borrower != null) {
            Loan l = this.findBorrowerLoanedBook(borrower);

            if (l != null) {
                l.get_book().checkInBook(borrower, (Staff) person, l);

            } else {
                Color.Print(Color.ANSI_RED, "Borrower has no books to return.");
            }

        }
    }

    public void printBorrowedBooks(Person person) {
        if ("DeskClerk".equals(person.getClass().getSimpleName()) || "Librarian".equals(person.getClass().getSimpleName())) {
            Borrower bor = this.findBorrowerInLibrary();

            if (bor != null)
                bor.printBorrowedBooks();
        } else {
            int id = person.get_id();
            Borrower bor = getBorrower(id);
            if (bor != null) {
                bor.printBorrowedBooks();
            }
        }
    }

    public void viewBorrowersPersonalInfo(Person person) {
        if ("DeskClerk".equals(person.getClass().getSimpleName()) || "Librarian".equals(person.getClass().getSimpleName())) {
            Borrower bor = this.findBorrowerInLibrary();

            if (bor != null)
                bor.printInfo();
        } else
            person.printInfo();
    }

    public void reserveBook(Person person) {
        // Borrower borrower = (Borrower) person;
        Book book = _bookList.get(2);
        Scanner scanner = new Scanner(System.in);
        ArrayList<Book> searchedBooks = this.bookSearch();
        if (searchedBooks != null) {
            int option1 = -1;
            while (option1 != 0) {
                System.out.println("--------1 to Enter Id of Book you want to reserve");
                System.out.println("--------0 to Exit");
                option1 = Integer.parseInt(scanner.nextLine());
                if (option1 == 1) {
                    int id;
                    Book temp = null;
                    while (temp == null) {
                        System.out.print("Enter The Id Of Book : ");
                        id = Integer.parseInt(scanner.nextLine());
                        temp = getBookFromMatchedBooks(searchedBooks, id);
                        if (temp == null) {
                            Color.Print(Color.ANSI_RED, "Book with id " + id + " dose not exist");
                        } else {
                            if ("DeskClerk".equals(person.getClass().getSimpleName()) || "Librarian".equals(person.getClass().getSimpleName())) {
                                Borrower bor = this.findBorrowerInLibrary();

                                if (bor != null) {
                                    temp.reserveBook(bor);
                                    Color.Print(Color.ANSI_GREEN_BACKGROUND + Color.ANSI_WHITE, "Book has been reserved.");
                                }
                            } else {
                                temp.reserveBook((Borrower) person); //borrower
                                Color.Print(Color.ANSI_GREEN_BACKGROUND + Color.ANSI_WHITE, "Book has been reserved.");
                            }
                        }
                    }
                    book = temp;
                    option1 = 0;
                } else if (option1 == 0) {
                    Color.Print(Color.ANSI_RED, "Operation Rejected");
                    return;
                }
            }
            //   if (borrower == null || book == null) {
            //       Color.Print(Color.ANSI_RED, "Operation Cancelled due to incomplete requirements");
            //       return;
            //   }

            //  book.reserveBook((Borrower) person); // calling issue book function of after checking everything
            //    Color.Print(Color.ANSI_GREEN_BACKGROUND + Color.ANSI_WHITE, "New Loan Book Added");
        }
    }


    Book getBookFromMatchedBooks(ArrayList<Book> b_list, int id) {
        for (Book b : b_list) {
            if (b.get_ID() == id) {
                return b;
            }
        }

        return null;
    }

    public void RunLibrary() {
        int main = -1;
        String menu = "\n1 to Login" +
                "\n2 to exit";
        while (main != 0) {
            Color.Print(Color.ANSI_GREEN, menu);
            System.out.print("Enter Option : ");
            main = ExceptionInput.EInputInt("Wrong Enter Integer");
            if (main == 1) {
                login();
                if (currentLogedIn == null) {
                    AdminMenu();
                } else if (currentLogedIn instanceof Librarian) {
                    LibrarianMenu();
                } else if (currentLogedIn instanceof DeskClerk) {
                    this.DeskClerkMenu();
                } else if (currentLogedIn instanceof Borrower) {
                    this.BorrowerMenu();
                }
            } else if (main == 0) {
                Color.Print(Color.ANSI_RED, "Exiting LMS Thanks!!!!!!");
            }
        }
    }

    public void Run() {
        login();


        Scanner scanner = new Scanner(System.in);
        int option = -1;
        String options =
                "\n1 for DeskClerk Options" +
                        "\n2 for Librarians Options" +
                        "\n3 for Borrowers Options" +
                        "\n4 for Books Options" +
                        "\n5 for Item Options" +
                        "\n6 for Loan Options" +
                        "\n7 for Reserve" +
                        "\n0 to exit";

        while (option != 0) {
            Color.Print(Color.ANSI_GREEN, options);
            option = ExceptionInput.EInputInt("The Input is Not valid");
            if (option == 1) {
                this.deskClerkMenu();
            } else if (option == 2) {
                this.librarianMenu();
            } else if (option == 3) {
                this.borrowerMenu();
            } else if (option == 4) {
                this.BookMenu();
            } else if (option == 5) {
                this.itemMenu();
            } else if (option == 6) {
                this.loanMenu();
            } else if (option == 7) {
                this.reserveMenu();
            } else if (option == 0) {
                Color.Print(Color.ANSI_RED, "Exiting the Program");
            } else {
                Color.Print(Color.ANSI_RED, "Option dose not exists");
            }

        }
    }

    public void RenewLoan() {

        Borrower borrower = findBorrowerInLibrary();
        if (borrower != null) {
            Loan l = this.findBorrowerLoanedBook(borrower);

            if (l != null) {
                l.renewIssuedBook(new Date());

            } else {
                Color.Print(Color.ANSI_RED, "\nThis borrower " + borrower.get_name() + " has no issued book which can be renewed.");
            }
        }

    }


    public void GiveLoanToBorrower(Person person) {
        Scanner scanner = new Scanner(System.in);
        Borrower borrower = findBorrowerInLibrary();
        Book book = _bookList.get(2);

        if (borrower != null) {
            ArrayList<Book> searchedBooks = this.bookSearch();
            if (searchedBooks != null) {
                int option1 = -1;
                while (option1 != 0) {
                    System.out.println("1 - to Enter Id of Book");
                    System.out.println("0 - to Exit");
                    option1 = Integer.parseInt(scanner.nextLine());
                    if (option1 == 1) {
                        int id;
                        Book temp = null;
                        while (temp == null) {
                            System.out.print("Enter The Id Of Book : ");
                            id = Integer.parseInt(scanner.nextLine());
                            temp = getBookFromMatchedBooks(searchedBooks, id);
                            if (temp == null) {
                                Color.Print(Color.ANSI_RED, "Book with id " + id + " dose not exist");
                            } else {
                                book.issueBook(borrower, (Staff) person); // calling issue book function of after checking everything
                                Color.Print(Color.ANSI_GREEN_BACKGROUND + Color.ANSI_WHITE, "New Loan Book Added");
                            }
                        }
                        book = temp;
                        option1 = 0;
                    } else if (option1 == 0) {
                        Color.Print(Color.ANSI_RED, "Operation Rejected");
                        return;
                    }
                }
            }
        }
        if (borrower == null || book == null) {
            Color.Print(Color.ANSI_RED, "Operation Cancelled due to incomplete requirements");
            return;
        }

    }

    void removeReservation() {
        Scanner scanner = new Scanner(System.in);
        int id = ExceptionInput.EInputInt("The Input is Not valid");

        for (Reserve reserve : _reserveList) {
            if (reserve.get_id() == id) {
                reserve.get_book().removeReserve(reserve);
                reserve.get_borrower().removeReservedBook(reserve.get_book());
            }
        }
        _reserveList.removeIf(reserve -> reserve.get_id() == id);


        System.out.println("Reservation of id " + id + " Removed");
    }

    public void computeTotalFine(Person person) {
        if ("DeskClerk".equals(person.getClass().getSimpleName()) || "Librarian".equals(person.getClass().getSimpleName())) {
            Borrower bor = this.findBorrowerInLibrary();

            if (bor != null) {
                double totalFine = this.computeFine2(bor);
                System.out.println("\nYour Total Fine is : Rs " + totalFine);
            }
        } else {
            double totalFine = this.computeFine2((Borrower) person);
            System.out.println("\nYour Total Fine is : Rs " + totalFine);
        }

    }

    //Computes total fine for all loans of a borrower
    public double computeFine2(Borrower borrower) {
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("No.\t\tBook's Title\t\tBorrower's Name\t\t\tIssued Date\t\t\tReturned Date\t\t\t\tFine(Rs)");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        double totalFine = 0;
        double per_loan_fine = 0;

        for (int i = 0; i < this._loanList.size(); i++) {
            Loan l = this._loanList.get(i);

            if ((l.get_borrower() == borrower)) {
                per_loan_fine = l.calculateFine();
                System.out.print(i + "-" + "\t\t" + this._loanList.get(i).get_book().get_name() + "\t\t\t" + this._loanList.get(i).get_borrower().get_name() + "\t\t" + this._loanList.get(i).get_issueDate() + "\t\t\t" + this._loanList.get(i).get_returnDate() + "\t\t\t\t" + per_loan_fine + "\n");
                totalFine += per_loan_fine;
            }
        }
        return totalFine;
    }

    public Loan findBorrowerLoanedBook(Borrower bor) {
        Scanner scanner = new Scanner(System.in);
        int option = -1;

        System.out.println("Enter The Loan Info");
        while (option != 0) {
            System.out.println("1 to Print All the loans of borrower");
            System.out.println("2 to Enter Id of loan");
            System.out.println("0 to Exit");
            System.out.print("Enter New Option : ");
            option = Integer.parseInt(scanner.nextLine());
            if (option == 1) {
                bor.printBorrowedBooks();
            } else if (option == 2) {
                int id;
                Loan temp = null;
                while (temp == null) {
                    System.out.print("Enter The Id Of Loan : ");
                    id = Integer.parseInt(scanner.nextLine());
                    temp = getLoan(id);
                    if (temp == null) {
                        Color.Print(Color.ANSI_RED, "Loan with id " + id + " dose not exist");
                        return null;
                    } else {
                        return temp; // returning the found loaned object
                    }
                }
                option = 0;
            } else if (option == 0) {
                Color.Print(Color.ANSI_RED, "Operation Rejected");
                return null;
            }
        }
        return null;
    }

    public Borrower findBorrowerInLibrary() {
        Scanner scanner = new Scanner(System.in);
        int option = -1;

        System.out.println("Enter The Borrower Info");
        while (option != 0) {
            System.out.println("1 to Print All Borrower");
            System.out.println("2 to Enter Id of Borrower");
            System.out.println("0 to Exit");
            System.out.print("Enter New Option : ");
            option = Integer.parseInt(scanner.nextLine());
            if (option == 1) {
                this.PrintAllBorrower();
            } else if (option == 2) {
                int id;
                Borrower temp = null;
                while (temp == null) {
                    System.out.print("Enter The Id Of Borrower : ");
                    id = Integer.parseInt(scanner.nextLine());
                    temp = getBorrower(id);
                    if (temp == null) {
                        Color.Print(Color.ANSI_RED, "Borrower with id " + id + " dose not exist");
                        return null;
                    } else {
                        return temp; // returning the found borrower
                    }
                }
                option = 0;
            } else if (option == 0) {
                Color.Print(Color.ANSI_RED, "Operation Rejected");
                return null;
            }
        }
        return null;
    }

    // Book Searching

    //function to search books for a borrower
    public ArrayList<Book> bookSearch() {
        Scanner scanner = new Scanner(System.in);
        int option = -1;
        String options = "\n1 for Search Book by Subject" +
                "\n2 for Search Book by Author" +
                "\n0 to exit";

        ArrayList<Book> matchedBooks = new ArrayList<Book>();

        while (option != 0) {
            Color.Print(Color.ANSI_GREEN, options);
            option = scanner.nextInt();
            scanner.nextLine();
            String searchQuery;
            if (option == 1) {
                System.out.print("Enter Book Subject: ");
                searchQuery = scanner.nextLine();
                ArrayList<Book> books = this.searchBookBySubject(searchQuery);
                for (Book book : books) {
                    matchedBooks.add(book);
                }
            } else if (option == 2) {
                System.out.print("Enter Book Author Name: ");
                searchQuery = scanner.nextLine();
                ArrayList<Book> books = this.searchBookByAuthor(searchQuery);
                for (Book book : books) {
                    matchedBooks.add(book);
                }
            } else {
                Color.Print(Color.ANSI_RED, "Option dose not exists");
            }

        }
        //Printing all the matched Books
        if (!matchedBooks.isEmpty()) {
            System.out.println("\nThese books are found: \n");

            for (int i = 0; i < matchedBooks.size(); i++) {
                System.out.print(i + "- ");
                matchedBooks.get(i).printInfo();
                System.out.print("\n");
            }

            return matchedBooks;
        } else {
            System.out.println("\nSorry. No Books were found related to your query.");
            return null;
        }
    }

    /**
     * @param bookArg = book subject query
     * @return returns the books having book subject query
     */
    public ArrayList<Book> searchBookBySubject(String bookArg) {

        ArrayList<Book> booksToReturn = new ArrayList<Book>();

        for (Book book : this._bookList) {
            if (Helper.contains(book.get_subject(), bookArg)) {
                booksToReturn.add(book);
            }
        }

        return booksToReturn;
    }

    /**
     * @param authorArg = author query
     * @return returns the books having book title query
     */
    public ArrayList<Book> searchBookByAuthor(String authorArg) {

        ArrayList<Book> booksToReturn = new ArrayList<Book>();

        for (Book book : this._bookList) {
            boolean found = false;
            for (int i = 0; i < book.get_authorList().size() && !found; i++) {
                if (Helper.contains(book.get_authorList().get(i), authorArg)) {
                    booksToReturn.add(book);
                }
            }
        }

        return booksToReturn;
    }

    /**
     * @param composerArg = composer query
     * @return returns the dvds having composer query
     */
    public ArrayList<Dvd> searchDvdByComposer(String composerArg) {

        ArrayList<Dvd> dvdsToReturn = new ArrayList<Dvd>();

        for (Dvd dvd : this._dvdList) {
            if (Helper.contains(dvd._composer, composerArg))
                dvdsToReturn.add(dvd);
        }

        return dvdsToReturn;
    }

    /**
     * @param companyArg = company query
     * @return returns the magazines having company query
     */
    public ArrayList<Magazine> searchMagazineByCompany(String companyArg) {

        ArrayList<Magazine> magazinesToReturn = new ArrayList<Magazine>();

        for (Magazine magazine : this._magazineList) {
            if (Helper.contains(magazine._company, companyArg)) {
                magazinesToReturn.add(magazine);
            }
        }

        return magazinesToReturn;
    }

    /**
     * @param itemName = item name query
     * @return returns the items having itemName query
     */
    public ArrayList<Item> searchItemsByName(String itemName) {

        ArrayList<Item> itemsToReturn = new ArrayList<Item>();

        for (Item item : this._magazineList) {
            if (Helper.contains(item.get_name(), itemName)) {
                itemsToReturn.add(item);
            }
        }

        for (Item item : this._dvdList) {
            if (Helper.contains(item.get_name(), itemName)) {
                itemsToReturn.add(item);
            }
        }

        for (Item item : this._bookList) {
            if (Helper.contains(item.get_name(), itemName)) {
                itemsToReturn.add(item);
            }
        }

        return itemsToReturn;
    }

    public ArrayList<Item> megaSearch() {
        Scanner scanner = new Scanner(System.in);
        int option = -1;
        String options = "\n1 for Search Item by Name" +
                "\n2 for Search Book by Subject" +
                "\n3 for Search Book by Author" +
                "\n4 for Search Magazine by Company" +
                "\n5 for Search Dvd by Composer" +
                "\n0 to exit";
        ArrayList<Item> items = new ArrayList<Item>();

        while (option != 0) {
            Color.Print(Color.ANSI_GREEN, options);
            option = scanner.nextInt();
            scanner.nextLine();
            String searchQuery;
            if (option == 1) {
                System.out.print("Enter Item Name: ");
                searchQuery = scanner.nextLine();
                items = this.searchItemsByName(searchQuery);
            } else if (option == 2) {
                System.out.print("Enter Book Subject: ");
                searchQuery = scanner.nextLine();
                ArrayList<Book> books = this.searchBookBySubject(searchQuery);
                for (Book book : books) {
                    items.add(book);
                }
            } else if (option == 3) {
                System.out.print("Enter Book Author Name: ");
                searchQuery = scanner.nextLine();
                ArrayList<Book> books = this.searchBookByAuthor(searchQuery);
                for (Book book : books) {
                    items.add(book);
                }

            } else if (option == 4) {
                System.out.print("Enter Magazine Company: ");
                searchQuery = scanner.nextLine();
                ArrayList<Magazine> magazines = this.searchMagazineByCompany(searchQuery);
                for (Magazine magazine : magazines) {
                    items.add(magazine);
                }

            } else if (option == 5) {
                System.out.print("Enter DVD Composer: ");
                searchQuery = scanner.nextLine();
                ArrayList<Dvd> dvds = this.searchDvdByComposer(searchQuery);
                for (Dvd dvd : dvds) {
                    items.add(dvd);
                }

            } else {
                Color.Print(Color.ANSI_RED, "Option dose not exists");
            }

        }
        return items;
    }

    public void Logout() {
        this.currentLogedIn = null;
    }
}

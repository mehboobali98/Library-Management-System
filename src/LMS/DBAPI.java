package LMS;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBAPI {
    private static DBAPI instance = new DBAPI();
    private String url = "jdbc:mysql://localhost:3306/LMS";
    private String user = "root";
    private String password = "Ronaldo07";
    private Connection connection;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private DBAPI() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<Book> getBooks() {
        List<Book> bookList = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(this.url, this.user, this.password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from books");
            while (rs.next()) {
                Book temp = (Book) LibraryFactory.createItem("book", rs.getInt(1), rs.getString(2), rs.getString(4));
                temp.set_isIssued(rs.getBoolean(3));
                temp.set_isReferenced(rs.getBoolean(5));
                temp.set_authorList(getBookAuthors(temp.get_ID()));
                bookList.add(temp);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return bookList;
    }

    public List<Dvd> getDvd() {
        List<Dvd> dvdList = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(this.url, this.user, this.password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from dvd");
            while (rs.next()) {
                Dvd temp = (Dvd) LibraryFactory.createItem("dvd", rs.getInt(1), rs.getString(2), rs.getString(3));
                dvdList.add(temp);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return dvdList;
    }

    public List<Magazine> getMagazine() {
        List<Magazine> magazineList = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(this.url, this.user, this.password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Magazine");
            while (rs.next()) {
                Magazine temp = (Magazine) LibraryFactory.createItem("magazine", rs.getInt(1), rs.getString(2), rs.getString(3));
                magazineList.add(temp);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return magazineList;
    }

    public static DBAPI getInstance() {
        return instance;
    }

    public List<String> getBookAuthors(int id) {
        List<String> list = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(this.url, this.user, this.password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from BooksAuthor where id =" + id);
            while (rs.next()) {
                list.add(rs.getString(2));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<DeskClerk> getDeskClerk() {
        List<DeskClerk> deskClerkList = new ArrayList<>();

        try {
            Connection con = DriverManager.getConnection(this.url, this.user, this.password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from DeskClerk");
            while (rs.next()) {
                DeskClerk temp = (DeskClerk) LibraryFactory.createPerson("desk clerk", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDouble(6), rs.getInt(7));
                deskClerkList.add(temp);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return deskClerkList;
    }

    public List<Librarian> getLibrarian() {
        List<Librarian> librarianList = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(this.url, this.user, this.password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Librarian");
            while (rs.next()) {
                Librarian temp = (Librarian) LibraryFactory.createPerson("Librarian", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDouble(6), rs.getInt(7));
                librarianList.add(temp);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return librarianList;
    }

    public List<Borrower> getBorrower() {
        List<Borrower> librarianList = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(this.url, this.user, this.password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Borrower");
            while (rs.next()) {
                Borrower temp = (Borrower) LibraryFactory.createPerson("Borrower", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), 0, 0);
                librarianList.add(temp);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return librarianList;
    }

    public List<Loan> getLoan() {
        List<Loan> loanList = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(this.url, this.user, this.password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Loan");
            while (rs.next()) {
                Loan temp = new Loan(rs.getInt(1), rs.getDate(2), rs.getDate(3), rs.getBoolean(4));
                loanList.add(temp);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return loanList;
    }

    public List<LoanDb> getLoanDB() {

        List<LoanDb> loanDbList = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(this.url, this.user, this.password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Loan");
            while (rs.next()) {
                LoanDb temp = new LoanDb(rs.getInt(1), rs.getDate(2), rs.getDate(3), rs.getBoolean(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8));
                loanDbList.add(temp);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return loanDbList;
    }

    void storeLoan(Loan loan) {
        try {
            Connection con = DriverManager.getConnection(this.url, this.user, this.password);
            Statement stmt = con.createStatement();
            String sql =
                    "insert into Loan(loanID, issueDate, returnDate, finePaid, issuerID, receiverID, borrowerID, bookid) \n" +
                            "values (" + loan.get_loanID() + ",'" + loan.get_issueDate() + "','" + loan.get_returnDate() + "'," + loan.is_finePaid() + "," + loan.get_issuer().get_id() + "," +
                            "0" + "," + loan.get_borrower().get_id() + "," + loan.get_book().get_ID() + ")";
            System.out.println("Query");
            System.out.println(sql);
            stmt.executeUpdate(sql);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }


    }

    void storeReserve(Reserve reserve) {
        try {
            Connection con = DriverManager.getConnection(this.url, this.user, this.password);
            Statement stmt = con.createStatement();
            String sql =
                    "insert into Reserve (RESERVEID, RESERVEDATE, BORROWERID, BOOKID)\n " +
                            "values (" + reserve.get_id() + "," + reserve.get_reserveDate() + "'," + reserve.get_borrower().get_id() + "," + reserve.get_book().get_ID() + ")";
            System.out.println("Query");
            System.out.println(sql);
            stmt.executeUpdate(sql);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

class LoanDb {
    int _loanID;
    Date _issueDate;
    Date _returnDate;
    boolean _finePaid;
    int _issuer;
    int _receiver;
    int _borrower;
    int _book;

    LoanDb(int loanID, Date issueDate, Date returnDate, boolean finePaid, int issuer, int receiver, int borrower, int book) {
        this._loanID = loanID;
        this._issueDate = issueDate;
        this._returnDate = returnDate;
        this._finePaid = finePaid;
        this._issuer = issuer;
        this._receiver = receiver;
        this._borrower = borrower;
        this._book = book;
    }

    @Override
    public String toString() {
        return "LoanDb{" +
                "_loanID=" + _loanID +
                ", _issueDate=" + _issueDate +
                ", _returnDate=" + _returnDate +
                ", _finePaid=" + _finePaid +
                ", _issuer=" + _issuer +
                ", _receiver=" + _receiver +
                ", _borrower=" + _borrower +
                ", _book=" + _book +
                '}';
    }
}


class ReserveDB {
    int reserveID;
    Date reservedate;
    int borrowerid;
    int BookId;


    @Override
    public String toString() {
        return "ReserveDB{" +
                "reserveID=" + reserveID +
                ", reservedate=" + reservedate +
                ", borrowerid=" + borrowerid +
                ", BookId=" + BookId +
                '}';
    }
}



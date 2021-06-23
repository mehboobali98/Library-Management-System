package LMS;

import java.util.Scanner;

public class Librarian extends Staff {
    private int _officeNumber;

    Librarian(int id, String name, String password, String phoneNumber, String address, double salary, int officeNumber) {
        super(id, name, password, phoneNumber, address, salary);
        this._officeNumber = officeNumber;
    }

    public int get_officeNumber() {
        return _officeNumber;
    }

    public void set_officeNumber(int _officeNumber) {
        this._officeNumber = _officeNumber;
    }

    @Override
    public String toString() {
        return "Librarian{" +
                "\nId            : " + _id +
                "\nName          : " + _name +
                "\nPassword      : " + _password +
                "\nPhoneNumber   : " + _phoneNumber +
                "\nAddress       : " + _address +
                "\nOfficeNumber  : " + _officeNumber +
                "\n}";
    }

    public void EditLibrarian() {
        Scanner scanner = new Scanner(System.in);
        int editOption = -1;

        String editMenu = "\n1 to edit id" +
                "\n2 to edit name" +
                "\n3 to edit password" +
                "\n4 to edit phoneNumber" +
                "\n5 to edit address" +
                "\n6 to edit salary" +
                "\n7 to edit office number" +
                "\n0 to exit";
        while (editOption != 0) {
            Color.Print(Color.ANSI_GREEN, editMenu);
            editOption = Integer.parseInt(scanner.nextLine());
            if (editOption == 1) {
                int id;
                System.out.println("Previous id = " + this.getId());
                System.out.println("Enter New Librarian id ");
                id = Integer.parseInt(scanner.nextLine());
                this.set_id(id);
            } else if (editOption == 2) {
                String name;
                System.out.println("Previous name = " + this.get_name());
                System.out.println("Enter New Librarian Name ");
                name = scanner.nextLine();
                this.set_name(name);
            } else if (editOption == 3) {
                String password;
                System.out.println("Previous Password = " + this.get_password());
                System.out.println("Enter New Librarian Password ");
                password = scanner.nextLine();
                this.set_password(password);
            } else if (editOption == 4) {
                String phoneNumber;
                System.out.println("Previous Phone Number = " + this.get_phoneNumber());
                System.out.println("Enter New Librarian PhoneNumber ");
                phoneNumber = scanner.nextLine();
                this.set_phoneNumber(phoneNumber);
            } else if (editOption == 5) {
                String address;
                System.out.println("Previous address = " + this.get_address());
                System.out.println("Enter New Librarian address ");
                address = scanner.nextLine();
                this.set_address(address);
            } else if (editOption == 6) {
                double salary;
                System.out.println("Previous Salary = " + this.get_salary());
                System.out.println("Enter New Librarian Salary ");
                salary = Double.parseDouble(scanner.nextLine());
                this.set_salary(salary);
            } else if (editOption == 7) {
                int officeNumber;
                System.out.println("Previous Office Number = " + this.get_officeNumber());
                System.out.println("Enter New Librarian Office Number");
                officeNumber = Integer.parseInt(scanner.nextLine());
                this.set_officeNumber(officeNumber);
            } else {
                Color.Print(Color.ANSI_RED, "Option dose not exists");
            }
        }
    }
}


package LMS;

public class LibraryFactory {

    /**
     * @param type type of Person to be created
     * @param id id of Person
     * @param name name of Person
     * @param password password of Person
     * @param phone phone of Person
     * @param address address of Person
     * @param deskOrOfficeNumber desk or office number (0 in case of borrower)
     * @param salary salary of staff member (0 in case of borrower)
     * @return returns the desired person
     */
    public static Person createPerson(String type,int id, String name, String password, String phone, String address, double salary, int deskOrOfficeNumber){
        if(type.equalsIgnoreCase("borrower")){
            return new Borrower(id,name,password,phone,address);
        } else if(type.equalsIgnoreCase("librarian")){
            return new Librarian(id,name,password,phone,address,salary,deskOrOfficeNumber);
        } else if(type.equalsIgnoreCase("desk clerk")) {
            return new DeskClerk(id, name, password, phone, address, salary, deskOrOfficeNumber);
        } else {
            return null;
        }
    }

    //TODO Check if LibraryFactory needs to be implemented in Mehboob's code
    /**
     * @param type type of Item to be created
     * @param id id of Item
     * @param name name of Item
     * @param subjectOrComposerOrCompany subject name of book /
     *                                   composer name of DVD /
     *                                   company name of Magazine
     * @return returns the desired Item
     */
    public static Item createItem(String type, int id, String name, String subjectOrComposerOrCompany){
        if(type.equalsIgnoreCase("book")){
            return new Book(id,name,false,subjectOrComposerOrCompany,false);
        } else if(type.equalsIgnoreCase("magazine")){
            return new Magazine(id,name,subjectOrComposerOrCompany);
        } else if(type.equalsIgnoreCase("dvd")){
            return new Dvd(id,name,subjectOrComposerOrCompany);
        } else {
            return null;
        }

    }
}

package Model;

public class Staff {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public Staff(String name, String contact, String add) {
        this.name = name;
        this.contact = contact;
        this.add = add;
    }

String name;
String contact;
String add; 

}

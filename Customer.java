package phaseTwo;

public class Customer {
    private int customerId;
    private String name;
    private String phone;
    private String address;

    public Customer(int customerId,String name,String phone,String address) {
        setCustomerId(customerId);
        setName(name);
        setPhone(phone);
        setAddress(address);
    }

    private void setCustomerId(int customerId) {
        if(customerId<=0)
            throw new IllegalArgumentException("Invalid customer ID");

        this.customerId=customerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name==null||name.trim().isEmpty())
            throw new IllegalArgumentException("Invalid customer name");

        this.name=name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if(phone==null||phone.trim().isEmpty())
            throw new IllegalArgumentException("Invalid customer phone");

        this.phone=phone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if(address==null||address.trim().isEmpty())
            throw new IllegalArgumentException("Invalid customer address");

        this.address=address.trim();
    }

    @Override
    public String toString() {
        return customerId+","+name+","+phone+","+address;
    }
}
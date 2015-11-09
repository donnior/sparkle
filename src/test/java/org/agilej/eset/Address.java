package org.agilej.eset;

public class Address {

    @Accessible
    private String state;
    
    @Accessible
    private String city;
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getCity() {
        return city;
    }
    
    public String getState() {
        return state;
    }
    
    
}

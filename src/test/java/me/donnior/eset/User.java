package me.donnior.eset;

import java.util.List;

public class User {
    
    private int id;
    
    @Accessible
    Gender gender;
    
    @Accessible
    private String name;
    
    @Accessible(name="nick")
    private String nickName;
    
    @Accessible
    private int age;
    
    @Accessible
    private short height;
    
    @Accessible
    private float weight;
    
    private boolean isAdmin;
    
    @Accessible
    private boolean isMale;
    
    @Accessible
    private List<String> mails;
    
    private String desc;

    private User friend;
    
    @Accessible
    private List<Address> address;
    
    @Accessible
    private Address[] studiedPlaces;
    
    public void setAddress(List<Address> address) {
        this.address = address;
    }
    
    public List<Address> getAddress() {
        return address;
    }
    
    public void setMails(List<String> mails) {
        this.mails = mails;
    }
    
    public User getFriend() {
        return friend;
    }
    
    public void setFriend(User friend) {
        this.friend = friend;
    }
    
    public List<String> getMails() {
        return mails;
    }
    
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isAdmin() {
        return isAdmin;
    }

    public String getNickName() {
        return nickName;
    }
    
    public boolean isMale() {
        return isMale;
    }
    
    public short getHeight() {
        return height;
    }
    
    public float getWeight() {
        return weight;
    }
    
    public Address[] getStudiedPlaces() {
        return studiedPlaces;
    }
    
    public Gender getGender() {
        return gender;
    }
}

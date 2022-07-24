package com.stackroute.rabbitmq;




public class UserDTO {
    private String emailId;
    private String password;

    public UserDTO() {
    }

    public UserDTO( String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

package com.app.iris.model;

public class LoginModel {
    String name;
    String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String password;

    int drawableId;

    public boolean equals(Object obj){

        if(!(obj instanceof LoginModel)){
            return false; //objects cant be equal
        }

        LoginModel employeeDataSubModel = (LoginModel) obj;

        return this.email.equals(employeeDataSubModel.email);

    }

    // The below method is not required to be overridden if you are not using HashSet, HashMap or Hashtable,
// but for consistency and extensibility let us do this
    public int hashCode(){
        return this.email.hashCode();
    }
}

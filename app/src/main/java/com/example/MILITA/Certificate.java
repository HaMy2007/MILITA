package com.example.MILITA;

import java.util.Date;

public class Certificate {
    private String name;
    private String session;
    private String organization;
    private String school;
    private String des;
    private String dateCreate;


    public Certificate(String name, String session, String organization, String school, String des, String dateCreate) {
        this.name = name;
        this.school = school;
        this.session = session;
        this.organization = organization;
        this.des = des;
        this.dateCreate = dateCreate;
    }

    public String getName() { return name; }
    public String getSchool() { return school; }
    public String getOrganization() { return organization; }
    public String getSession() { return session; }
    public String getDes() { return des; }
    public String getDateCreate() { return dateCreate; }
}

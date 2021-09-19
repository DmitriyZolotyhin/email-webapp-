package com.parser.letters.models.classes4Hibernate;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "contacts")
public class Email2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email")
    private String address;

    @Column(name = "url")
    private String url;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EmailStatus status;

    @Column(name = "date")
    private Date date;

    public Email2(String address, String url) {
        this.address = address;
        this.url = url;
        this.date = new Date();
        this.status = EmailStatus.New;

    }

    public Email2() {
    }



    public Email2(String url) {
         this.url = url;
     }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public EmailStatus getStatus() {
        return status;
    }

    public void setStatus(EmailStatus status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

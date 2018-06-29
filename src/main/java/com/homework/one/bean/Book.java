package com.homework.one.bean;

import javax.validation.constraints.NotBlank;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_book")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  //产生自增表
    @Column(name = "bookId")
    private Integer id;

    @NotBlank(message = "ISBN非空")
    private String bookISBN;

    private double bookPrice;

    private String bookName;

    private String publisher;

    private String bookSummary;

    private String label;

    private String bookPhoto;

    private String translator;

    private int replicateQuantity;

    private int stockQuantity;

    @ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "t_user_book",
            joinColumns = {@JoinColumn(name = "bookId", referencedColumnName = "bookId")},
            inverseJoinColumns = {@JoinColumn(name = "useId",  referencedColumnName ="user_id")})
    private User user;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getBookSummary() {
        return bookSummary;
    }

    public void setBookSummary(String bookSummary) {
        this.bookSummary = bookSummary;
    }

    public String getBookPhoto() {
        return bookPhoto;
    }

    public void setBookPhoto(String bookPhoto) {
        this.bookPhoto = bookPhoto;
    }

    public int getReplicateQuantity() {
        return replicateQuantity;
    }

    public void setReplicateQuantity(int replicateQuantity) {
        this.replicateQuantity = replicateQuantity;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUser(){
        this.user = null;
    }

    public Book(@NotBlank(message = "ISBN非空") String bookISBN, String bookName, String publisher, String bookSummary) {
        this.bookISBN = bookISBN;
        this.bookName = bookName;
        this.publisher = publisher;
        this.bookSummary = bookSummary;
    }

    public Book(@NotBlank(message = "ISBN非空") String bookISBN, String bookName, String publisher, String label, String bookSummary) {
        this.bookISBN = bookISBN;
        this.bookName = bookName;
        this.publisher = publisher;
        this.bookSummary = bookSummary;
        this.label = label;
    }




    public Book(){
        super();
    }

    @Override
    public String toString() {

        return "Book{" +
                "id=" + id +
                ", bookISBN='" + bookISBN + '\'' +
                ", bookPrice=" + bookPrice +
                ", bookName='" + bookName + '\'' +
                ", publisher='" + publisher + '\'' +
                ", translator='" + translator + '\'' +
                ", bookSummary='" + bookSummary + '\'' +
                ", bookPhoto='" + bookPhoto + '\'' +
                ", replicateQuantity=" + replicateQuantity +
                ", stockQuantity=" + stockQuantity +
                ", user=" + user.getStuName() +
                '}';
    }
}

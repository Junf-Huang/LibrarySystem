package com.homework.one.bean;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_evaluate")
public class Evaluator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  //产生自增表
    @Column(name = "id")
    private Integer id;

    private String bookId;

    private String content;

    private String stuId;

    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Evaluator{" +
                "id=" + id +
                ", bookId='" + bookId + '\'' +
                ", content='" + content + '\'' +
                ", stuId='" + stuId + '\'' +
                ", date=" + date +
                '}';
    }
}

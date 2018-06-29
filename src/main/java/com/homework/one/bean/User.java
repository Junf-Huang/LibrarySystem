package com.homework.one.bean;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//用户类
@Entity
@Table(name = "t_user")
public class User implements Serializable {     //Hibernate 有二级缓存， 缓存会将对象写进硬盘。就必须序列化。以及兼容对象在网络钟的传输。
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  //产生自增表
    @Column(name = "user_id")
    private Integer uid;

    @Column(unique=true)
    @NotBlank(message = "学号不能空")
    //@Pattern(regexp = "^([1-9][4-9]|[2-9][0-9])+([a-zA-Z0-9]*[0-9]{4}$|[0-9]{2}$|[0-9]{3}$)",message = "学号格式错误")
    private String stuID;

    //@NotBlank(message = "学生名字不为空")
    private String stuName;

    @NotBlank(message = "密码不能空")
    private String password;

    private String stuSex;            //1为男，0为女

    private String stuBirth;

    //@NotBlank(message = "专业不能为空")
    private String stuPro;

    //@Pattern(regexp = "^[0-7]([0-9]?)|80", message = "总学分过大或过小")
    private double stuCredit;

    //I should divide users into student and admin, many to one.Here just for convenience.
    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    //if you want to make a relation between Hibernate objects, just use the primary key as the link column.
    //Otherwise, make the linked object Serializable and that'll work as well.
    @JoinTable(name = "t_user_role",
            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "stuId") },
            inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "roleId") })
    //@OrderBy(" ASC")
    private Set<Role> roles;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER ,mappedBy = "user") //the same as book's userName
    private List<Book> books;

    public Integer getUid() {
        return uid;
    }

    public String getStuID() {
        return stuID;
    }

    public void setStuID(String stuID) {
        this.stuID = stuID;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuSex() {
        return stuSex;
    }

    public void setStuSex(String stuSex) {
        this.stuSex = stuSex;
    }

    public String getStuBirth() {
        return stuBirth;
    }

    public void setStuBirth(String stuBirth) {
        this.stuBirth = stuBirth;
    }

    public String getStuPro() {
        return stuPro;
    }

    public void setStuPro(String stuPro) {
        this.stuPro = stuPro;
    }

    public double getStuCredit() {
        return stuCredit;
    }

    public void setStuCredit(double stuCredit) {
        this.stuCredit = stuCredit;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Book> getBooks() {
        /*String bookList = "";
        for(Book book:books)
            bookList += book.getBookName()+" ";
        return bookList;*/
        return this.books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public User() {
    }

    public User(@NotBlank(message = "学号不能空") String stuID,
                @NotBlank(message = "密码不能空") String password,
                String stuName) {
        this.stuID = stuID;
        this.password = password;
        this.stuName = stuName;
    }

    public User(User user) {
        this.stuID = user.getStuID();
        this.stuName = user.getStuName();
        this.password = user.getPassword();
        this.stuSex = user.getStuSex();
        this.stuBirth = user.getStuBirth();
        this.stuPro = user.getStuPro();
        this.stuCredit = user.stuCredit;
        this.roles = user.getRoles();
    }

    @Override
    public String toString() {

        return "User{" +
                "uid=" + uid +
                ", stuID='" + stuID + '\'' +
                ", stuName='" + stuName + '\'' +
                ", password='" + password + '\'' +
                ", stuSex='" + stuSex + '\'' +
                ", stuBirth='" + stuBirth + '\'' +
                ", stuPro='" + stuPro + '\'' +
                ", stuCredit=" + stuCredit +
                ", roles=" + roles +
                '}';
    }
}

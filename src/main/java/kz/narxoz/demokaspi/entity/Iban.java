package kz.narxoz.demokaspi.entity;

import javax.persistence.*;

@Entity
@Table(name = "iban")
public class Iban {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_phoneNumber")
    private String user_phoneNumber;

    @Column(name = "sum")
    private int sum;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUser_phoneNumber() {
        return user_phoneNumber;
    }
    public void setUser_phoneNumber(String user_phoneNumber) {
        this.user_phoneNumber = user_phoneNumber;
    }

    public int getSum() {
        return sum;
    }
    public void setSum(int sum) {
        this.sum = sum;
    }
}

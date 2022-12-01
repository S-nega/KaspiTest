package kz.narxoz.demokaspi.entity;

import javax.persistence.*;

@Entity
@Table(name = "iban")
public class Iban {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @Column(name = "user_phoneNumber")
    public String user_phoneNumber;

    @Column(name = "sum")
    public int sum;

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

package kz.narxoz.demokaspi.entity;

import javax.persistence.*;

@Entity
@Table(name = "operation")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "iban_sender")
    private int iban_sender;

    @Column(name = "iban_getter")
    private int iban_getter;

    @Column(name = "operation_type")//plus or minus
    private String operation_type;

    @Column(name = "sum")
    private int sum;

    @Column(name = "message")
    private String message;
//
//    @Column(name = "date")
//    private String date;//22/01/24/13/05


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getIban_sender(){
        return iban_sender;
    }
    public void setIban_sender(int iban_sender) {
        this.iban_sender = iban_sender;
    }

    public int getIban_getter() {
        return iban_getter;
    }
    public void setIban_getter(int iban_getter) {
        this.iban_getter = iban_getter;
    }

    public String getOperation_type() {
        return operation_type;
    }
    public void setOperation_type(String operation_type) {
        this.operation_type = operation_type;
    }

    public int getSum() {
        return sum;
    }
    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
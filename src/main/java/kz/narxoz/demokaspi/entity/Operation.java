package kz.narxoz.demokaspi.entity;

import javax.persistence.*;

@Entity
@Table(name = "operation")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "iban_id")
    private int iban_id;

    @Column(name = "operation_type")//plus or minus
    private String operation_type;

    @Column(name = "sum")
    private int sum;

//    @Column(name = "message")
//    private String message;
//
//    @Column(name = "date")
//    private String date;//22/01/24/13/05


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getIban_id() {
        return iban_id;
    }
    public void setIban_id(int iban_id) {
        this.iban_id = iban_id;
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
}

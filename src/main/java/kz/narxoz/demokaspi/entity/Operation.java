package kz.narxoz.demokaspi.entity;

import javax.persistence.*;

@Entity
@Table(name = "operation")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ibanSender")
    private int ibanSender;

    @Column(name = "ibanGetter")
    private int ibanGetter;

    @Column(name = "operation_type")
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

    public int getIbanSender(){
        return ibanSender;
    }
    public void setIbanSender(int ibanSender) {
        this.ibanSender = ibanSender;
    }

    public int getIbanGetter() {
        return ibanGetter;
    }
    public void setIbanGetter(int ibanGetter) {
        this.ibanGetter = ibanGetter;
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

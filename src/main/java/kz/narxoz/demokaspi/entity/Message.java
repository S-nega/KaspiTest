package kz.narxoz.demokaspi.entity;
import javax.persistence.*;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ibanGetter")
    private int ibanGetter;

    @Column(name = "iban_sender")
    private int iban_sender;

    @Column(name = "sum")
    private int sum;

    @Column(name = "message")
    private String message;

    @Column(name = "visible")
    private Boolean visible;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getIbanGetter() {
        return ibanGetter;
    }
    public void setIbanGetter(int ibanGetter) {
        this.ibanGetter = ibanGetter;
    }

    public int getIban_sender() {
        return iban_sender;
    }
    public void setIban_sender(int iban_sender) {
        this.iban_sender = iban_sender;
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

    public Boolean getVisible() {
        return visible;
    }
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}

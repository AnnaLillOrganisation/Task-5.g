package hiber.model;

import javax.persistence.*;
@Entity
@Table(name = "cars")

public class Car {
    @Column
    private String model;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column
    private int series;
    @OneToOne(mappedBy = "car")
    private User user;
    public Car(){
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public int getSeries() {
        return series;
    }
    public void setSeries(int series) {
        this.series = series;
    }
    public Car(String model, int series){
        this.model = model;
        this.series = series;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", id=" + id +
                ", series=" + series +
                '}';
    }
}

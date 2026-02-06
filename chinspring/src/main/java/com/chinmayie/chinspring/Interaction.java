package com.chinmayie.chinspring;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Interaction {

    @Id
    @SequenceGenerator(
            name = "interaction_id_sequence",
            sequenceName = "interaction_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "interaction_id_sequence"
    )
    private Long id;

    private String note;
    private String type; // CALL, EMAIL, MEETING
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    public Interaction(String note, String type, LocalDateTime dateTime, Customer customer) {
        this.note = note;
        this.type = type;
        this.dateTime = dateTime;
        this.customer = customer;
    }

    public Interaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interaction that = (Interaction) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(note, that.note) &&
                Objects.equals(type, that.type) &&
                Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, note, type, dateTime);
    }

    @Override
    public String toString() {
        return "Interaction{" +
                "id=" + id +
                ", note='" + note + '\'' +
                ", type='" + type + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}

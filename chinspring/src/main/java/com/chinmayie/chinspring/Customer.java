package com.chinmayie.chinspring;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Customer {

    @Id
    @SequenceGenerator(
            name = "cis",
            sequenceName = "cis",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cis"
    )
    private Long id;

    private String name;
    private Integer age;
    private String email;
    private String phone;
    
    // CRM Fields
    private String status;   // LEAD, ACTIVE, INACTIVE
    private String priority; // LOW, MEDIUM, HIGH

    // Advanced CRM Fields
    private LocalDateTime nextFollowUp;
    
    private String ownerEmail; // For multi-user support
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Interaction> interactions = new ArrayList<>();

    public Customer(String name, Integer age, String email, String phone, String status, String priority, LocalDateTime nextFollowUp, String ownerEmail) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.priority = priority;
        this.nextFollowUp = nextFollowUp;
        this.ownerEmail = ownerEmail;
    }

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDateTime getNextFollowUp() {
        return nextFollowUp;
    }

    public void setNextFollowUp(LocalDateTime nextFollowUp) {
        this.nextFollowUp = nextFollowUp;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public List<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<Interaction> interactions) {
        this.interactions = interactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) &&
                Objects.equals(name, customer.name) &&
                Objects.equals(age, customer.age) &&
                Objects.equals(email, customer.email) &&
                Objects.equals(phone, customer.phone) &&
                Objects.equals(status, customer.status) &&
                Objects.equals(priority, customer.priority) &&
                Objects.equals(nextFollowUp, customer.nextFollowUp) &&
                Objects.equals(ownerEmail, customer.ownerEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, email, phone, status, priority, nextFollowUp, ownerEmail);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", nextFollowUp=" + nextFollowUp +
                '}';
    }
}

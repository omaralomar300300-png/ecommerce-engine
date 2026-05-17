package com.example.ecommerceengine.wallet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;

@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private double balance;

    // نقطة تزامن:
    // هذا الحقل يحمي رصيد المحفظة من التعديل المتزامن.
    // إذا حاول أكثر من طلب خصم رصيد من نفس المحفظة بنفس الوقت،
    // يتم كشف التعارض عن طريق رقم النسخة.
    @Version
    private Long version;

    public Wallet() {
    }

    public Wallet(String userName, double balance) {
        this.userName = userName;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public double getBalance() {
        return balance;
    }

    public Long getVersion() {
        return version;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
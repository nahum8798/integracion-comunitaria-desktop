package model;

import java.util.Date;

public class Customer {
    private int idCustomer;
    private String phone;
    private String adress;
    private Integer idGenderType;
    private Integer idUserCreate;
    private Integer idUserUpdate;
    private Date dateCreate;
    private Date dateUpdate;
    private Integer idUser;

    

    public Customer(int idCustomer, String phone, String adress, Integer idGenderType, Integer idUserCreate,
            Integer idUserUpdate, Date dateCreate, Date dateUpdate, Integer idUser) {
        this.idCustomer = idCustomer;
        this.phone = phone;
        this.adress = adress;
        this.idGenderType = idGenderType;
        this.idUserCreate = idUserCreate;
        this.idUserUpdate = idUserUpdate;
        this.dateCreate = dateCreate;
        this.dateUpdate = dateUpdate;
        this.idUser = idUser;
    }

    

    public Customer(int i) {
    }



    // Getters y Setters
    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Integer getIdGenderType() {
        return idGenderType;
    }

    public void setIdGenderType(Integer idGenderType) {
        this.idGenderType = idGenderType;
    }

    public Integer getIdUserCreate() {
        return idUserCreate;
    }

    public void setIdUserCreate(Integer idUserCreate) {
        this.idUserCreate = idUserCreate;
    }

    public Integer getIdUserUpdate() {
        return idUserUpdate;
    }

    public void setIdUserUpdate(Integer idUserUpdate) {
        this.idUserUpdate = idUserUpdate;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
}

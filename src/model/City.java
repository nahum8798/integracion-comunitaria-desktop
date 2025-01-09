package model;

import java.sql.Timestamp;

public class City {

    private int idCity;
    private String name;
    private int idDepartment;
    private String postalCode;
    private Integer idUserCreate;
    private Integer idUserUpdate;
    private Timestamp dateCreate;
    private Timestamp dateUpdate;

    // Constructor vacío
    public City() {
    }

    // Constructor con parámetros
    public City(int idCity, String name, int idDepartment, String postalCode, Integer idUserCreate, Integer idUserUpdate, Timestamp dateCreate, Timestamp dateUpdate) {
        this.idCity = idCity;
        this.name = name;
        this.idDepartment = idDepartment;
        this.postalCode = postalCode;
        this.idUserCreate = idUserCreate;
        this.idUserUpdate = idUserUpdate;
        this.dateCreate = dateCreate;
        this.dateUpdate = dateUpdate;
    }

    // Getters y Setters

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdDepartment() {
        return idDepartment;
    }

    public void setIdDepartment(int idDepartment) {
        this.idDepartment = idDepartment;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

}

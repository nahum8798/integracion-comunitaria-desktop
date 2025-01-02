package model;

public class Provider {
    private int idProvider;
    private String name;
    private Integer idTypeProvider;
    private Integer idCategory;
    private Integer idGradeProvider;
    private Integer idUserCreate;
    private Integer idUserUpdate;
    private Integer idAdress;
    private int idUser;
    private Integer idProfession;
    private Long idTypeJornal;


    

    public Provider(int idProvider, String name, Integer idTypeProvider, Integer idCategory, Integer idGradeProvider,
            Integer idUserCreate, Integer idUserUpdate, Integer idAdress, int idUser, Integer idProfession,
            Long idTypeJornal) {
        this.idProvider = idProvider;
        this.name = name;
        this.idTypeProvider = idTypeProvider;
        this.idCategory = idCategory;
        this.idGradeProvider = idGradeProvider;
        this.idUserCreate = idUserCreate;
        this.idUserUpdate = idUserUpdate;
        this.idAdress = idAdress;
        this.idUser = idUser;
        this.idProfession = idProfession;
        this.idTypeJornal = idTypeJornal;
    }

    

    public Provider(int i) {
    }



    // Getters y Setters
    public int getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(int idProvider) {
        this.idProvider = idProvider;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdTypeProvider() {
        return idTypeProvider;
    }

    public void setIdTypeProvider(Integer idTypeProvider) {
        this.idTypeProvider = idTypeProvider;
    }

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }

    public Integer getIdGradeProvider() {
        return idGradeProvider;
    }

    public void setIdGradeProvider(Integer idGradeProvider) {
        this.idGradeProvider = idGradeProvider;
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

    public Integer getIdAdress() {
        return idAdress;
    }

    public void setIdAdress(Integer idAdress) {
        this.idAdress = idAdress;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Integer getIdProfession() {
        return idProfession;
    }

    public void setIdProfession(Integer idProfession) {
        this.idProfession = idProfession;
    }

    public Long getIdTypeJornal() {
        return idTypeJornal;
    }

    public void setIdTypeJornal(Long idTypeJornal) {
        this.idTypeJornal = idTypeJornal;
    }
}

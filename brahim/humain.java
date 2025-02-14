public class humain
{
    private String nom;
    private String prenom;
    private int age;
    private String sexe;
    private String adresse;
    private String telephone;

    public humain(){
        System.out.println("constructeur sans parametres");
    }

    public humain(String nom, String prenom, int age, String sexe, String adresse, String telephone)
    {
        System.out.println("constructeur avec parametres");
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.sexe = sexe;
        this.adresse = adresse;
        this.telephone = telephone;
        System.out.println("parameters : "+ "nom: " + nom + " " + prenom + " " + age + " " + sexe + " " + adresse + " " + telephone);
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public String getNom()
    {
        return this.nom;
    }

    public void setPrenom(String prenom)
    {
        this.prenom = prenom;
    }

    public String getPrenom()
    {
        return this.prenom;
    }   

    public void setAge(int age)
    {
        this.age = age;
    }

    public int getAge()
    {
        return this.age;
    }   

    public void setSexe(String sexe)
    {
        this.sexe = sexe;
    }

    public String getSexe()
    {
        return this.sexe;
    }

    public void setAdresse(String adresse)
    {
        this.adresse = adresse;
    }

    public String getAdresse()
    {
        return this.adresse;
    }

    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    public String getTelephone()
    {
        return this.telephone;
    }
}
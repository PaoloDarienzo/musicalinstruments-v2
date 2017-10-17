package model;

import java.awt.Image;
import java.net.InetAddress;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import dao.ProductDAO;

/**
 * The product class represents the product, with all his attributes.
 * ID is unique for every product.
 * @author Paolo D'Arienzo
 * @version 1.5
 *
 */
public class Product {
	
    private final UUID ID;
    private String nome;
    private int numeroPezziDisponibili;
    private String descrizione;
    private final float peso;
    private float prezzo;
    private final TipoStrumento classificazione;
    private final Brand marca;
    private final Date dataInserimento;
    private final InetAddress IPInserimento;
    /**
     * @deprecated Image use not implemented
     */
    private final List<Image> img;
    /**
     * c = classic product, s = Scholastic product, p = professional product
     */
    private String productType;
    private int sconto;
    private final Boolean usato;
    private int numeroPezziMinimo;
    private LivelloStrumento livelloConsigliato;
	
    /**
     * Constructor of Product.
     * @param ID is a unique ID for product
     * @param nome is the name of the product
     * @param numeroPezziDisponibili is the number of available pieces
     * @param descrizione is the description of the product
     * @param peso is the weight of the product
     * @param prezzo is the price of the product
     * @param classificazione is the classification of the product
     * @param marca is the brand of the product
     * @param dataInserimento is the insertion date of the product
     * @param IPInserimento is the IP address of the user who added the product
     * @param productType is the type of product, if normal professional or scholastic
     * @param sconto is the discount rate
     * @param numeroPezziMinimo is the minimum number of products needed for getting the discount
     * @param livelloConsigliato is the suggested level for the instrument
     * @param usato indicate if the product is used or not
     */
    public Product(UUID ID, String nome, int numeroPezziDisponibili, String descrizione,
                        float peso, float prezzo, TipoStrumento classificazione,
                        Brand marca, Date dataInserimento, InetAddress IPInserimento, String productType,
                        int sconto, int numeroPezziMinimo, LivelloStrumento livelloConsigliato,
                        Boolean usato){

        this.ID = ID;
        this.nome = nome;
        this.numeroPezziDisponibili = numeroPezziDisponibili;
        this.descrizione = descrizione;
        this.peso = peso;
        this.prezzo = prezzo;
        this.classificazione = classificazione;
        this.marca= marca;
        this.dataInserimento = dataInserimento;
        this.IPInserimento = IPInserimento; 
        this.img = new ArrayList<Image>();
        this.productType = productType;
        
        if (productType.equalsIgnoreCase("s")) {
            this.sconto = sconto;
            this.numeroPezziMinimo = numeroPezziMinimo;
            this.livelloConsigliato = livelloConsigliato;
            this.usato = false;
        }
        else if(productType.equalsIgnoreCase ("p")) {
            this.sconto = sconto;
            this.usato = usato; 
            this.numeroPezziMinimo = 0;
            this.livelloConsigliato = LivelloStrumento.ND;
        }
        else {
        	assert productType.equalsIgnoreCase("c");
        	this.sconto = 0;
        	this.numeroPezziMinimo = 0;
        	this.usato = false;
        	this.livelloConsigliato = LivelloStrumento.ND;
        }

}
	
    /**
     * Returns the ID of the product
     * @return the ID of the product
     */
    public UUID getID(){
        return this.ID;
    }

    /**
     * Returns the name of the product
     * @return the name of the product
     */
    public String getNome(){
        return this.nome;
    }
    
    /**
     * Set a new name for the product
     * @param nome new name of the product
     */
    public void setNome(String nome){
        this.nome = nome;
    }

    /**
     * Returns the number of available pieces of that product
     * @return the number of available pieces of that product
     */
    public int getNumeroPezziDisponibili(){
        return this.numeroPezziDisponibili;
    }
    
    /**
     *  Set the new number of available pieces of that product
     * @param numeroPezziDisponibili new number of available pieces of that product
     */
    public void setNumeroPezziDisponibili(int numeroPezziDisponibili){
        this.numeroPezziDisponibili = numeroPezziDisponibili;
    }

    /**
     * Returns the description of the product
     * @return the description of the product
     */
    public String getDescrizione(){
        return this.descrizione;
    }
    
    /**
     *  Set a new description for the product
     * @param descrizione the new description of the product
     */
    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }

    /**
     * Returns the weight of the product
     * @return the weight of the product
     */
    public float getPeso(){
        return this.peso;
    }

    /**
     * Returns the price of the product
     * @return the price of the product
     */
    public float getPrezzo(){
        return this.prezzo;
    }
    
    /**
     * Set a new value to the price of the product
     * @param prezzo new price of the product 
    */
    public void setPrezzo(float prezzo){
        this.prezzo = prezzo;
    }
    
    /**
     * Returns the classification of the product
     * @return the classification of the product
     */
    public TipoStrumento getClassificazione(){
    	return this.classificazione;
    }
    
    /**
     * Returns the brand of the product
     * @return the brand of the product
     */
    public Brand getMarca() {
    	return this.marca;
    }

    /**
     * Returns product insertion date
     * @return product insertion date
     */
    public Date getDataInserimento(){
        return this.dataInserimento;
    }

    /**
     * Returns the IP address of who created the product
     * @return the IP address of who created the product
     */
    public InetAddress getIPInserimento(){
        return this.IPInserimento;
    }

    /**
     * Returns the image at the position indicated
     * @param i is the position of the image
     * @return an image of the product
     * @deprecated Not implemented
     */
    public Image getImage(int i){
        return this.img.get(i);
    }
    
    /**
     * Add the image to the list in Product
     * @param image to add
     * @deprecated Not implemented
     */
    public void addImage(Image image){
        this.img.add(image);
    }
	
    /**
     * Removes the image at the position indicated
     * @param i is the position of the image that has to be removed
     * @deprecated Not implemented
     */
    public void removeImage(int i){
        img.remove(i);
    }
    
    /**
     * Returns the string indicating the product type: s for scholastic product, p for professional product, c otherwise
     * @return the string indicating the product type
     */
    public String getProductType() {
    	return this.productType;
    }
    
    /**
     * Sets the string indicating the product type: s for scholastic product, p for professional product, c otherwise
     * @param newType is the type of product; scholastic, professional or normal one
     */
    public void setProductType(String newType) {
    	this.productType = newType;
    }
    
    /**
     * Returns if the product is used or not
     * @return a boolean value that state for used or not
     */
    public Boolean isUsato(){
        return this.usato;
    }
    
    /**
     * Returns the discount rate
     * @return the discount rate
     */
    public int getSconto(){
        return this.sconto;
    }
    
    /**
     * Sets a new value for the discount of the product
     * @param nuovoSconto is the new discount of the product
     */
    public void setSconto(int nuovoSconto){
        this.sconto = nuovoSconto;
    }

    /**
     * Returns the minimum number of pieces needed to the discount to be applied
     * @return the minimum number of pieces needed to the discount to be applied
     */
    public int getNumeroPezziMinimo(){
        return this.numeroPezziMinimo;
    }
    
    /**
     * Sets a new value for the minimum number of pieces of the product to get access to the discount
     * @param nuovoNumeroPezziMinimo is the new value for the minimum number of pieces of the product to get access to the discount
     */
    public void setNumeroPezziMinimo(int nuovoNumeroPezziMinimo){
        this.numeroPezziMinimo = nuovoNumeroPezziMinimo;
    }

    /**
     * Returns the suggested level for the product
     * @return the suggested level for the product
     */
    public LivelloStrumento getLivelloConsigliato(){
        return this.livelloConsigliato;
    }

    /**
     * Sets a new level suggested for the product
     * @param nuovoLivelloConsigliato the new suggested level for the product
     */
    public void setLivelloConsigliato(LivelloStrumento nuovoLivelloConsigliato){
        this.livelloConsigliato = nuovoLivelloConsigliato;
    }
    
    /**
     * Adds the product in the database
     * @see ProductDAO#addInDatabase(Product)
     */
    public void addInDatabase(){
    	ProductDAO.addInDatabase(this);    	
    }
    
    /**
     * Updates the product in the database
     * @see ProductDAO#updateInDatabase(Product)
     */
    public void updateInDatabase(){
    	ProductDAO.updateInDatabase(this);    	
    }
    
    /**
     * Deletes the product in the database
     * @see ProductDAO#deleteFromDatabase(UUID)
     */
    public void deleteFromDatabase(){
    	ProductDAO.deleteFromDatabase(this.ID);    	
    }
	
}//End Product class
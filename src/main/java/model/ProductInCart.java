package model;

import customExceptions.NoItemException;

/**
 * ProductInCart associate the product that is already added in the cart
 * to an <i>int</i> that indicate the number of that product in the cart.
 * @author Paolo D'Arienzo
 * @version 1.5
 */
public class ProductInCart{
    
    private final Product product;
    private int numeroProdotto;
	
    /**
     * Class constructor.
     * @param product Product to be added; by default, the number added is 1
     */
    public ProductInCart(Product product){
        this.product = product;
        this.numeroProdotto = 1;
    }

    /**
     * Class constructor.
     * @param product Product to be added
     * @param num Number of that item in the cart
     */
    public ProductInCart(Product product, int num){
        this.product = product;
        this.numeroProdotto = num;
    }

    /**
     * Returns the product
     * @return Product in the cart
     */
    public Product getProduct(){
        return this.product;
    }

    /**
     * Returns the number of items of that product in the cart
     * @return the number of items of that product in the cart
     */
    public int getNumeroProdotto(){
        return this.numeroProdotto;
    }

    /**
     * Sets a new quantity to the associated product
     * @param newQuantity the new number of the associated product in the cart
     */
    protected void setNumeroProdotto(int newQuantity){
        this.numeroProdotto = newQuantity;
    }

    /**
     * Adds 1 element to the product
     */
    protected void addOneItem(){
        this.numeroProdotto++;
    }

    /**
     * Removes one element from the number of products in the cart
     * @throws NoItemException when there aren't items to decrease
     */
    protected void removeOneItem() throws NoItemException{
        if (this.numeroProdotto == 1){
            throw new NoItemException("Cannot decrease more the quantity of that product.");
        }			
        else
            this.numeroProdotto--;
    }
    
    /**
     * Returns the total price for that kind of product, based on the number of that item;
     * The total price is discounted if the product have a discount percentage and if the number of items reach the application threshold.
     * @return the total price for that kind of product, considering any discounts
     */
    public float getPrezzo(){
    	if(product.getSconto()!= 0 && (numeroProdotto >= product.getNumeroPezziMinimo())){
    		return ((product.getPrezzo() - ((float) (product.getPrezzo() * product.getSconto()) / 100)) * numeroProdotto);
    	}
    	else{
    		return product.getPrezzo() * numeroProdotto;
    	}
    }
    
}
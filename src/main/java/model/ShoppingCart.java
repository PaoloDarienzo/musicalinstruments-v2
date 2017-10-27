package model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import customExceptions.NoItemException;
import dao.ShoppingCartDAO;

/**
 * ShoppingCart class represents the shopping cart, that is associated to an user.
 * @author Paolo D'Arienzo
 * @version 1.5
 *
 */
public class ShoppingCart {

	private final String userMail;
    private final List<ProductInCart> articoliInCarrello;

    /**
     * Constructor of ShoppingCart.
     * It creates a new ProductInCart item and assigns it to the list of ShoppingCart
     * @param mail is the mail of the user, that is the owner of the shopping cart
     */
    public ShoppingCart(String mail){

    	this.userMail = mail;
    	this.articoliInCarrello = new ArrayList<ProductInCart>();

    }
    
    /**
     * Returns the mail of the user that owns the shopping cart
     * @return the mail of the user that owns the shopping cart
     */
    public String getUserMailFromShopCart(){
    	return this.userMail;
    }

    /**
     * Adds a new product to the list of ShoppingCart
     * @param product to be added
     * @see ShoppingCartDAO#addOneItem(String, ProductInCart)
     */
    public void addToCart(Product product){
    	
    	UUID IDProductToAdd = product.getID();
    	Boolean isAlreadyAdded = false;
    	
    	for (ProductInCart prodottoInCarrello : this.articoliInCarrello) {
    		if(prodottoInCarrello.getProduct().getID() == IDProductToAdd) {
    			isAlreadyAdded = true;
    			
    			//While I'm here, I add 1 item
    			prodottoInCarrello.addOneItem();
    			//prodottoInCarrello.getNumeroProdotto() is already updated
    			ShoppingCartDAO.updateInDatabase(this.userMail, IDProductToAdd, prodottoInCarrello.getNumeroProdotto());
    			
    			return;
    		}
    		else
    			isAlreadyAdded = false;    			
    	}
    	
    	if(!isAlreadyAdded) { //The product that is added wasn't already in cart
    		ProductInCart aggiunto = new ProductInCart(product);
            this.articoliInCarrello.add(aggiunto);
            ShoppingCartDAO.addOneItem(this.userMail, aggiunto);
    	}
    	
    }

    /**
     * Adds multiple items of a new product in the cart
     * @param product added in cart
     * @param num number of items of that product
     * @see ShoppingCartDAO#addOneItem(String, ProductInCart)
     */
    public void addToCart(Product product, int num){
    	ProductInCart aggiunto = new ProductInCart(product, num);
        this.articoliInCarrello.add(aggiunto);
        ShoppingCartDAO.addOneItem(this.userMail, aggiunto);
    }

    /**
     * Adds multiple items to a product in the list
     * @param product which number has to be modified
     * @param quantity new number of items of that product
     * @throws NoSuchElementException when the items searched is not present in the list
     * @see ShoppingCartDAO#updateInDatabase(String, UUID, int)
     */
    public void updateCart(Product product, int quantity) throws NoSuchElementException{
    	//Since this method is always invoked on an already existing product,
    	//the exception can exist only in a runtime error.
    	
    	UUID productID = product.getID();
        
    	//convert the list in a stream, then
    	//search based on the filter, then
    	//findFirst returns an Optional describing the first element or returns an empty Optional
        Optional<ProductInCart> result = this.articoliInCarrello.stream()
        		.filter(a -> Objects.equals(a.getProduct().getID(), productID))
                .findFirst();
        
        if (result.isPresent()) {
            result.get().setNumeroProdotto(quantity);
            ShoppingCartDAO.updateInDatabase(this.userMail, productID, quantity);
        }
        else
        	System.out.println("No item found.");

    }

    /**
     * Removes the product from the list
     * @param productToRemove is the product that has to be removed from the list
     * @see ShoppingCartDAO#deleteProductFromDatabase(String, UUID)
     */
    public void removeFromCart(Product productToRemove){
    	//If productToRemove is not in the list, nothing happens.
    	UUID IDToRemove = productToRemove.getID();
        this.articoliInCarrello.removeIf(a -> Objects.equals(a.getProduct().getID(), IDToRemove));
        ShoppingCartDAO.deleteProductFromDatabase(this.userMail, IDToRemove);
    }
    
    /**
     * Removes all the products from the list
     * @see ShoppingCartDAO#cleanShoppingCart(String)
     */
    public void removeAllFromCart(){
    	this.articoliInCarrello.clear();
    	ShoppingCartDAO.cleanShoppingCart(this.userMail);
    }

    /**
     * Adds 1 item to the product in the list
     * @param product to increment
     * @throws NoSuchElementException when the items searched is not present in the list
     * @see ShoppingCartDAO#updateInDatabase(String, UUID, int)
     */
    public void incrementInCart(Product product) throws NoSuchElementException{
    	//Since this method is always invoked on an already existing product,
    	//the exception can exist only in a runtime error.
    	
    	UUID productID = product.getID();
        
    	//convert the list in a stream, then
    	//search based on the filter, then
    	//findFirst returns an Optional describing the first element or returns an empty Optional
        Optional<ProductInCart> result = this.articoliInCarrello.stream()
        		.filter(a -> Objects.equals(a.getProduct().getID(), productID))
                .findFirst();
        
        if (result.isPresent()) {
        	result.get().addOneItem();
            ShoppingCartDAO.updateInDatabase(this.userMail, productID, (result.get().getNumeroProdotto()));
        }
        else
        	System.out.println("No item found.");

    }

    /**
     * Removes 1 item from the product in the list; if there is only 1 item left, removes the product from the cart
     * @param product to decrement
     * @throws NoItemException when there aren't items to decrease
     * @see ShoppingCartDAO#updateInDatabase(String, UUID, int)
     */
    public void decrementInCart(Product product) throws NoItemException{
    	//Since this method is always invoked on an already existing product,
    	//the exception can exist only in a runtime error.
    	
    	UUID productID = product.getID();
        
    	//convert the list in a stream, then
    	//search based on the filter, then
    	//findFirst returns an Optional describing the first element or returns an empty Optional
        Optional<ProductInCart> result = this.articoliInCarrello.stream()
        		.filter(a -> Objects.equals(a.getProduct().getID(), productID))
                .findFirst();
        
        if (result.isPresent()) {
        	
            //If there is only 1 element, removes the product from the cart
            if( result.get().getNumeroProdotto() == 1){
            	removeFromCart(product);
            }
            else {//Otherwise, decrement the number of items of that product in the cart
                result.get().removeOneItem();
                ShoppingCartDAO.updateInDatabase(this.userMail, productID, (result.get().getNumeroProdotto()));
            }
            
        }
        else
        	System.out.println("No item found.");

    }

    /**
     * Returns the list of products in the cart
     * @return the list of products in the cart
     */
    public List<ProductInCart> getArticoliInCarrello(){
        return this.articoliInCarrello;
    }
    
    /**
     * Returns the total number of items in the cart, adding the number of item of every product.
     * @return the total number of items in the cart
     */
    public int getNumberOfItems() {
    	
    	int totalCount = 0;
    	
    	for(ProductInCart prodottoInCarrello : this.articoliInCarrello) {
    		totalCount += prodottoInCarrello.getNumeroProdotto();
    	}

    	return totalCount;
    	
    }
    
    /**
     * Returns the total price of the items in the shopping cart
     * @return the total price of the items in the shopping cart
     */
    public float getTotalPrice(){
    	
    	double prezzoTotale = articoliInCarrello.stream()
    			.mapToDouble(b -> b.getPrezzo())
                .sum();
    	
    	return (float) prezzoTotale;
    	
    }

}
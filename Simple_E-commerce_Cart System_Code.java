import java.util.*;

class Product {
    private String name;
    private double price;
    private boolean available;

    public Product(String name, double price, boolean available) {
        this.name = name;
        this.price = price;
        this.available = available;
    }
    public String toString()
    {
    	return name;
    }
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }
}

class Laptop extends Product {
    public Laptop(String name, double price, boolean available) {
        super(name, price, available);
    }
}

class Headphones extends Product {
    public Headphones(String name, double price, boolean available) {
        super(name, price, available);
    }
}

class Mobile extends Product {
    public Mobile(String name, double price, boolean available) {
        super(name, price, available);
    }
}

// DiscountStrategy interface for different discount strategies
interface DiscountStrategy {
    double applyDiscount(double originalPrice, int quantity);
}

class PercentageOffDiscount implements DiscountStrategy {
    private double percentage;

    public PercentageOffDiscount(double percentage) {
        this.percentage = percentage;
    }

    public double applyDiscount(double originalPrice, int quantity) {
        return originalPrice * (1 - (percentage / 100)) * quantity;
    }
}

class BuyOneGetOneFree implements DiscountStrategy {
    public double applyDiscount(double originalPrice, int quantity) {
        int discountedQuantity = quantity / 2;
        return discountedQuantity * originalPrice;
    }
}

class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity)
    {
    	this.quantity=quantity;
    }
}

class ShoppingCart {
    private List<CartItem> cartItems = new ArrayList<>();
    private DiscountStrategy discountStrategy;

    public ShoppingCart(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public void addItem(Product product, int quantity) {
        CartItem existingItem = findCartItem(product);
        if (existingItem != null) {
            int q = existingItem.getQuantity();
            q = q+quantity;
            existingItem.setQuantity(q);
            System.out.println("Entered item already present in your cart.");
        } else {
            CartItem newItem = new CartItem(product, quantity);
            cartItems.add(newItem);
            System.out.println("Item added successfully.");
        }
    }

    public void updateQuantity(Product product, int quantity) {
        CartItem existingItem = findCartItem(product);
        if (existingItem != null) {
            existingItem.setQuantity(quantity);
            System.out.println("Quantity updated successfully");
        }
        else
        {
        	System.out.println("Item you have entered is not present in the cart");
        }
    }

    public void removeItem(Product product) {
        CartItem existingItem = findCartItem(product);
        if (existingItem != null) {
            cartItems.remove(existingItem);
            System.out.println("Item removed successfully");
        }
        else
        {
        	System.out.println("Item you have entered is not present in the cart");
        }
    }

    public double calculateTotal() {
        double total = 0;
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            int quantity = cartItem.getQuantity();
            total += discountStrategy.applyDiscount(product.getPrice(), quantity);
        }
        return total;
    }
    private CartItem findCartItem(Product product) {
        for (CartItem item : cartItems) {
            if (item.getProduct() == product) {
                return item;
            }
        }
        return null;
    }
    public void displayCart() {
        for (CartItem cartItem : cartItems) {
            System.out.println("You have " + cartItem.getQuantity() + " " + cartItem.getProduct().getName() + " in your cart.");
        }
    }
}

public class ECommerceCartSystem {
    public static void main(String[] args) {
        Product laptop = new Laptop("Laptop", 1000, true);
        Product headphones = new Headphones("Headphones", 50, true);
        Product mobile = new Mobile("mobile", 2000, true);

        ArrayList<Product> productsAvailable = new ArrayList<>();
        productsAvailable.add(laptop);
        productsAvailable.add(headphones);
        productsAvailable.add(mobile);
        
        System.out.println("List of Products with their attributes: ");
        for(Product p:productsAvailable)
        {
        	System.out.println("{name: "+p.getName()+", price: "+p.getPrice()+", available: "+p.isAvailable()+"}");
        }
        
        System.out.println("===========================================================");
        
        ArrayList<String> products = new ArrayList<>();
        products.add(headphones.getName());
        products.add(laptop.getName());
        products.add(mobile.getName());
        
        LinkedHashMap<String,Product> hm = new LinkedHashMap<>();
        hm.put(laptop.getName(), laptop);
        hm.put(headphones.getName(), headphones);
        hm.put(mobile.getName(), mobile);
        
        //You can select the discount strategy
        DiscountStrategy percentageOffDiscount = new PercentageOffDiscount(0); // 0% off
        DiscountStrategy buyOneGetOneFree = new BuyOneGetOneFree();
        ShoppingCart cart = new ShoppingCart(percentageOffDiscount);
        
        boolean b=true;
        while(b)
        {
        	System.out.println("Select an option:\n1: To add item to cart\n"
            		+ "2: To update the quantity of an item\n3: Remove an item from the cart\n"
            		+ "4: View items in the cart\n5: Calculate and display the total bill\n6: Exit");
            System.out.println("===========================================================");
            
            Scanner in=new Scanner(System.in);
            System.out.print("Enter the option:");
            String op = in.nextLine();
            int option = Integer.parseInt(op);
            System.out.println("You had selected option "+op);
            System.out.println("===========================================================");
            
            switch(option)
            {
            case 1:
            	System.out.print("Add to Cart(Case sensitive):");
            	String pro = in.nextLine();
            	if (products.contains(pro))
            	{
            		Product p = hm.get(pro);
            		System.out.println("Enter quantity: ");
            		int qty = in.nextInt();
            		if(qty<=0)
            		{
            			System.out.println("Quantity cannot be <= 0.Please enter positive value:)");
            		}
            		else
            		{
            			cart.addItem(p, qty);
            		}
            		
            	}
            	else
            	{
            		System.out.println("Product you have entered is not available");
            	}
            	System.out.println("===========================================================");
            	break;
            case 2:
            	System.out.print("Enter the name of the product(Case sensitive):");
            	String p=in.nextLine();
            	if (products.contains(p))
            	{
            		Product pr = hm.get(p);
            		System.out.println("Enter the new quantity:");
                	String q = in.nextLine();
                	int new_qty = Integer.parseInt(q);
                	if(new_qty<=0)
            		{
            			System.out.println("Quantity cannot be <= 0.Please enter positive value:)");
            		}
            		else
            		{
            			cart.updateQuantity(pr, new_qty);
            		}
            	}
            	else
            	{
            		System.out.println("Product you have entered is not available");
            	}
            	System.out.println("===========================================================");
            	break;
            case 3:
            	System.out.println("Enter the name of the product that you want to remove(Case sensitive):");
            	String pro1 = in.nextLine();
            	if (products.contains(pro1))
            	{
            		Product pr1 = hm.get(pro1);
            		cart.removeItem(pr1);
            	}
            	else
            	{
            		System.out.println("Product you have entered is not available");
            	}
            	System.out.println("===========================================================");
            	break;
            case 4:
            	System.out.println("Items present in your cart: ");
            	cart.displayCart();
            	System.out.println("===========================================================");
            	break;
            case 5:
            	System.out.println("Total Bill: Your total bill is $" + cart.calculateTotal());
            	System.out.println("===========================================================");
            	break;
            case 6:
            	b=false;
            	System.out.println("Exited");
            	System.out.println("===========================================================");
            	break;
            default:
            	System.out.println("Invalid choice. Please select a valid option.");
            	System.out.println("===========================================================");
            	break;
            }
        }
    }
}

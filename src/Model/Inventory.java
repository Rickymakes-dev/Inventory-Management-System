/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author rickychau
 */
public class Inventory 
{

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    public static int onQueuePart,onQueueProduct ;
    public static int partIdCount, productIdCount;
    
    public static int getAutoGenPartId()
    {
        int nextQueue;
        if(onQueuePart != 0)
        {
            nextQueue = onQueuePart;
        }
        else
        {
            nextQueue = partIdCount;
            getPartIdCount();
        }
        return nextQueue;
    }
    
    public static int getAutoGenProductId()
    {
        int nextQueue;
        if(onQueueProduct != 0)
        {
            nextQueue = onQueueProduct;
        }
        else
        {
            nextQueue = productIdCount;
            getProductIdCount();
        }
        return nextQueue;
    }
    
    //+ addPart(newPart:Part):void
    public static void addPart(Part newPart)
    {
        allParts.add(newPart);
    }
    
    //+ addProduct(newProduct:Product):void
    public static void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }
    
    //+ lookupPart(partId:int):Part
    public static Part lookupPart(int partId)
    {
        Part string = null;
        for(Part part: allParts)
        {
            if(partId == part.getId().getValue())
            {
                string = part;
            }
        }
        return string;
    }
    
    //+ lookupProduct(productId:int):Product
    public static Product lookupProduct(int productId)
    {
        Product string = null;
        for(Product product: allProducts)
        {
            if(productId == product.getId().getValue())
            {
                string = product;
            }
        }
        return string;
    }
    
    //+ lookupPart(partName:String):ObservableList<Part>
    public static Part lookupPart(String partName)
    {
        Part string = null;
        for(Part part: allParts)
        {
            if(partName.equals(part.getName().get()))
            {
                string = part;
            }
        }
        return string;
    }
    
    //+ lookupProduct(productName:String):ObservableList<Product>
    public static Product lookupProduct(String productName)
    {
        Product string = null;
        for(Product product: allProducts)
        {
            if(productName.equals(product.getName().get()))
            {
                string = product;
            }
        }
        return string;
    }
        
    //+ updatePart(index:int, selectedPart:Part):void
    public static void updatePart(int index, Part selectedPart)
    {
        allParts.set(index, selectedPart);
    }
        
    
    //+ updateProduct(index:int, newProduct:Product):void
    public static void updateProduct(int index, Product newProduct)
    {
        allProducts.set(index, newProduct);
    }
    
    
    //+ deletePart(selectedPart:Part):boolean
    public static boolean deletePart(Part selectedPart)
    {
        return allParts.remove(selectedPart);
    }
    
    //+ deleteProduct(selectedProduct:Product):boolean
    public static boolean deleteProduct(Product selectedProduct)
    {
        return allProducts.remove(selectedProduct);
    }
    
    //+ getAllParts():ObservableList<Part>
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }
    
    //+ getAllProducts():ObservableList<Product>
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }
    
    //Auto Gen ID
    public static int getPartIdCount()
    {
        return partIdCount++;
    }
    
    public static int getProductIdCount()
    {
        return productIdCount++;
    }

    public static int getOnQueuePart() {
        return onQueuePart;
    }

    public static void setOnQueuePart(int onQueuePart) {
        Inventory.onQueuePart = onQueuePart;
    }

    public static int getOnQueueProduct() {
        return onQueueProduct;
    }

    public static void setOnQueueProduct(int onQueueProduct) {
        Inventory.onQueueProduct = onQueueProduct;
    }
    
    public static boolean isNumeric(String str)
    {
        try
        {
            Integer.parseInt(str);
            return true;
        }
        catch(NumberFormatException e)
        {
            return false;
        }
    }
    
}

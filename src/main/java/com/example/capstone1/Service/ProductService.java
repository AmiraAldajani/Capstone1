package com.example.capstone1.Service;

import com.example.capstone1.Model.Category;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {

    protected static ArrayList<Product> products = new ArrayList<>();

    public ArrayList<Product> getProducts(){
        return products;
    }

    public String addProduct(Product product){
        for ( Product p : products){
            if (p.getId().equals(product.getId()))
                return "Product ID already exists";
        }
        for ( Category c : CategoryService.categories)
            if (c.getId().equals(product.getCategoryID())){
                products.add(product);
                return "Product added successfully";
        }
        return "Category does not exist";
    }

    public boolean deleteProduct(String id){
        for ( Product p : products)
            if (p.getId().equals(id)) {
                products.remove(p);
                return true;
            }
        return false;
    }

    public boolean updateProduct(Product product){
        for ( int i =0; i<products.size(); i++)
            if (products.get(i).getId().equals(product.getId())){
                products.set(i, product);
                return true;
            }
        return false;
    }

    public String applyDiscount(String adminID, String categoryID, double percentage){
        boolean isAdmin = false, categoryExists = false;
        for( User u : UserService.users)
            if (u.getId().equals(adminID)){
                if (u.getRole().equals("admin")) {
                    isAdmin = true;
                    break;
                }
                return "You don't have admin access";
            }
        for ( Category c : CategoryService.categories)
            if (c.getId().equals(categoryID)) {
                categoryExists = true;
                break;
            }
        if(!categoryExists)
            return "Category does not exist";
        if(isAdmin){
            for (Product p : ProductService.products)
                if( p.getCategoryID().equals(categoryID)){
                    p.setPrice(p.getPrice()-p.getPrice()*percentage);
                }
            return "Discount Applied successfully";
        }
        return "Your ID was not found in the system";
    }


}

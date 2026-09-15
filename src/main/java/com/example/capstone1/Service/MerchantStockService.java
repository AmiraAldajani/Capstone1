package com.example.capstone1.Service;

import com.example.capstone1.Model.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    @Getter
    protected ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    protected ArrayList<String> purchases = new ArrayList<>();

    public String addMerchantStock(MerchantStock merchantStock) {
        boolean merchantExists=false, productExists=false;
        for (MerchantStock m : merchantStocks) {
            if (m.getId().equals(merchantStock.getId()))
                return "case1";
        }
        for (MerchantStock m : merchantStocks) {
            if (m.getProductID().equals(merchantStock.getProductID()) && m.getMerchantID().equals(merchantStock.getMerchantID()))
                return "case2";
        }
        for (Product p: ProductService.products)
            if (p.getId().equals(merchantStock.getProductID())) {
                productExists = true; break;
            }
        for (Merchant m: MerchantService.merchants)
            if(m.getId().equals(merchantStock.getMerchantID())) {
                merchantExists = true; break;
            }
        if (!productExists)
            return "case3";
        if(!merchantExists)
            return "case4";
        merchantStocks.add(merchantStock);
        return "Added successfully";
    }

    public boolean deleteMerchantStock(String id) {
        for (MerchantStock m : merchantStocks)
            if (m.getId().equals(id)) {
                merchantStocks.remove(m);
                return true;
            }
        return false;
    }

    public boolean updateMerchantStock(MerchantStock merchantStock) {
        for (int i = 0; i < merchantStocks.size(); i++)
            if (merchantStocks.get(i).getId().equals(merchantStock.getId())) {
                merchantStocks.set(i, merchantStock);
                return true;
            }
        return false;
    }

    public boolean addStocks(String merchantID, String productID, Integer stockNumber) {
        if (stockNumber < 1)
            return false;
        for (MerchantStock m : merchantStocks)
            if (m.getMerchantID().equals(merchantID) && m.getProductID().equals(productID)) {
                m.setStock(stockNumber + m.getStock());
                return true;
            }
        return false;
    }

    /*
        public String addStocks(String merchantID, String productID, int stockNumber){
            boolean merchantIDMatches=false, productIDMatches= false;
            for ( MerchantStock m: merchantStocks) {
                if (m.getMerchantID().equals(merchantID)){
                    merchantIDMatches=true;
                }
                if (m.getProductID().equals(productID)){
                    productIDMatches= true;
                }
            }
            if (!merchantIDMatches)
                return "Merchant ID doesn't match";
            if (!productIDMatches)
                return
        }
     */
    public String buyProduct(String userID, String merchantID, String productID) {
        User user = null;Product product = null;
        for (User u : UserService.users)
            if (u.getId().equals(userID)){
                user = u;
                break;}
        if (user == null)
            return "case1";

        for (Product p : ProductService.products)
            if (p.getId().equals(productID)) {
                product = p;
                break;
            }
        if (product == null)
            return "case2";

        for (MerchantStock m : merchantStocks)
            if (m.getMerchantID().equals(merchantID) && m.getProductID().equals(productID)) {
                if (m.getStock() > 0 && user.getBalance() >= product.getPrice()) {
                    m.setStock(m.getStock() - 1);
                    user.setBalance(user.getBalance() - product.getPrice());
                    purchases.add(userID+merchantID+productID);
                    return "Product purchased successfully";
                }
        }
        return "case3";
    }

    public ArrayList<Product> availableProductsBasedOnCategory(String categoryID){
        ArrayList<Product> productsAvailable = new ArrayList<>();
        for (MerchantStock m : merchantStocks){
            if (m.getStock()< 1)
                continue;
            for (Product p: ProductService.products)
                if (p.getId().equals(m.getProductID())){
                    if (p.getCategoryID().equals(categoryID) && !productsAvailable.contains(p))//maybe there is more than 1 merchant selling the same product. hence, check
                        productsAvailable.add(p);
                    break;
                }
        }
        return productsAvailable;
    }

    public String merchantInventoryValue(String merchantID){
        boolean merchantExists = false;
        for (Merchant m: MerchantService.merchants)
            if (m.getId().equals(merchantID)) {
                merchantExists = true;
                break;
            }
        if (!merchantExists)
            return "case1";

        double total =0;
        for (MerchantStock m: merchantStocks)
            if (m.getMerchantID().equals(merchantID))
                for ( Product p: ProductService.products)
                    if(m.getProductID().equals(p.getId()))
                        total=total +p.getPrice()*m.getStock();
        return "Total inventory value is: "+ total;
    }

    public boolean returnProduct(String userID, String merchantID, String productID){
        String code = userID+merchantID+productID;
        boolean foundMatch = false;
        for ( String s : purchases)
            if (s.equals(code)) {
                foundMatch = true;
                break;
            }
        if (foundMatch){
            double price=0;
            for (Product p : ProductService.products)
                if( p.getId().equals(productID)){
                    price = p.getPrice();
                    break;
                }
            for(User u: UserService.users)
                if(u.getId().equals(userID)){
                    u.setBalance(u.getBalance()+price);
                    break;
                }
            for (MerchantStock ms : merchantStocks)
                if (ms.getProductID().equals(productID)&&ms.getMerchantID().equals(merchantID)){
                    ms.setStock(ms.getStock()+1);
                    break;
                }
            purchases.remove(code);
        }
        return foundMatch;
    }

}

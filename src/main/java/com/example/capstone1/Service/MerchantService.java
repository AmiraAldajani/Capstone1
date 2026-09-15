package com.example.capstone1.Service;

import com.example.capstone1.Model.Category;
import com.example.capstone1.Model.Merchant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {
    protected static ArrayList<Merchant> merchants = new ArrayList<>();

    public ArrayList<Merchant> getMerchants(){
        return merchants;
    }

    public boolean addMerchants(Merchant merchant){
        for ( Merchant m : merchants){
            if (m.getId().equals(merchant.getId()))
                return false;
        }
        merchants.add(merchant);
        return true;
    }

    public boolean deleteMerchants(String id){
        for ( Merchant m : merchants)
            if (m.getId().equals(id)) {
                merchants.remove(m);
                return true;
            }
        return false;
    }

    public boolean updateMerchant(Merchant merchant){
        for ( int i =0; i<merchants.size(); i++)
            if (merchants.get(i).getId().equals(merchant.getId())){
                merchants.set(i, merchant);
                return true;
            }
        return false;
    }
}

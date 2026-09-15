package com.example.capstone1.Service;

import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    protected static ArrayList<User> users = new ArrayList<>();

    public ArrayList<User> getUsers(){
        return users;
    }

    public boolean addUser(User user){
        for ( User u : users){
            if (u.getId().equals(user.getId()))
                return false;
        }
        users.add(user);
        return true;
    }

    public boolean deleteUser(String id){
        for ( User u: users)
            if (u.getId().equals(id)) {
                users.remove(u);
                return true;
            }
        return false;
    }

    public boolean updateUser(User user){
        for ( int i =0; i<users.size(); i++)
            if (users.get(i).getId().equals(user.getId())){
                users.set(i, user);
                return true;
            }
        return false;
    }

    public String transferBalance(String senderID, String receiverID, double amount ){
        if ( amount<0)
            return "case1";
        for ( User u: users) {
            if (u.getId().equals(senderID))
                if (u.getBalance() >= amount) {
                    for (User receiver: users)
                        if (receiver.getId().equals(receiverID)) {
                            u.setBalance(u.getBalance()-amount);
                            receiver.setBalance(receiver.getBalance()+amount);
                            return "Transformation completed!"; //case3
                        }
                    return "case3";
                } else return "case2";
        }
        return "case5";
    }
}

package com.wat.recipesapp.recipies;

import com.sun.istack.NotNull;
import com.wat.recipesapp.user.AuthProvider;
import com.wat.recipesapp.user.User;

import javax.persistence.*;

@Entity(name = "Recipies")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String desciption;
    //To bedzie w przypadku, jesli nie powiazemy many to one
    @NotNull
    private Long userId;
    //private String image;


    public Recipe(String desciption, Long userId) {
        this.desciption = desciption;
        this.userId = userId;
    }

    public Recipe() {
    }
    //tutaj ma byc w join column nazwa kolumny, po ktorej laczymy fo userow
    // , ja nwm jak ta kolumna sie nazywa
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ID")
//    private User user;

}

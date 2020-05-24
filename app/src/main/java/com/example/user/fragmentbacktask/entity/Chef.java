package com.example.user.fragmentbacktask.entity;

import javax.inject.Inject;

public  class Chef {
   private int id;

   @Inject
    public Chef(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

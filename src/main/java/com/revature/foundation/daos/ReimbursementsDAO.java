package com.revature.foundation.daos;

import com.revature.foundation.models.Users;

import java.sql.Connection;

public class ReimbursementsDAO implements CrudDAO<Users> {

    private Connection conn;

//    public UseReimbursementDAOrDAO(Connection conn) {
//        this.conn = conn;
//    }

    public Users filterReimbursementByType(String username, String password) {


        return null;

    }

    public Users filterReimbursementByStatus(String username, String password) {


        return null;

    }

    @Override
    public void save(Users newObject) {

    }

    @Override
    public Users getById(String id) {
        return null;
    }

    @Override
    public Users[] getAll() {
        return new Users[0];
    }

    @Override
    public void update(Users updatedObject) {

    }

    @Override
    public void deleteById(String id) {

    }
}

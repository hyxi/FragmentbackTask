package com.example.user.fragmentbacktask.viewmodel.room;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Created by huangyuxi on 2019-08-20
 * Title:
 */
@Dao
public interface StudentDao {

    @Query("SELECT * FROM Student ORDER BY sname COLLATE NOCASE ASC")
    DataSource.Factory<Integer, Student> getAllStudent();

    @Query("SELECT * FROM STUDENT")
    List<Student> getStudents();

    @Insert
    void insert(List<Student> students);

    @Insert
    void insert(Student student);

    @Update
    void update(Student student);
//
//    public void insertAll(List<User> users) {
//        for(User user:users) {
//            if(user.pets != null) {
//                insertPetsForUser(user, user.pets);
//            }
//        }
////        _insertAll(users);
//    }
//
//    private void insertPetsForUser(User user, List<Pet> pets){
//
//        for(Pet pet : pets){
//            pet.setUserId(user.getId());
//        }
//
//        _insertAll(pets);
//    }
//public List<User> getUsersWithPetsEagerlyLoaded() {
//    List<UserWithPets> usersWithPets = _loadUsersWithPets();
//    List<User> users = new ArrayList<User>(usersWithPets.size())
//    for(UserWithPets userWithPets: usersWithPets) {
//        userWithPets.user.pets = userWithPets.pets;
//        users.add(userWithPets.user);
//    }
//    return users;
//}


//    @Insert
//    void _insertAll(List<Pet> pets);

//    @Insert
//    void _insertAll(List<User> users);
}

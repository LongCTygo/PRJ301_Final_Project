/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connect;

import java.sql.PreparedStatement;
import java.util.Vector;

/**
 *
 * @author ADMIN
 * @param <E> An Entity class
 */
public abstract class DAOEntity<E> extends DBConnect{
    
    /**
     * Add an entity to the database
     * @param entity 
     * @return 1 if success
     */
    public abstract int add(E entity);
    /**
     * Update an entity, using a new entity with a similar primary key(s)
     * @param entity
     * @return 1 if success
     */
    public abstract int update(E entity);
    
    /**
     *  Get all
     * @return a vector containing all entity
     */
    public abstract Vector<E> getAll();
    /**
     * Get all from a SQL.
     * @param sql
     * @return the Vector
     */
    public abstract Vector<E> getAll(String sql);
    
    public abstract Vector<E> getAll(PreparedStatement statement);
    
    /**
     * Remove an entity with 1 primary key.
     * @param s
     * @return
     */
    public int remove(String s){
        throw new UnsupportedOperationException("Does not implement.");
    }
    /**
     * Remove an entity with 2 primary keys.
     * @param s1
     * @param s2
     * @return
     */
    public int remove(String s1, String s2){
        throw new UnsupportedOperationException("Does not implement.");
    }
    
}

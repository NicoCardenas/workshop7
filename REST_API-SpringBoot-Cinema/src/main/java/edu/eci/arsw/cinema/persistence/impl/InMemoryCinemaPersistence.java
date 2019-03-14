/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.persistence.impl;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 *
 * @author cristian
 */
@Service
public class InMemoryCinemaPersistence implements CinemaPersitence{
    
    private final Map<String,Cinema> cinemas=new HashMap<>();

    public InMemoryCinemaPersistence() {
        //load stub data
        String functionDate = "2018-12-18 15:30";
        String functionDate2 = "2018-12-18 16:30";
        
        List<CinemaFunction> functions= new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie","Action"),functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night","Horror"),functionDate2);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c=new Cinema("CinemaX",functions);
        cinemas.put("CinemaX", c);
        
        List<CinemaFunction> functions2= new ArrayList<>();
        CinemaFunction funct12 = new CinemaFunction(new Movie("SuperHeroes Movie","Action"),functionDate);
        CinemaFunction funct22 = new CinemaFunction(new Movie("The Night","Horror"),functionDate2);
        functions2.add(funct12);
        functions2.add(funct22);
        Cinema c2=new Cinema("CinemaY",functions2);
        cinemas.put("CinemaY", c2);        
    }    

    @Override
    public void buyTicket(int row, int col, String cinema, String date, String movieName) throws CinemaException {
        List<CinemaFunction> temp = getFunctionsbyCinemaAndDate(cinema, date);
        if (temp.isEmpty()) throw new CinemaException("Can't fine " + cinema +" "+ date);
        for (CinemaFunction cfuncions : temp) {            
            if (cfuncions.getMovie().getName().equals(movieName))
                cfuncions.buyTicket(row, col);
        }
        
    }

    @Override
    public List<CinemaFunction> getFunctionsbyCinemaAndDate(String cinema, String date) {
        List<CinemaFunction> temp = new ArrayList<>();
        Cinema Ctemp = cinemas.get(cinema);
        Ctemp.getFunctions().stream().filter((cf) -> (cf.getDate().equals(date))).forEachOrdered((cf) -> {
            temp.add(cf);
        });        
        return temp;
    }

    @Override
    public void saveCinema(Cinema c) throws CinemaPersistenceException {
        if (cinemas.containsKey(c.getName())){
            throw new CinemaPersistenceException("The given cinema already exists: "+c.getName());
        }
        else{
            cinemas.put(c.getName(),c);
        }   
    }

    @Override
    public Cinema getCinema(String name) throws CinemaPersistenceException {        
        if (cinemas.containsKey(name)) {
            return cinemas.get(name);
        }else
            throw new CinemaPersistenceException("Not exist a cinema with name: "+ name + "." );
        
    }

    @Override
    public Set<Cinema> getAllCinemas() {

        Set<String> mapKeys = cinemas.keySet();
        Set<Cinema> Setcinemas = new HashSet();
        mapKeys.forEach((mapKey) -> {
            Setcinemas.add(cinemas.get(mapKey));
        });
        return Setcinemas;
    }

}

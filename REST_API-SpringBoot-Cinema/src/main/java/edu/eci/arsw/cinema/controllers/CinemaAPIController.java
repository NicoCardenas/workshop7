/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.controllers;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.services.CinemaServices;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author cristian
 */
@RestController
@Service
@RequestMapping(value = "/cinemas")
public class CinemaAPIController {
    
    @Autowired
    CinemaServices cs;
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllCinemas(){
        try {
            //obtener datos que se enviarán a través del API
            return new ResponseEntity<>(cs.getAllCinemas(),HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping(path = "/{name}",  produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> getCinema(@PathVariable String name) throws ResourceNotFoundException{
        try {            
            return new ResponseEntity<>(cs.getCinemaByName(name), HttpStatus.ACCEPTED);
        } catch (CinemaException e) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(new ResourceNotFoundException(e.getMessage()).getMessage(), HttpStatus.NOT_FOUND);
        }        
    }
    
    @GetMapping("/{name}/{date}")
    public ResponseEntity<?> getDate(@PathVariable String name, @PathVariable String date){
        if (!cs.getFunctionsbyCinemaAndDate(name, date).isEmpty())
            return new ResponseEntity<>(cs.getFunctionsbyCinemaAndDate(name, date), HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(new ResourceNotFoundException("There are not funcions with this date " + date).getMessage(), HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/{name}/{date}/{moviename}")
    public ResponseEntity<?> getMoviename(@PathVariable String name, @PathVariable String date, @PathVariable String moviename){
        if (!cs.getFunctionsbyCinemaAndDate(name, date).isEmpty()){
            for (CinemaFunction function : cs.getFunctionsbyCinemaAndDate(name, date)) {
                if (function.getMovie().getName().equals(moviename))
                    return new ResponseEntity<>(function, HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(new ResourceNotFoundException("There are not funcions with this name " + moviename).getMessage(), HttpStatus.NOT_FOUND);
        }            
        else
            return new ResponseEntity<>(new ResourceNotFoundException("There are not funcions with this name " + moviename).getMessage(), HttpStatus.NOT_FOUND);        
    }
    
    @RequestMapping(path = "/{name}", method = RequestMethod.POST, consumes = "application/json")    
    public ResponseEntity<?> postCinemaFuncion(@PathVariable String name, @RequestBody CinemaFunction function){
        try {
            //registrar dato
            cs.addCinemaFunction(name, function);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CinemaPersistenceException ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(new ResourceNotFoundException(ex.getMessage()).getMessage(),HttpStatus.FORBIDDEN);
        }         
    }
    
    @RequestMapping(path = "/{name}")
    public ResponseEntity<?> putCinemaFuncion(@PathVariable String name, @RequestBody CinemaFunction function){
        try {
            //registrar dato
            cs.updateCinemaFunction(name, function);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CinemaPersistenceException ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(new ResourceNotFoundException(ex.getMessage()).getMessage(),HttpStatus.FORBIDDEN);
        }   
    }
}

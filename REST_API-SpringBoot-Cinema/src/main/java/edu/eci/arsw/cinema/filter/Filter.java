package edu.eci.arsw.cinema.filter;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.Movie;
import java.util.List;

public interface Filter {
    
    public List<Movie> filterBy(Cinema cinema, String date, String filter);
}

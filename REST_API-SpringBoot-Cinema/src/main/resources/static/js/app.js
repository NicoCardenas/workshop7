
$(document).ready(function () {
    $("#button").click(function () {
        var name = $("input").val();
        var data = Tabla.getfuntions(name);       
        var res = "";
        jQuery.each(data.funct, function (i ,val) {
            var c = "<td>"+data.name+"</td>";
            var f = "<td>"+val.name+"</td>";
            var s = "<td>"+val.seat+"</td>";
            var fe = "<td>"+val.fecha+"</td>";
            var markup = "<tr>"+c+f+s+fe+"</tr>";
            
            res += markup;
        });
        document.getElementById("data").innerHTML = res;        
    });
});

var Tabla = (function () {
    
    var CinemaName;
    var funtions = new Array();
    
    var freeSeats = function (seats) {
        var count = 0;
        for (var i in seats) { 
            for (var j in seats[i]) { 
                if (seats[i][j]) { 
                    count ++; 
                } 
            } 
        }
        return count;
    };
    
    var mapFunction = function (val) {
        return {
            name : val.movie.name,
            seat : freeSeats(val.seats),
            fecha : val.date
        };
    };

    var getCinema = function(name) {        
        apimock.getCinemaByName(name, function (data) {            
            for (i in data){
                funtions.push(data[i].functions.map(mapFunction));
            }            
        });
        //console.log(funtions);
        return funtions[0];
    };
    
    var func = function (name) {
        CinemaName = name;
        return {
            name : CinemaName,
            funct : getCinema(name)
        };
    };
    
    return {
        getfuntions: function (name) {
            return func(name);            
        }
    };
})();
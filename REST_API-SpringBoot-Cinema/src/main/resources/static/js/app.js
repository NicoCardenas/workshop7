import "apimock.js";

var Tabla = (function () {   

    var Cinemas = [];
    var Functions = [];

    var _getCinemas = function() {
        Cinemas[0] = mock.getCinemaByName("Cine80", function (value){ return value;});
    };
    
    var _getFunctions = function() {

        };

    var getData = function () {
        return _getCinemas();
    };

    return {
        getData: getData
    };
})();
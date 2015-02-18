var sharedServices = angular.module("Services", []);

sharedServices.factory('TreeListService', function($http) {
    return $http.get("./input_files/reporting_file.json");
});

sharedServices.factory('SimilarToolService', function($http) {
    return $http.get("./input_files/similarTools.json");
});



sharedServices.factory('NewSimTools', function($http) {
    return $http.get("./input_files/newSimTools.json");
});

sharedServices.factory('DataTreeService', function($http) {
    return  $http.get("./input_files/dataTree.json");
});

//sharedServices.factory('RestServices', function($http) {
//    return  $http.get("http://localhost:8080/BTL-REST/resources/tools/63");
//});

sharedServices.factory('RestServices', function($resource){
    return $resource('http://localhost:8080/BTL-REST/resources/tools/63', {})
    
}).value('version', '0.1');



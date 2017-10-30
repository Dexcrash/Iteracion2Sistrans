(function (ng) {
    var mod = ng.module("usuariosModule");
    mod.controller("usuariosCtrl", ['$scope', '$http', function ($scope, $http) {
            $scope.elements = [];
            $http.get("localhost:8080/RotondAndes/rest/usuarios")
                    .then(function (response) {
                        $scope.elements = response.data;
            });
        }]);
})(window.angular);
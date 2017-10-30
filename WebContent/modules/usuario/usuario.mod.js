(function (ng) {
    var mod = ng.module("usuariosModule", []);
        mod.constant("usuariosContext", "api/usuarios");
        mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
                var basePath = 'src/modules/usuario/';
                $urlRouterProvider.otherwise("");
    
                $stateProvider.state('usuariosList', {
                    url: '/usuarios',
                    views: {
                        'mainView': {
                            controller: 'usuariosCtrl',
                            controllerAs: 'ctrl',
                            templateUrl: basePath + 'usuario.html'
                        }
                    }
                });
            }]);
    
    })(window.angular);
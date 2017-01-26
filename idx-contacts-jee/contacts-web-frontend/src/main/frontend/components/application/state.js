function detailRoute($stateProvider) {

    return $stateProvider
        .state('index', {
            url: '/',
            views: {
                application: {
                    controller: 'ApplicationController as ctrl',
                    templateUrl: 'templates/application.html',
                    resolve: {
                        userInfo: ['UserService', function (userService) {
                            return userService.getUserInfo();
                        }]
                    }
                },
                menu: {
                    controller: 'ApplicationController as ctrl',
                    templateUrl: 'templates/default-menu.html'
                },
                userMenu: {
                    controller: 'ApplicationController as ctrl',
                    templateUrl: 'templates/user-menu.html',
                    resolve: {
                        userInfo: ['UserService', function (userService) {
                            return userService.getUserInfo();
                        }]
                    }
                }
            }
        });
}

export default ['$stateProvider', detailRoute];
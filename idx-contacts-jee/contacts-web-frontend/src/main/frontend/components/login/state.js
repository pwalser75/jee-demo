function detailRoute($stateProvider) {

    return $stateProvider
        .state('login', {
            url: '/login',
            views: {
                application: {
                    controller: 'LoginController as ctrl',
                    templateUrl: 'templates/login.html'
                },
                menu: {
                    controller: 'ApplicationController as ctrl',
                    templateUrl: 'templates/contact-menu.html'
                }
            }
        });
}

export default ['$stateProvider', detailRoute];

function detailRoute($stateProvider) {

	 return $stateProvider
        .state('index', {
            url: '/',
            views: {
                application: {
                    controller: 'ApplicationController as ctrl',
                    templateUrl: 'templates/application.html'
                },
				menu: {
                    controller: 'ApplicationController as ctrl',
                    templateUrl: 'templates/default-menu.html'
                }
            }
        });
}

export default ['$stateProvider', detailRoute];
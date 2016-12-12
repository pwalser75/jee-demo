function detailRoute($stateProvider) {

	 return $stateProvider
        .state('contactDetail', {
            url: '/contacts/view/{id:[0-9]+}',
            views: {
                application: {
                    controller: 'ContactDetailController as ctrl',
                    templateUrl: 'templates/contact-detail.html',
					resolve: {
                        contact: ['ContactService', '$stateParams', function(contactService, $stateParams) {
							console.log("resolve contact: "+$stateParams.id);
                            return contactService.getContact($stateParams.id);
                        }]
                    }
                },
				menu: {
                    controller: 'ApplicationController as ctrl',
                    templateUrl: 'templates/contact-menu.html'
                }
            }
        });
}

export default ['$stateProvider', detailRoute];
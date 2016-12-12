function detailRoute($stateProvider) {

	 return $stateProvider
        .state('contacts', {
            url: '/contacts',
            views: {
                application: {
                    controller: 'ContactListController as ctrl',
                    templateUrl: 'templates/contact-list.html',
					resolve: {
                        contacts: ['ContactService', function(contactService) {
                            return contactService.listContacts();
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
function detailRoute($stateProvider) {

	 return $stateProvider
        .state('editContact', {
            url: '/contacts/edit/{id:[0-9]+}',
            views: {
                application: {
                    controller: 'ContactEditController as ctrl',
                    templateUrl: 'templates/contact-edit.html',
					resolve: {
                        contact: ['ContactService', '$stateParams', function(contactService, $stateParams) {
                            return contactService.getContact($stateParams.id);
                        }]
                    }
                },
				menu: {
                    controller: 'ApplicationController as ctrl',
                    templateUrl: 'templates/contact-menu.html'
                }
            }
        })
		.state('addContact', {
            url: '/contacts/add',
            views: {
                application: {
                    controller: 'ContactEditController as ctrl',
                    templateUrl: 'templates/contact-edit.html',
					resolve: {
                        contact: ['ContactService','$q', function(contactService, $q) {
						
							var deferred = $q.defer();
							setTimeout(function() {
								var contact = contactService.newContact();
								deferred.resolve(contact);
							  }, 300);
							return deferred.promise;
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
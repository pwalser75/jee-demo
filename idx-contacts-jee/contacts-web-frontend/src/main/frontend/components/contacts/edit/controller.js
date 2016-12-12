class ContactEditController {
	
    constructor(contactService, $state, contact) {
	
		this.contactService=contactService;
		this.$state=$state;
		this.original=contact;
		this.reset();
    }
	
	reset() {
		this.contact=angular.copy(this.original);
	}
	
	save() {
		return this.contactService
			.saveContact(this.contact)
			.then(r => 
				this.$state.go('contacts')
			);
	}
}
ContactEditController.$inject=['ContactService', '$state', 'contact'];
export default ContactEditController;
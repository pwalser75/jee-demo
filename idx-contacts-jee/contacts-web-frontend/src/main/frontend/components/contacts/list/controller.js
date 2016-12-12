class ContactListController {
	
    constructor(contacts, $state) {
		this.contacts=contacts;
		this.$state=$state;
    }
	
	showContact(contact) {
		this.$state.go('contactDetail',{ 'id': contact.id });
	}
}
ContactListController.$inject=['contacts', '$state'];
export default ContactListController;
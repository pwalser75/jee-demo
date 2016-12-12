class ContactService {

    constructor($http) {
		this.$http=$http;
    }
	
	listContacts() {
		return this.$http.get('api/contact').then(function(response) {
              return response.data;
            });		
	}
	
	getContact(id) {
		return this.$http.get('api/contact/'+id).then(function(response) {
              return response.data;
            });
	}
	
	newContact() {
		return {};
	}
	
	saveContact(contact) {
		return this.$http.post('api/contact', contact).then(function(response) {
              return response.data;
            });
	}	
}
ContactService.$inject=['$http'];
export default ContactService;
class ContactDetailController {
	
    constructor(contact) {
		console.log("Constructor123: "+contact);
		this.contact=contact;
    }
}
ContactDetailController.$inject=['contact'];
export default ContactDetailController;
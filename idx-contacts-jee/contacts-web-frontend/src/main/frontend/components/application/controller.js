class ApplicationController {

    constructor(userInfo) {
        this.title = "idx Contacts";
        this.message = "JEE / Angular Demo Project";
        this.data = [
            {
                title: "First",
                description: "This is the first item"
            },
            {
                title: "Second",
                description: "This is the second item"
            },
            {
                title: "Third",
                description: "This is the third item"
            }
        ];

        this.userInfo = userInfo;
    }
}
ApplicationController.$inject = ['userInfo'];
export default ApplicationController;
class LoginController {

    constructor($state) {

        this.$state = $state;
    }

    reset() {
        console.log("LoginForm: reset()");
    }

    login() {
        console.log("LoginForm: login(): " + this.username + " : " + this.password);
    }
}
LoginController.$inject = ['$state'];
export default LoginController;
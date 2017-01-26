class UserService {

    constructor($http) {
        this.$http = $http;
    }

    getUserInfo() {
        return this.$http.get('api/info/user').then(function (response) {
            return response.data;
        });
    }
}
UserService.$inject = ['$http'];
export default UserService;
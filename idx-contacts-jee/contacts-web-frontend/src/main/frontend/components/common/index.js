import angular from "angular";
import commaSeparatedFilter from "./filters/CommaSeparatedFilter";
import userService from "./services/UserService";
import contactService from "./services/ContactService";


const dependencies = [];

export default angular
    .module('Common', dependencies)
    .filter('commaSeparated', commaSeparatedFilter)
    .service('UserService', userService)
    .service('ContactService', contactService);

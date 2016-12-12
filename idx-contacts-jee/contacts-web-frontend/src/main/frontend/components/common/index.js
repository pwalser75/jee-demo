import angular from 'angular';
import commaSeparatedFilter from './filters/CommaSeparatedFilter';
import contactService from './services/ContactService';

const dependencies = [];

export default angular
    .module('Common', dependencies)
    .filter('commaSeparated', commaSeparatedFilter)
	.service('ContactService', contactService);

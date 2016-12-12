import angular from 'angular';
import contactList from './list/index';
import contactDetail from './detail/index';
import contactEdit from './edit/index';

const dependencies = [
   'ui.router',
   contactList.name,
   contactDetail.name,
   contactEdit.name
];

export default angular
    .module('Contact', dependencies);

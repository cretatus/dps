var router = angular.module('cometa', [ 'ui.router', 'mwl.confirm', 'ngDialog', 'angularjs-dropdown-multiselect', 'ngAnimate', 'ui.bootstrap', 'ui.tree', 'smart-table']);
router.config(function($stateProvider, $urlRouterProvider) {
	$stateProvider
	.state('main', {
		url : '/',
		templateUrl : 'login.html',
		controller : 'LoginController'})
	.state('admin', {
		url : '/admin', 
		templateUrl : 'admin.html',	
		controller : 'DefaultController'})
	.state('application', {
		url : '/application', 
		templateUrl : 'application.html',	
		controller : 'ApplicationController'})
	.state('invitation', {
		url : '/invitation', 
		templateUrl : 'invitation.html',	
		controller : 'InvitationController'})
	.state('inbox', {
		url : '/inbox', 
		templateUrl : 'inbox.html',	
		controller : 'InboxController'})
	.state('app', {
		url : '/app', 
		templateUrl : 'default.html',	
		controller : 'DefaultController'})
	.state('module', {
		url : '/module', 
		templateUrl : 'module.html',	
		controller : 'ModuleController'})
	.state('version', {
		url : '/version', 
		templateUrl : 'version.html',	
		controller : 'VersionController'})
	.state('dependency', {
		url : '/dependency', 
		templateUrl : 'dependency.html',	
		controller : 'DependencyController'})
	.state('stereotype', {
		url : '/stereotype', 
		templateUrl : 'stereotype.html',	
		controller : 'StereotypeController'})
	.state('platform', {
		url : '/platform', 
		templateUrl : 'platform.html',	
		controller : 'PlatformController'})
	.state('component', {
		url : '/component', 
		templateUrl : 'component.html',	
		controller : 'ComponentController'})
	.state('package', {
		url : '/package', 
		templateUrl : 'package.html',	
		controller : 'PackageController'})
	.state('model', {
		url : '/model', 
		templateUrl : 'default.html',	
		controller : 'DefaultController'})
	.state('area', {
		url : '/area', 
		templateUrl : 'area.html',	
		controller : 'AreaController'})
	.state('element', {
		url : '/element', 
		templateUrl : 'element.html',	
		controller : 'ElementController'})
	.state('structure', {
		url : '/structure', 
		templateUrl : 'structure.html',	
		controller : 'StructureController'})
	.state('entity', {
		url : '/entity', 
		templateUrl : 'entity.html',	
		controller : 'EntityController'})
	.state('assembly', {
		url : '/assembly', 
		templateUrl : 'assembly.html',	
		controller : 'AssemblyController'})
	.state('generator', {
		url : '/generator', 
		templateUrl : 'generator.html',	
		controller : 'GeneratorController'})
	.state('debug', {
		url : '/debug', 
		templateUrl : 'debug.html',	
		controller : 'DebugController'})
	.state('build', {
		url : '/build', 
		templateUrl : 'build.html',	
		controller : 'BuildController'})
	.state('transformation', {
		url : '/transformation', 
		templateUrl : 'transformation.html',	
		controller : 'TransformationController'})
	;

	$urlRouterProvider.otherwise('/');
});

router.directive('numbersOnly', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attr, ngModelCtrl) {
            function fromUser(text) {
                if (text) {
                    var transformedInput = text.replace(/[^0-9-]/g, '');
                    if (transformedInput !== text) {
                        ngModelCtrl.$setViewValue(transformedInput);
                        ngModelCtrl.$render();
                    }
                    return transformedInput;
                }
                return undefined;
            }
            ngModelCtrl.$parsers.push(fromUser);
        }
    };
});

router.directive('myInput', function () {
    return {
        restrict: 'A',
        link: function (scope, element) {
            element.bind('click', function (event) {
                event.stopPropagation();
            });
        }
    };
});

router.directive('caption', function () {
	return {
		
        link: function (scope, element, attrs) {
        	element.html("<a class='sortingHeader'>"+element.text()+"</a>");
        	element.find("a").attr("field", attrs.field);
        	element.find("a").on('click', function (e) {
        		scope.sortType = e.currentTarget.attributes.field.value;
        		scope.sortReverse = !scope.sortReverse;
            });	
        	
        }
    };
});

router.factory('httpService', function($http) {
        return {
            getParameterStructures: function(parameterStructureSet) {
                return $http.post('load/parameter_structures_by_parameter_structure_set', parameterStructureSet.id);
            }
        };
    });

router.factory('Excel',function($window){
	var uri='data:application/vnd.ms-excel;base64,',
		template='<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>',
		base64=function(s){return $window.btoa(unescape(encodeURIComponent(s)));},
		format=function(s,c){return s.replace(/{(\w+)}/g,function(m,p){return c[p];})};
	return {
		tableToExcel:function(tableId,worksheetName){
			var table=$(tableId),
				ctx={worksheet:worksheetName,table:table.html()},
				href=uri+base64(format(template,ctx));
			return href;
		}
	};
})

router.directive('fileModel', ['$parse', function ($parse) {
    return {
       restrict: 'A',
       link: function(scope, element, attrs) {
          var model = $parse(attrs.fileModel);
          var modelSetter = model.assign;

          element.bind('change', function(){
             scope.$apply(function(){
                modelSetter(scope, element[0].files[0]);
             });
          });
       }
    };
}]);

router.service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function(file, uploadUrl, parameters){
       var fd = new FormData();
       fd.append('file', file);
       fd.append('parameters', JSON.stringify(parameters));

       $http.post(uploadUrl, fd, {
          transformRequest: angular.identity,
          headers: {'Content-Type': undefined}
       })

       .success(function(){
			$scope.popupMessage = 'Файл успешно загружен';
			ngDialog.open({	template: 'popup',	scope: $scope});
       })

       .error(function(){
			$scope.popupMessage = 'Что-то пошло не так';
			ngDialog.open({template: 'popup',scope: $scope});
       });
    }
}]);
router.directive('ngModelOnblur', function() {
   return {
       restrict: 'A',
       require: 'ngModel',
       priority: 1,
       link: function(scope, elm, attr, ngModelCtrl) {
           if (attr.type === 'radio' || attr.type === 'checkbox') return;

           elm.unbind('input').unbind('keydown').unbind('change');
           elm.bind('blur', function() {
               scope.$apply(function() {
                   ngModelCtrl.$setViewValue(elm.val());
               });
           });
       }
   };
});

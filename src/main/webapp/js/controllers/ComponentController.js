var componentController = angular.module('cometa');

componentController.controller('ComponentController', function ($scope, $http, ngDialog, $q) {
	$scope.regexSysname = '[a-zA-Z][\\w]*';
	$scope.show = function(){
		$scope.addView = false;
		$scope.editView = false;
		$scope.tableView = true;
		$scope.component={};
		$scope.components=[];
		$scope.read();
	}

	$scope.add = function(){
		$scope.addView = true;
		$scope.editView = false;
		$scope.tableView = false;
		$scope.component={};
		$scope.component.pack=$scope.pack;
		$scope.component.filterMethod='true';
		$scope.component.fileNameTemplate='object.sysname';
	}
	
	$scope.edit = function(component){
		$scope.addView = false;
		$scope.editView = true;
		$scope.tableView = false;
		$scope.component=component;
	}
	
	$scope.read = function(){
		if(!$scope.currentVersion) return;
		$http.get('read/components')
		.success(function(data, status, headers, config) {
            $scope.components = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
	}

	$scope.readLookups = function(){
		if(!$scope.currentVersion) return;
		$http.get('read/platforms_lookup')
		.success(function(data, status, headers, config) {
            $scope.platforms = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
		$http.get('read/packages_lookup')
		.success(function(data, status, headers, config) {
            $scope.packages = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
	}

	$scope.save = function(){
		$http.post('save/component', $scope.component)
			.success(function(data, status, headers, config){
				$scope.popupMessage = 'Done';
				ngDialog.open({template: 'popup', scope: $scope});
				$scope.show();
			})
			.error(function(data, status, headers, config){
				$scope.popupMessage = data.message;
				ngDialog.open({template: 'popup', scope: $scope});
			});
	}

	$scope.remove = function(component){
        $scope.confirmMessage = "Remove record?";
        ngDialog.openConfirm({
            template: 'confirm_form.html',
            className: 'ngdialog-theme-default custom-width',
            scope: $scope
        }).then(
        	function () {
        		$http.post('remove/component', component)
    			.success(function (data, status, headers, config) {
    				$scope.popupMessage = 'Done';
    				ngDialog.open({template: 'popup', scope: $scope});
    				$scope.show();
    			})
    			.error(function (data, status, headers, config) {
    				$scope.popupMessage = data.message;
    				ngDialog.open({template: 'popup', scope: $scope });
    			});
        	}
        );

	}
	
	$scope.boot = function(){
		$http.get('read/versions')
		.success(function(data, status, headers, config) {
			$scope.versions = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
		$http.get('read/metatypes')
		.success(function(data, status, headers, config) {
			$scope.metatypes = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
		$http.get('read/current_version')
		.success(function(data, status, headers, config) {
			$scope.currentVersion = data;
			$scope.readLookups();
			$scope.show();
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
	}

	$scope.filterModuleChange = function(){
		$scope.pack = {};
		if(!$scope.currentVersion) return;
		$http.post('save/current_version', $scope.currentVersion)
		.success(function(data, status, headers, config) {
			$scope.readLookups();
			$scope.read();
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
	}
	
	$scope.metatypeChange = function(){
		if(!component.metatype) return;
		$scope.component.fileNameTemplate = component.metatype.code + ".sysname";
	}
	
	$scope.boot();
});
var dependencyController = angular.module('cometa');

dependencyController.controller('DependencyController', function ($scope, $http, ngDialog, $q) {

	$scope.show = function(){
		$scope.addDependencyView = false;
		$scope.editDependencyView = false;
		$scope.tableView = true;
		$scope.read();
	}

	$scope.addDependency = function(influencerVersion, dependentVersion){
		$scope.addDependencyView = true;
		$scope.editDependencyView = false;
		$scope.tableView = false;
		$scope.dependency={};
		$scope.disableInfluencer = false;
		$scope.disableDependent = false;
		if(influencerVersion){
			$scope.dependency.influencerVersionModule = influencerVersion.module;
			$scope.dependency.influencerVersion = influencerVersion;
			$scope.disableInfluencer = true;
		} 
		if(dependentVersion){
			$scope.dependency.dependentVersionModule = dependentVersion.module;
			$scope.dependency.dependentVersion = dependentVersion;
			$scope.disableDependent = true;
		}
	}
	
	$scope.editDependency = function(dependency){
		$scope.addDependencyView = false;
		$scope.editDependencyView = true;
		$scope.tableView = false;
		$scope.disableInfluencer = false;
		$scope.disableDependent = false;
		$scope.dependency=dependency;
		$scope.dependency.influencerVersionModule = dependency.influencerVersion.module;
		$scope.dependency.dependentVersionModule = dependency.dependentVersion.module;
		$scope.disableInfluencer = true;
		$scope.disableDependent = true;
	}
	
	$scope.boot = function(){
		$scope.module={};
		$http.get('read/versions')
		.success(function(data, status, headers, config) {
			$scope.versions = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
		$http.get('read/current_version')
		.success(function(data, status, headers, config) {
			$scope.currentVersion = data;
			$scope.show();
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
	}

	$scope.read = function(){
		if(!$scope.currentVersion) return;
		$http.post('save/current_version', $scope.currentVersion)
		.success(function(data, status, headers, config) {
			$scope.readLookups();
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
	}

	$scope.readLookups = function(){
		if(!$scope.currentVersion || !$scope.currentVersion.id) return;
		$http.get('read/dependencies_by_influencer_version')
		.success(function(data, status, headers, config) {
            $scope.dependents = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
		$http.get('read/dependencies_by_dependent_version')
		.success(function(data, status, headers, config) {
            $scope.influencers = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
		$http.get('read/modules')
		.success(function(data, status, headers, config) {
			$scope.modules = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
	}
	
	$scope.saveDependency = function(){
		$http.post('save/dependency', $scope.dependency)
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

	$scope.removeDependency = function(dependency){
        $scope.confirmMessage = "Remove record?";
        ngDialog.openConfirm({
            template: 'confirm_form.html',
            className: 'ngdialog-theme-default custom-width',
            scope: $scope
        }).then(
        	function () {
        		$http.post('remove/dependency', dependency)
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
	
    $scope.boot();
});
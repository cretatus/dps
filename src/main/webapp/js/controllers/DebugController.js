var debugController = angular.module('cometa');

debugController.controller('DebugController', function ($scope, $http, ngDialog, $q) {
	
	$scope.read = function(){
		if(!$scope.currentVersion) return;
		$http.post('/save/current_version', $scope.currentVersion)
		.success(function(data, status, headers, config) {
			$scope.readLookups();
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});

	}

	$scope.readLookups = function(){
		if(!$scope.currentVersion) return;
		$http.get('/read/packages_lookup')
		.success(function(data, status, headers, config) {
            $scope.packages = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
		$http.get('/read/components_lookup')
		.success(function(data, status, headers, config) {
            $scope.components = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
	}

	$scope.boot = function(){
		$scope.search = {};
		$scope.component = {};
		$scope.object = {};
		$scope.resultText = "None";
		$http.get('/read/versions')
		.success(function(data, status, headers, config) {
			$scope.versions = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
		$http.get('/read/current_version')
		.success(function(data, status, headers, config) {
			$scope.currentVersion = data;
			$scope.readLookups();
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
	}

	$scope.reloadObjects = function(){
		if(!$scope.component) return;
		$http.post('/read/objects_by_component', $scope.component)
		.success(function(data, status, headers, config) {
			$scope.objects = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
	}
	
	$scope.openFile = function(){
        var file = $scope.importFile;
        var fileReader = window.FileReader ? new FileReader() : null;

        if (file){
        	if (fileReader){
        		fileReader.addEventListener("loadend", $scope.refreshText, false);
        		fileReader.readAsText(file);
            } 
        }
	}

	$scope.refreshText = function(e){
		$scope.templateText = e.target.result;
	}
	
	$scope.templateTextChange = function(){
		window.document.getElementById("importFile").value = "";
		$scope.importFile = undefined;
	}
	
	$scope.generate = function(){
		var p = {};
		p["componentId"] = $scope.component.id;
		p["objectId"] = $scope.object.id;
		p["templateText"] = $scope.templateText;
		$http.post('/operation/generate_template', p)
		.success(function(data, status, headers, config) {
			$scope.resultText = data.resultText;
		})
		.error(function(data, status, headers, config) {
			$scope.resultText = data.message;
		});
	}
	
	$scope.boot();
});
var assemblyController = angular.module('cometa');

assemblyController.controller('AssemblyController', function ($scope, $http, ngDialog, $q) {
	$scope.regexSysname = '[a-zA-Z][\\w]*';
	$scope.show = function(){
		$scope.addAssemblyView = false;
		$scope.editAssemblyView = false;
		$scope.addVersionView = false;
		$scope.tableView = true;
		$scope.assembly={};
		$scope.assemblies=[];
		$scope.read();
	}

	$scope.addAssembly = function(){
		$scope.addAssemblyView = true;
		$scope.editAssemblyView = false;
		$scope.addVersionView = false;
		$scope.tableView = false;
		$scope.assembly={};
		$scope.assembly.versions=[];
	}
	
	$scope.addVersion = function(){
		$scope.addVersionView = true;
		$scope.versoin={};
		$scope.dependencies=[];
	}
	
	$scope.editAssembly = function(assembly){
		$scope.addAssemblyView = false;
		$scope.editAssemblyView = true;
		$scope.addVersionView = false;
		$scope.tableView = false;
		$scope.assembly=assembly;
	}
	
	$scope.addBuild = function(assembly){
        $scope.confirmMessage = "Build assembly?";
        ngDialog.openConfirm({
            template: 'confirm_form.html',
            className: 'ngdialog-theme-default custom-width',
            scope: $scope
        }).then(
        	function () {
        		$http.post('operation/build_assembly', assembly)
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
	
	$scope.read = function(){
		$http.get('read/assemblies')
		.success(function(data, status, headers, config) {
            $scope.assemblies = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
		$http.get('read/builds')
		.success(function(data, status, headers, config) {
            $scope.builds = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
	}

	$scope.saveAssembly = function(){
		$http.post('save/assembly', $scope.assembly)
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

	$scope.validateVersion = function(version){
		$scope.popupMessage = null;
		if(!version){
			$scope.popupMessage = "The version is required";
		}
		for(var i=0; i<$scope.assembly.versions.length; i++){
			if($scope.assembly.versions[i].id == version.id){
				$scope.popupMessage = "The version [" + version.label + "] already exists";
				break;
			}
		}
		if($scope.popupMessage){
			ngDialog.open({template: 'popup', scope: $scope});
			return false;
		}
		return true;
	}
	
	$scope.saveVersion = function(){
		if(!$scope.validateVersion($scope.version)){
			return false;
		}
		$scope.assembly.versions.push($scope.version);
		for(var i=0; i< $scope.dependencies.length; i++){
			if(!$scope.assembly.versions.find(function(v){return v.id == $scope.dependencies[i].influencerVersion.id;})){
				$scope.assembly.versions.push($scope.dependencies[i].influencerVersion);
			}
		}
		$scope.addVersionView = false;
	}

	$scope.cancelVersion = function(){
		$scope.addVersionView = false;
	}

	$scope.removeAssembly = function(assembly){
        $scope.confirmMessage = "Remove record?";
        ngDialog.openConfirm({
            template: 'confirm_form.html',
            className: 'ngdialog-theme-default custom-width',
            scope: $scope
        }).then(
        	function () {
        		$http.post('remove/assembly', assembly)
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
	
	$scope.removeVersion = function(version){
		$scope.version = version;
        $scope.confirmMessage = "Remove module version?";
        ngDialog.openConfirm({
            template: 'confirm_form.html',
            className: 'ngdialog-theme-default custom-width',
            scope: $scope
        }).then(
        	function () {
        		$scope.assembly.versions.splice($scope.assembly.versions.indexOf($scope.version), 1);
        		$scope.version = {};
        	}
        );
	}

	$scope.boot = function(){
		$http.get('read/versions')
		.success(function(data, status, headers, config) {
			$scope.versions = data;
			$scope.show();
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
		$http.get('read/dependency_influencers')
		.success(function(data, status, headers, config) {
			$scope.influencers = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
	}
	
	$scope.versionChange = function(){
		$scope.dependencies = $scope.influencers[$scope.version.id];
	}

	$scope.showBuild = function(index, assembly){
        $scope.assembly = assembly;
        $scope.selectedRow = index;
	}
	
	$scope.downloadBuild = function(build, count){
		$scope.showAjaxLoader = true;
		if(count === 21)
		{
			$scope.popupMessage = 'Timed out';
			ngDialog.open({template: 'popup',scope: $scope});
			$scope.showAjaxLoader = false;
			return;
		}
		$http({
			url: 'operation/download_build_files',
			method: 'POST',
			responseType: 'arraybuffer',
			data: build,
			headers: {'Content-type': 'application/json','Accept': 'application/zip'}
		})
			.success(function (data, status, headers, config) {
				console.log(data);
				if (data === null || data === undefined || data.byteLength === 0)
					$scope.downloadBuild(build, count + 1);
				else {
					var blob = new Blob([data], {type: "application/zip"});
					saveAs(blob, build.label + '.zip');
					$scope.showAjaxLoader = false;
				}
			})
			.error(function (data, status, headers, config) {
				$scope.popupMessage = data.message;
				ngDialog.open({template: 'popup',scope: $scope});
				$scope.showAjaxLoader = false;
			});
	}
	
	$scope.boot();
});
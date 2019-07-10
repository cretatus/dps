var versionController = angular.module('cometa');

versionController.controller('VersionController', function ($scope, $http, ngDialog, $q) {

	$scope.show = function(index){
		$scope.addMajorView = false;
		$scope.editMajorView = false;
		$scope.addMinorView = false;
		$scope.editMinorView = false;
		$scope.tableView = true;
		$scope.read(index);
	}

	$scope.addMajor = function(){
		$scope.addMajorView = true;
		$scope.editMajorView = false;
		$scope.addMinorView = false;
		$scope.editMinorView = false;
		$scope.tableView = false;
		$scope.majorVersion={};
		$scope.majorVersion.number = $scope.majorVersions.length;
	}
	
	$scope.addMinor = function(){
		$scope.addMajorView = false;
		$scope.editMajorView = false;
		$scope.addMinorView = true;
		$scope.editMinorView = false;
		$scope.tableView = false;
		$scope.minorVersion={};
		$scope.minorVersion.number = $scope.getMaxMinorVersion($scope.majorVersion) + 1;
	}
	
	$scope.editMajor = function(majorVersion){
		$scope.addMajorView = false;
		$scope.editMajorView = true;
		$scope.addMinorView = false;
		$scope.editMinorView = false;
		$scope.tableView = false;
		$scope.majorVersion=majorVersion;
	}
	
	$scope.editMinor = function(minorVersion){
		$scope.addMajorView = false;
		$scope.editMajorView = false;
		$scope.addMinorView = false;
		$scope.editMinorView = true;
		$scope.tableView = false;
		$scope.minorVersion=minorVersion;
	}
	
	$scope.boot = function(){
		$scope.module={};
		$scope.majorVersion={};
		$scope.majorVersions=[];
		$scope.minorVersion={};
		$scope.minorVersions=[];
		$http.get('/read/modules')
		.success(function(data, status, headers, config) {
            $scope.modules = data;
    		$http.get('/read/current_version')
    		.success(function(data, status, headers, config) {
    			$scope.module = data.module;
    			$scope.show(0);
    		})
    		.error(function(data, status, headers, config) {
    			$scope.popupMessage = data.message;
    			ngDialog.open({template: 'popup', scope: $scope});
    		});
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
	}

	$scope.read = function(index){
		if(!$scope.module.id) return;
		$scope.index = index;
		$http.post('/read/major_versions', $scope.module)
		.success(function(data, status, headers, config) {
            $scope.majorVersions = data;
            if($scope.index == 0){
            	$scope.showMinor(0, $scope.majorVersions[$scope.majorVersions.length-1]);
            }
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
		$http.post('/read/minor_versions', $scope.module)
		.success(function(data, status, headers, config) {
            $scope.minorVersions = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
	}

	$scope.saveMajor = function(){
		$scope.majorVersion.module = $scope.module;
		$http.post('/save/major_version', $scope.majorVersion)
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

	$scope.saveMinor = function(){
		$scope.minorVersion.module = $scope.module;
		$scope.minorVersion.majorVersion = $scope.majorVersion;
		$http.post('/save/minor_version', $scope.minorVersion)
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

	$scope.removeMajor = function(majorVersion){
        $scope.confirmMessage = "Remove record?";
        ngDialog.openConfirm({
            template: 'confirm_form.html',
            className: 'ngdialog-theme-default custom-width',
            scope: $scope
        }).then(
        	function () {
        		$http.post('/remove/major_version', majorVersion)
    			.success(function (data, status, headers, config) {
    				$scope.majorVersion = {};
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
	
	$scope.removeMinor = function(minorVersion){
        $scope.confirmMessage = "Remove record?";
        ngDialog.openConfirm({
            template: 'confirm_form.html',
            className: 'ngdialog-theme-default custom-width',
            scope: $scope
        }).then(
        	function () {
        		$http.post('/remove/minor_version', minorVersion)
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
	
	$scope.filterChange = function(){
		$scope.majorVersion={};
		$scope.majorVersions=[];
		$scope.minorVersion={};
		$scope.minorVersions=[];
	}

    $scope.showMinor = function(index, majorVersion){
        $scope.majorVersion = majorVersion;
        $scope.selectedRow = index;
    }
    
    $scope.getMaxMinorVersion = function(majorVersion){
    	var result = -1;
    	for(var i = 0; i < $scope.minorVersions.length; i++){
    		if($scope.minorVersions[i].majorVersion.id == majorVersion.id){
    			if(result < $scope.minorVersions[i].number){
    				result = $scope.minorVersions[i].number;
    			}
    		}
    	}
    	return result;
    }

    $scope.boot();
});
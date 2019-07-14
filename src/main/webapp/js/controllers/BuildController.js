var buildController = angular.module('cometa');

buildController.controller('BuildController', function ($scope, $http, ngDialog, $q) {
	
	$scope.boot = function(){
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
	
	$scope.read = function(){
		if(!$scope.build || !$scope.build.id) return;
		$http.post('read/build_logs', $scope.build)
		.success(function(data, status, headers, config) {
            $scope.buildLogs = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
	}

	$scope.downloadBuild = function(build, count){
		$http({
			url: 'operation/download_build_files',
			method: 'POST',
			responseType: 'arraybuffer',
			data: build,
			headers: {'Content-type': 'application/json','Accept': 'application/zip'}
		})
			.success(function (data, status, headers, config) {
				console.log(data);
				if (data === null || data === undefined || data.byteLength === 0){
					$scope.popupMessage = "Download error";
					ngDialog.open({template: 'popup',scope: $scope});
				}
				else {
					var blob = new Blob([data], {type: "application/zip"});
					saveAs(blob, build.label + '.zip');
				}
			})
			.error(function (data, status, headers, config) {
				$scope.popupMessage = data.message;
				ngDialog.open({template: 'popup',scope: $scope});
			});
	}

	$scope.downloadFile = function(buildLog, count){
		$http({
			url: 'operation/download_build_log_file',
			method: 'POST',
			responseType: 'arraybuffer',
			data: buildLog,
			headers: {'Content-type': 'application/json','Accept': 'application/zip'}
		})
			.success(function (data, status, headers, config) {
				console.log(data);
				if (data === null || data === undefined || data.byteLength === 0){
					$scope.popupMessage = "Download error";
					ngDialog.open({template: 'popup',scope: $scope});
				}
				else {
					var contentType = buildLog.isDirectory ? {type: "application/zip"} : {type: undefined};
					var blob = new Blob([data], contentType);
					saveAs(blob, buildLog.file);
				}
			})
			.error(function (data, status, headers, config) {
				$scope.popupMessage = data.message;
				ngDialog.open({template: 'popup',scope: $scope});
			});
	}
	
	$scope.boot();
});
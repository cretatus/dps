var generatorController = angular.module('cometa');

generatorController.controller('GeneratorController', function ($scope, $http, ngDialog, $q) {
	$scope.regexSysname = '[a-zA-Z][\\w]*';
	$scope.show = function(){
		$scope.addView = false;
		$scope.editView = false;
		$scope.tableView = true;
		$scope.generator={};
		$scope.generators=[];
		$scope.read();
	}

	$scope.add = function(){
		$scope.addView = true;
		$scope.editView = false;
		$scope.tableView = false;
		$scope.generator={};
		$scope.templateTextChange();
	}
	
	$scope.edit = function(generator){
		$scope.addView = false;
		$scope.editView = true;
		$scope.tableView = false;
		$scope.generator=generator;
		$http.post('/read/resource', generator.resourceId)
		.success(function(data, status, headers, config) {
			$scope.templateText = data.text;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
	}
	
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
		$http.get('/read/generators')
		.success(function(data, status, headers, config) {
            $scope.generators = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
		$http.get('/read/platforms_lookup')
		.success(function(data, status, headers, config) {
            $scope.platforms = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
		$http.post('/read/stereotypes_lookup', 'generator')
		.success(function(data, status, headers, config) {
            $scope.stereotypes = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
	}

	$scope.validate = function(generator){
		$scope.popupMessage = null;
		if(!generator.stereotype){
			$scope.popupMessage = "The field stereotype cannot be empty";
		}
		if(!generator.platform){
			$scope.popupMessage = "The field platform cannot be empty";
		}
		if(!$scope.importFile){
			$scope.popupMessage = "The template file was not selected";
		}
		if($scope.popupMessage){
			ngDialog.open({template: 'popup', scope: $scope});
			return false;
		}
		return true;
	}
	
	$scope.save = function(){
		if(!$scope.generator.resource){
			$scope.generator.resource = {};
		}
		if($scope.templateText){
			$scope.uploadText();
		}
		else if ($scope.importFile){
			$scope.uploadFile();
		}
		else{
			$scope.popupMessage = "The template does not exist";
			ngDialog.open({template: 'popup', scope: $scope});
		}
	}

	$scope.uploadFile = function(){
		var fd = new FormData();
		fd.append('file', $scope.importFile);
		fd.append('parameters', JSON.stringify({resourceId: $scope.generator.resource.id}));

		$http.post("/upload/file_upload", fd, {
			transformRequest : angular.identity,
			headers : {'Content-Type' : undefined}}
			)
			.success(function(data, status, headers, config) {
				if(!$scope.generator.resource || !$scope.generator.resource.id){
					$scope.generator.resource.id = data;
				}
				$scope.saveGenerator();
			})
			.error(function(data, status, headers, config) {
				$scope.popupMessage = data.message;
				ngDialog.open({template : 'popup', scope : $scope});
			});
	}

	$scope.uploadText = function(){
		var fd = new FormData();
		fd.append('text', $scope.templateText);
		fd.append('parameters', JSON.stringify({resourceId: $scope.generator.resource.id}));

		$http.post("/upload/text_upload", fd, {
			transformRequest : angular.identity,
			headers : {'Content-Type' : undefined}}
			)
			.success(function(data, status, headers, config) {
				if(!$scope.generator.resource || !$scope.generator.resource.id){
					$scope.generator.resource.id = data;
				}
				$scope.saveGenerator();
			})
			.error(function(data, status, headers, config) {
				$scope.popupMessage = data.message;
				ngDialog.open({template : 'popup', scope : $scope});
			});
	}

	$scope.saveGenerator = function(){
		$http.post('/save/generator', $scope.generator)
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
	
	$scope.remove = function(generator){
        $scope.confirmMessage = "Remove record?";
        ngDialog.openConfirm({
            template: 'confirm_form.html',
            className: 'ngdialog-theme-default custom-width',
            scope: $scope
        }).then(
        	function () {
        		$http.post('/remove/generator', generator)
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
			$scope.show();
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
	
	$scope.boot();
});
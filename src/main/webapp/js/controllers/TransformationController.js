var transformationController = angular.module('cometa');

transformationController.controller('TransformationController', function ($scope, $http, ngDialog, $q) {
	$scope.regexSysname = '[a-zA-Z][\\w]*';
	$scope.show = function(){
		$scope.addView = false;
		$scope.editView = false;
		$scope.tableView = true;
		$scope.transformation={};
		$scope.transformations=[];
		$scope.read();
	}

	$scope.add = function(){
		$scope.addView = true;
		$scope.editView = false;
		$scope.tableView = false;
		$scope.transformation={};
	}
	
	$scope.edit = function(transformation){
		$scope.addView = false;
		$scope.editView = true;
		$scope.tableView = false;
		$scope.transformation=transformation;
		$scope.sourceProtocolChange();
		$scope.targetProtocolChange();
		$scope.sourceReaderChange();
		$scope.targetReaderChange();
		readParams($scope.sourceParameters, JSON.parse($scope.transformation.sourceParameters));
		readParams($scope.targetParameters, JSON.parse($scope.transformation.targetParameters));
	}
	
	$scope.readParams = function(parameters, values){
		for(var i=0;i<parameters.length;i++){
			for(var j=0;j<values.length;j++){
				if(parameters[i].name == values[j].name){
					parameters[i].value = values[j].value;
					break;
				}
			}
		}
	}
	
	$scope.read = function(){
		$http.get('read/transformations')
			.success(function(data, status, headers, config) {
	            $scope.transformations = data;
			})
			.error(function(data, status, headers, config) {
				$scope.popupMessage = data.message;
				ngDialog.open({template: 'popup', scope: $scope});
			});
	}

	$scope.save = function(){
		if(!$scope.transformation.transformingResource){
			$scope.transformation.transformingResource = {};
		}
		if($scope.transformingCodeText){
			$scope.uploadText();
		}
		else if ($scope.importFile){
			$scope.uploadFile();
		}
		else{
			$scope.popupMessage = "The transforming code does not exist";
			ngDialog.open({template: 'popup', scope: $scope});
		}
	}
	
	$scope.createUploadParams = function(){
		return {
			resourceId: $scope.transformation.transformingResource.id,
			resourceFormat: $scope.transformingCodeFormat,
			resourceEncoding: $scope.transformingCodeEncoding,
		};
	}

	$scope.uploadFile = function(){
		var fd = new FormData();
		fd.append('file', $scope.importFile);
		fd.append('parameters', JSON.stringify($scope.createUploadParams()));

		$http.post("upload/file_upload", fd, {
			transformRequest : angular.identity,
			headers : {'Content-Type' : undefined}}
			)
			.success(function(data, status, headers, config) {
				if(!$scope.transformation.transformingResource || !$scope.transformation.transformingResource.id){
					$scope.transformation.transformingResource.id = data;
				}
				$scope.saveTransformation();
			})
			.error(function(data, status, headers, config) {
				$scope.popupMessage = data.message;
				ngDialog.open({template : 'popup', scope : $scope});
			});
	}

	$scope.uploadText = function(){
		var fd = new FormData();
		fd.append('text', $scope.templateText);
		fd.append('parameters', JSON.stringify($scope.createUploadParams()));

		$http.post("upload/text_upload", fd, {
			transformRequest : angular.identity,
			headers : {'Content-Type' : undefined}}
			)
			.success(function(data, status, headers, config) {
				if(!$scope.transformation.transformingResource || !$scope.transformation.transformingResource.id){
					$scope.transformation.transformingResource.id = data;
				}
				$scope.saveTransformation();
			})
			.error(function(data, status, headers, config) {
				$scope.popupMessage = data.message;
				ngDialog.open({template : 'popup', scope : $scope});
			});
	}

	$scope.saveTransformation = function(){
		$scope.transformation.sourceParameters = JSON.stringify($scope.sourceParameters);
		$scope.transformation.targetParameters = JSON.stringify($scope.targetParameters);
		$http.post('save/transformation', $scope.transformation)
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
	

	$scope.remove = function(transformation){
        $scope.confirmMessage = "Remove the transformation?";
        ngDialog.openConfirm({
            template: 'confirm_form.html',
            className: 'ngdialog-theme-default custom-width',
            scope: $scope
        }).then(
        	function () {
        		$http.post('remove/transformation', transformation)
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
		$scope.sourceParameters = [];
		$scope.targetParameters = [];
		$scope.sourceReaders = [];
		$scope.targetWriters = [];
		$scope.transformingCodeFormat = "groovy";
		$scope.transformingCodeEncoding = "UTF-8";
		$http.get('admin/transformation_config')
		.success(function(data, status, headers, config) {
            $scope.protocols = data.protocols;
            $scope.adapters = data.adapters;
            $scope.parameters = data.parameters;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
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

	$scope.openFile = function(){
        var file = $scope.importFile;
        var fileReader = window.FileReader ? new FileReader() : null;

        if (file){
        	if (fileReader){
        		fileReader.addEventListener("loadend", $scope.refreshText, false);
        		fileReader.readAsText(file, $scope.transformingCodeEncoding);
            } 
        }
	}
	
	$scope.refreshText = function(e){
		$scope.transformingCodeText = e.target.result;
	}
	
	$scope.transformingCodeTextChange = function(){
		window.document.getElementById("importFile").value = "";
		$scope.importFile = undefined;
		$scope.transformingCodeEncoding = "UTF-8";
	}
	
	$scope.sourceReaderChange = function(){
		$scope.sourceParameters = $scope.parameters[$scope.transformation.sourceReader];
	}
	
	$scope.targetWriterChange = function(){
		$scope.targetParameters = $scope.parameters[$scope.transformation.targetWriter];
	}
	
	$scope.sourceProtocolChange = function(){
		$scope.sourceReaders = $scope.adapters[$scope.transformation.sourceProtocol];
	}

	$scope.targetProtocolChange = function(){
		$scope.targetWriters = $scope.adapters[$scope.transformation.targetProtocol];
	}
	
	$scope.run = function(){
		$http.post('operation/run_transformation', $scope.transformation)
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

	$scope.boot();
});
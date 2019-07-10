function columnize(input, cols) {
	var arr = [];
	for (var i = 0; i < input.length; i++) {
		var colIdx = i % cols;
		if (colIdx == 0) {
			newArray = [];
			arr.push(newArray);
		}
		newArray.push(input[i]);
	}
	return arr;
}

function prepareParameterValues(parameterValueSet, parameterStructures){
	var oldParameterValues = [];
	
	if(parameterValueSet != null && parameterValueSet.id != null){
		oldParameterValues = parameterValueSet.parameterValues;
	}

	var parameterValues = [];
	for(i=0; i < parameterStructures.length; ++i)
	{
		var parameterValue = {};
		for(j = 0; j < oldParameterValues.length; ++j)
		{
			if(oldParameterValues[j].parameterStructure.id == parameterStructures[i].id)
			{
				parameterValue = oldParameterValues[j];
				break;
			}
		}
		parameterValue.parameterStructure =  parameterStructures[i];
		parameterValues.push(parameterValue);
	}
	return parameterValues;
}

function prepareParameterValuesOutput(parameterValueSet, parameterStructures){
	var oldParameterValues = [];

	if(parameterValueSet != null && parameterValueSet.id != null){
		oldParameterValues = parameterValueSet.parameterValues;
	}

	var parameterValues = [];
	for(i=0; i < parameterStructures.length; ++i)
	{
		var parameterValue = findParameterValueByParameterStructure(oldParameterValues, parameterStructures[i]);
		if(parameterValue != null)
			parameterValues.push(parameterValue);
	}
	return parameterValues;
}

function prepareParameterValuesOutputForSomeObjects(parameterValueSet, parameterStructures){
	var parameterValues = [];
	for(var i=0; i < parameterStructures.length; ++i)
	{
		var parameters = null;
		for(var j=0; j < parameterValueSet.length; ++j)
		{
			var oldParameterValues = [];
			if(parameterValueSet[j] != null && parameterValueSet[j].id != null){
				oldParameterValues = parameterValueSet[j].parameterValues;
			}
			
			var parameterValue = findParameterValueByParameterStructure(oldParameterValues, parameterStructures[i]);
			
			if(parameterValue != null)
			{
				if(parameters == null)
				{
					parameters = {};
					parameters.parameterName = parameterValue.parameterName;
					parameters.sysname = parameterValue.sysname;
					parameters.values = [];
				}
				if(parameters.values.length < j)
				{
					while(parameters.values.length != j)
						parameters.values.push("");
				}
				
				parameters.values.push(parameterValue.value);
			}
		}
		if(parameters != null)
			parameterValues.push(parameters);
	}
	return parameterValues;
}

function findParameterValueByParameterStructure(oldParameterValues, parameterStructure)
{
	/*
	if(parameterStructure.directoryType != null)
	{
		parameterValue = {};
		parameterValue.value = parameterStructure.directoryType.name + " (directory)";
		parameterValue.parameterName = parameterStructure.keyName;
		parameterValue.sysname = parameterStructure.sysname;
		return parameterValue;
	}
	*/
	var parameterValue = null;
	for(var j = 0; j < oldParameterValues.length; ++j)
	{
		if(oldParameterValues[j].parameterStructure.id == parameterStructure.id)
		{
			var oldParameterValue = oldParameterValues[j];
			if(oldParameterValue.valueInt != null)
			{
				parameterValue = {};
				if(parameterStructure.keyType == 'boolean')
				{
					if(oldParameterValue.valueInt == 1)
						parameterValue.value = true;
					if(oldParameterValue.valueInt == 0)
						parameterValue.value = false;
				}
				else if(parameterStructure.keyType == 'code')
				{
					parameterValue.value = mySplit(parameterStructure.valueList)[oldParameterValue.valueInt];
				}
				else
					parameterValue.value = oldParameterValue.valueInt;
			}
			else if(oldParameterValue.valueFloat != null)
			{
				parameterValue = {};
				parameterValue.value = oldParameterValue.valueFloat;
			}
			else if(oldParameterValue.valueString != null)
			{
				parameterValue = {};
				parameterValue.value = oldParameterValue.valueString;
			}
			else if(oldParameterValue.directory != null)
			{
				parameterValue = {};
				parameterValue.value = oldParameterValue.directory.name + " (id=" + oldParameterValue.directory.id+")";
			}
			else
				break;
			parameterValue.parameterName = parameterStructure.keyName;
			parameterValue.sysname = parameterStructure.sysname;
		}
	}
	return parameterValue;
}

function isolateParameterValues(parameterValues){
	for(var i = 0; i < parameterValues.length; i++){
		parameterValues[i].parameterStructure.parameterStructureSet = null;
		parameterValues[i].parameterStructure.directoryType = null;
		parameterValues[i].parameterValueSet = null;
		if(parameterValues[i].parameterStructure.directories != null){
			for(var j = 0; j < parameterValues[i].parameterStructure.directories.length; j++){
				parameterValues[i].parameterStructure.directories[j].directoryType = null;
				parameterValues[i].parameterStructure.directories[j].dirParameterValueSet = null;
			}
		}
	}	
}

function prepareParameterValueSet(parameterValueSet, parameterStructures){
	var result = parameterValueSet;
	
	if(parameterValueSet == null || parameterValueSet.id == null){
		result = {};
	}

	result.parameterValues = prepareParameterValues(parameterValueSet, parameterStructures);
	isolateParameterValues(result.parameterValues);
	return result;
}

function prepareParameterValueSet2($http, parameterValueSet, parameterStructures){
	var result = parameterValueSet;
	
	if(parameterValueSet == null || parameterValueSet.id == null){
		result = {};
	}

	result.parameterValues = prepareParameterValues(parameterValueSet, parameterStructures);
	isolateParameterValues(result.parameterValues);
	return result;
}

function mySplit(string) {
	if(string == null)
		return;
	var array = string.split(';');
	var i;
	var newArray = [];
	for(i = 0; i < array.length; ++i)
		newArray.push(array[i].trim());
	return newArray;
}


function loadLookups($http, $scope, ngDialog, lookups){
	for(var j = 0; j < lookups.length; j++){
		$http.get('/load/' + lookups[j])
			.success(function(data, status, headers, config) {
				$scope[config.url.substr(6)] = data;
			})
			.error(function(data, status, headers, config) {
				$scope.popupMessage = 'Ошибка при загрузки параметров отчета';
				ngDialog.open({template: 'popup',scope: $scope});
			});
	}
}

function prepareParameterValueSetByGroups(parameterValues){
	var result = [];
	
	var parameterValueSetByGroup = {};
	parameterValueSetByGroup.name = "";
	parameterValueSetByGroup.hasRequired = true;
	parameterValueSetByGroup.parameterValues = [];
	result.push(parameterValueSetByGroup);
	for(var i = 0; i < parameterValues.length; ++i)
	{
		var parameterValue = parameterValues[i];
		var parameterStructure = parameterValue.parameterStructure;
		if(parameterStructure.groupName == null)
		{
			result[0].parameterValues.push(parameterValue);
			continue;
		}
		var added = false;
		var required = parameterStructure.required;
		for(var j = 0; j < result.length; ++j)
		{
			parameterValueSetByGroup = result[j];
			if(parameterValueSetByGroup.name == parameterStructure.groupName)
			{
				if(required)
					parameterValueSetByGroup.hasRequired = true;
				parameterValueSetByGroup.parameterValues.push(parameterValue);
				added = true;
				break;
			}
		}
		if(!added)
		{
			parameterValueSetByGroup = {};
			parameterValueSetByGroup.name = parameterStructure.groupName;
			parameterValueSetByGroup.hasRequired = false;
			if(required)
				parameterValueSetByGroup.hasRequired = true;
			parameterValueSetByGroup.parameterValues = [];
			parameterValueSetByGroup.parameterValues.push(parameterValue);
			result.push(parameterValueSetByGroup);
		}
	}
	
	return result;
}

function compareParameterValueByKeyName(pv1, pv2)
{
  if (pv1.parameterStructure.keyName > pv2.parameterStructure.keyName) return 1;
  if (pv1.parameterStructure.keyName < pv2.parameterStructure.keyName) return -1;
}

function number_format_full(number, decimals, dec_point, thousands_sep) {
	number = (number + '').replace(/[^0-9+\-Ee.]/g, '');
	var n = !isFinite(+number) ? 0 : +number, prec = !isFinite(+decimals) ? 0
			: Math.abs(decimals), sep = (typeof thousands_sep === 'undefined') ? ','
			: thousands_sep, dec = (typeof dec_point === 'undefined') ? '.'
			: dec_point, s = '', toFixedFix = function(n, prec) {
		var k = Math.pow(10, prec);
		return '' + (Math.round(n * k) / k).toFixed(prec);
	};
	// Fix for IE parseFloat(0.55).toFixed(0) = 0;
	s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
	if (s[0].length > 3) {
		s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
	}
	if ((s[1] || '').length < prec) {
		s[1] = s[1] || '';
		s[1] += new Array(prec - s[1].length + 1).join('0');
	}
	return s.join(dec);
}

function number_format(number, decimals, dec_point, thousands_sep) {
	if(number == 0) return '0';
	if(!number) return null;
	var s = number_format_full(number, decimals, dec_point, thousands_sep);
	return s.substr(0, s.indexOf(dec_point)) + s.substr(s.indexOf(dec_point)).replace(/0/g, ' ').trim().replace(/ /g, '0');
}

function date_format(d){
	return '' + d.getDate() + '.' + (d.getMonth() + 1) + '.' + d.getFullYear();
}
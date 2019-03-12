$(function(){
	$('#tree').tree({
    	animate:true,
    	checkbox:true,
	});
	
	$('#grid').datagrid({
		url:'emp_list',
		columns:[[
  		    {field:'uuid',title:'编号',width:100},
  		    {field:'name',title:'名称',width:100},    
		 ]],
		 singleSelect:true,
		 onClickRow:function(rowIndex,rowData){
			 $('#tree').tree({
				 url:'emp_readEmpRoles?id='+rowData.uuid,
				 animate:true,
   			     checkbox:true
			 });
		 },
		 
		 
	});
	
	$('#btnSave').bind('click',function(){
		//alert(JSON.stringify($('#tree').tree('getChecked')));
		var nodes = $('#tree').tree('getChecked');
		var ids = new Array();
		$.each(nodes,function(i,node){
			ids.push(node.id)
		});
		//[1,2,3]-->"1,2,3"
		//构建提交的参数
		var formData={};
		formData.id=$('#grid').datagrid('getSelected').uuid;
		var checkedStr=ids.join(',');//数组的每个元素都拼接上逗号
		formData.checkedStr=checkedStr;
		//alert(JSON.stringify(formData));
		$.ajax({
			url:'emp_updateEmpRoles',
			data:formData,
			dataType:'json',
			type:'post',
			success:function(rtn){
				$.messager.alert('提示',rtn.message,'info');
			}
		});
	});
});
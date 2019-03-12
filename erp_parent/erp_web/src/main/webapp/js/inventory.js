$(function(){
	url="inventory_listByPage";
	if(Request['oper']=='doCheck'){
		url+="?t1.state=0";//只查询没有审核的
		document.title="盘盈盘亏审核";
	}
	if(Request['oper']=='myinventory'){
		document.title="盘盈盘亏登记";
	}
	
	$('#grid').datagrid({
		url:url,
		singleSelect: true,
		pagination: true,
		columns:[[
  		    {field:'uuid',title:'编号',width:100},
  		    {field:'storename',title:'仓库',width:100},
  		    {field:'goodsname',title:'商品',width:100},
  		    {field:'num',title:'数量',width:100},
  		    {field:'type',title:'类型',width:100,formatter:getType},
  		    {field:'createtime',title:'登记日期',width:100,formatter:formatDate},
  		    {field:'checktime',title:'审核日期',width:100,formatter:formatDate},
  		    {field:'creatername',title:'登记人',width:100},
  		    {field:'checkername',title:'审核人',width:100},
  		    {field:'state',title:'状态',width:100,formatter:getState},
  		    {field:'remark',title:'备注',width:100},
		     ]],


	});
	

	//盘盈盘亏登记新增
	if(Request['oper']=='myinventory'){
		$('#grid').datagrid({
		     toolbar: [{
					text: '盘盈盘亏登记',
					iconCls: 'icon-add',
					handler: function(){
						//设置保存按钮提交的方法为add
						method = "add";
						//关闭编辑窗口
						document.getElementById("editForm").reset();
						$('#editDlg').dialog('open');
					}
				}],
		});
	}
	
	$('#editDlg').dialog({
		title: '盘盈盘亏登记',//窗口标题
		width: 300,//窗口宽度
		height: 300,//窗口高度
		closed: true,//窗口是是否为关闭状态, true：表示关闭
		modal: true//模式窗口
	});
	
	$('#btnSave').bind('click',function(){
		var formData = $('#editForm').serializeJSON();
	//	alert(JSON.stringify(formData));
		$.ajax({
			url: 'inventory_add',
			data: formData,
			dataType: 'json',
			type: 'post',
			success:function(rtn){
				$.messager.alert("提示",rtn.message,'info',function(){
					//成功的话，我们要关闭窗口
					$('#editDlg').dialog('close');
					//刷新表格数据
					$('#grid').datagrid('reload');
				});
			}
		});
	});
	
	//点击查询按钮
	$('#btnSearch').bind('click',function(){
		//把表单数据转换成json对象
		var formData = $('#searchForm').serializeJSON();
		$('#grid').datagrid('load',formData);
	});
	
	$('#checkDlg').dialog({
		title:'盘盈盘亏审核',
		closed:true,
		modal:true,
		width:300,
		height:270,
		buttons:[{
			text:'审核',
			iconCls:'icon-save',
			handler:doCheck
		}]
	});
	
	if(Request['oper']=='doCheck'){
		$('#grid').datagrid({
			onDblClickRow:function(rowIndex,rowData){
				//alert(JSON.stringify(rowData));
				if(rowData.state*1==1){
					$.messager.alert('提示','该订单已经审核过，请不要再次审核','info');
					return ;
				}
				$('#uuid').prop('readonly',true);
				$('#uuid').val(rowData.uuid);
				$('#createtime').val(rowData.createtime);
				$('#goodsname').val(rowData.goodsname);
				$('#storename').val(rowData.storename);
				$('#storeuuid').val(rowData.storeuuid);
				$('#num').val(rowData.num);
				$('#type').val(getType(rowData.type));
				$('#remark').val(rowData.remark);
				$('#checkDlg').dialog('open');
			}
		});
	}
})

/**
 * 盘盈还是盘亏
 * @returns
 */
function getType(value){
	if(value*1==0){
		return "盘亏";
	}
	if(value*1==1){
		return "盘盈";
	}
}


function getState(value){
	if(value*1==0){
		return "未审核";
	}
	if(value*1==1){
		return "审核";
	}
	if(value*1==2){
		return "完成";
	}
}
/**
 * 日期格式化器
 * @param value
 * @returns
 */
function formatDate(value){
	return new Date(value).Format("yyyy-MM-dd");
}


function doCheck(){
	$.messager.confirm('提示','确认审核吗？',function(yes){
		if(yes){
			$.ajax({
				url:'inventory_doCheck?id='+$('#uuid').val(),
				type:'post',
				dataType:'json',
				success:function(rtn){
					$.messager.alert('提示',rtn.message,'info',function(){
						if(rtn.success){
							$('#checkDlg').dialog('close');
							$('#grid').datagrid('reload');
						}
					});
				}
			});
		}
	});
}
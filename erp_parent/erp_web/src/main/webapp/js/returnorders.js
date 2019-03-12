$(function() {
	var url = 'returnorders_listByPage';
	var btnText = "";
	var inOutLine = "";
	if (Request['oper'] == 'myreturnorders') {
		if (Request['type'] * 1 == 2) {// 销售退货
			url = 'returnorders_myListByPage?t1.type=2';
			document.title = '我的销售退货订单查询';
			btnText = "新增退货申请";
			$('#addOrderSupplier').html('客户');
		}
		if (Request['type'] * 1 == 1) {// 采购退货
			url = 'returnorders_myListByPage?t1.type=1';
			document.title = '我的采购退货订单查询';
			btnText = "新增采购退货申请";
			$('#addOrderSupplier').html('供应商');
		}
	}
	if (Request['oper'] == 'returnorders') {
		if (Request['type'] * 1 == 2) {// 销售
			url += '?t1.type=2';
			document.title = '销售退货订单查询';
		}
		if (Request['type'] * 1 == 1) {// 采购退货
			url += '?t1.type=1';
			document.title = '采购退货订单查询';
		}

	}

	if (Request['oper'] == 'doCheck') {
		if (Request['type'] * 1 == 1) {
			url += "?t1.type=1&t1.state=0";
			document.title = '采购订单退货审核';
		}
		if (Request['type'] * 1 == 2) {
			url += "?t1.type=2&t1.state=0";
			document.title = '销售订单退货审核';
		}

	}
	// 采购退货订单出库
	if (Request['oper'] == 'doOutStore') {
		url += "?t1.type=1&t1.state=1";
		document.title = '采购订单退货出库';
		inOutLine = "出库";

	}
	if (Request['oper'] == 'doInStore') {
		url += "?t1.type=2&t1.state=1";
		document.title = '销售订单退货入库';
		inOutLine = "入库";
	}

	$('#grid').datagrid({
		url : url,
		columns : [ [ {
			field : 'uuid',
			title : '编号',
			width : 100
		}, {
			field : 'createtime',
			title : '录入日期',
			width : 100,
			formatter : formatDate
		}, {
			field : 'checktime',
			title : '审核日期',
			width : 100,
			formatter : formatDate
		}, {
			field : 'endtime',
			title : '出库日期',
			width : 100,
			formatter : formatDate
		}, {
			field : 'creatername',
			title : '下单员',
			width : 100
		}, {
			field : 'checkername',
			title : '审核员工',
			width : 100
		}, {
			field : 'endername',
			title : '库管员',
			width : 100
		}, {
			field : 'suppliername',
			title : '供应商',
			width : 100
		}, {
			field : 'totalmoney',
			title : '总金额',
			width : 100
		}, {
			field : 'state',
			title : '订单状态',
			width : 100,
			formatter : getState
		}, {
			field : 'waybillsn',
			title : '运单号',
			width : 100
		}, ] ],
		singleSelect : true,
		pagination : true,
		fitColumns : true,
		onDblClickRow : function(rowIndex, rowData) {
			// 显示详情
			// alert(JSON.stringify(rowData));
			$('#uuid').html(rowData.uuid);
			$('#suppliername').html(rowData.suppliername);
			$('#state').html(getState(rowData.state));
			$('#creater').html(rowData.creatername);
			$('#checker').html(rowData.checkername);
			$('#ender').html(rowData.endername);
			$('#createtime').html(formatDate(rowData.createtime));
			$('#checktime').html(formatDate(rowData.checktime));
			$('#endtime').html(formatDate(rowData.endtime));
			// 打开窗口
			// alert(JSON.stringify(rowData));
			$('#ordersDlg').dialog('open');

			$('#itemgrid').datagrid('loadData', rowData.returnorderDetails);
		},
	});

	// 我的
	if (Request['oper'] == 'myreturnorders') {
		$('#grid').datagrid({
			toolbar : [ {
				text : btnText,
				iconCls : 'icon-add',
				handler : function() {
					$('#addOrdersDlg').dialog('open');
				}
			} ],
		});
	}
	// 出入库窗口
	$('#itemDlg').dialog({
		title : inOutLine,
		width : 300,
		height : 200,
		modal : true,
		closed : true,
		buttons : [ {
			text : inOutLine,
			iconCls : 'icon-save',
			handler : doInOutStore
		} ]

	});
	// 明细列表
	$('#itemgrid').datagrid({
		columns : [ [ {
			field : 'uuid',
			title : '编号',
			width : 100
		}, {
			field : 'goodsuuid',
			title : '商品编号',
			width : 100
		}, {
			field : 'goodsname',
			title : '商品名称',
			width : 100
		}, {
			field : 'price',
			title : '价格',
			width : 100
		}, {
			field : 'num',
			title : '数量',
			width : 100
		}, {
			field : 'money',
			title : '金额',
			width : 100
		}, {
			field : 'state',
			title : '状态',
			width : 100,
			formatter : getDetailState
		}, ] ],
		singleSelect : true,
		fitColumns : true,
	});

	if (Request['oper'] == 'doOutStore' || Request['oper'] == 'doInStore') {
		$('#itemgrid').datagrid({
			onDblClickRow : function(rowIndex, rowData) {
				$('#itemuuid').val(rowData.uuid);
				$('#goodsuuid').html(rowData.goodsuuid);
				$('#goodsname').html(rowData.goodsname);
				$('#goodsnum').html(rowData.num);
				// 打开出入库窗口
				$('#itemDlg').dialog('open');
			}
		});
	}
	// 添加退货申请窗口
	$('#addOrdersDlg').dialog({
		title : '增加采购退货',
		width : 700,
		height : 400,
		modal : true,
		closed : true,
	});

	// 审核退货
	if (Request['oper'] == 'doCheck') {
		$('#ordersDlg').dialog({
			toolbar : [ {
				text : '审核',
				iconCls : 'icon-search',
				handler : doCheck
			} ],
		});
	}
})

/**
 * 过去订单状态
 * 
 * @param value
 *            type:0：采购退货 type:1:销售退货
 * @returns
 */
function getState(value) {
	if (Request['type'] * 1 == 1) {
		switch (value * 1) {
		case 0:
			return "未审核";
		case 1:
			return "已审核";
		case 2:
			return "已出库";
		default:
			return "";
		}
	}
	if (Request['type'] * 1 == 2) {
		switch (value * 1) {
		case 0:
			return "未审核";
		case 1:
			return "已审核";
		case 2:
			return "已入库";
		default:
			return "";
		}
	}
}
/**
 * 订单明细的状态
 * 
 * @param value
 * @returns
 */
function getDetailState(value) {
	if (Request['type'] * 1 == 1) {// 采购出库
		switch (value * 1) {
		case 0:
			return "未出库";
		case 1:
			return "已出库";

		default:
			return "";
		}
	}
	if (Request['type'] * 1 == 2) {
		switch (value * 1) {
		case 0:
			return "未入库";
		case 1:
			return "已入库";

		default:
			return "";
		}
	}
}
function formatDate(value) {
	return new Date(value).Format("yyyy-MM-dd");
}

function doCheck() {
	// 确认是否审核
	$.messager.confirm('提示', '确认审核吗？', function(yes) {
		if (yes) {
			$.ajax({
				url : 'returnorders_doCheck()?id=' + $('#uuid').html(),
				dataType : 'json',
				type : 'post',
				success : function(rtn) {
					$.messager.alert('提示', rtn.message, 'info', function() {
						if (rtn.success) {
							// 关闭窗口
							$('#ordersDlg').dialog('close');
							// 刷新表格
							$('#grid').datagrid('reload');
						}
					});
				}
			});
		}
	})
}

function doInOutStore() {
	var formData = $('#itemForm').serializeJSON();
	// alert(JSON.stringify(formData));
	if (formData.storeuuid == '') {
		$.messgaer.alert('提示', '未选择仓库', 'info');
		return;
	}
	var message = "";
	var url = "";
	if (Request['oper'] == 'doOutStore') {
		message = "是否采购退货？";
		url = "returnorderdetail_doOutStore";
	}
	if (Request['oper'] == 'doInStore') {
		message = "是否销售退货?";
		url = "returnorderdetail_doInStore";
	}
	$.messager.confirm('确认', message, function(yes) {
		if (yes) {
			$.ajax({
				url : url,
				data : formData,
				dataType : 'json',
				type : 'post',
				success : function(rtn) {
					$.messager.alert('提示', rtn.message, 'info', function() {
						if (rtn.success) {
							// 关闭窗口
							$('#itemDlg').dialog('close');
							// 设置选中的条目的状态为：已经出库
							$('#itemgrid').datagrid('getSelected').state = "1";
							var data = $('#itemgrid').datagrid('getData');
							$('#itemgrid').datagrid('loadData', data);

							var allIn = true;
							// 判断是否所有明细都出库了
							$.each(data.rows, function(i, row) {
								if (row.state * 1 == 0) {
									allIn = false;
									return;
								}
							});
							if (allIn) {
								$('#ordersDlg').dialog('close');
								$('#grid').datagrid('reload');
							}

						}
					});
				}
			});
		}
	});

}
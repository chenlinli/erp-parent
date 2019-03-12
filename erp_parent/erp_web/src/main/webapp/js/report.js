$(function(){
	//加载表格数据
	$('#grid').datagrid({
		url:'report_ordersReport',
		columns:[[
			{field:'name',title:'商品类型',width:100},
			{field:'y',title:'销售额',width:100}
		]],
		singleSelect: true,
		onLoadSuccess:function(data){
			//显示图
			showChart(data.rows);
		}
	});

	//点击查询按钮
	$('#btnSearch').bind('click',function(){
		//把表单数据转换成json对象
		var formData = $('#searchForm').serializeJSON();
		//alert(JSON.stringify(formData));
		if(formData.endDate){//有输入就是true
			formData.endDate+=" 23:59:59";
		}
		$('#grid').datagrid('load',formData);
	});
	
});

function showChart(_data){
	Highcharts.chart('pieChart', {
	    chart: {
	        plotBackgroundColor: null,
	        plotBorderWidth: null,
	        plotShadow: false,
	        type: 'pie'
	    },
	    //授权
	    credits:{enabled:false},
	    //导出
	    exporting:{enabled:true},
	    title: {
	        text: '销售统计'
	    },
	    tooltip: {
	        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	    },
	    plotOptions: {
	        pie: {
	            allowPointSelect: true,
	            cursor: 'pointer',
	            dataLabels: {
	                enabled: true,
	                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
	                style: {
	                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
	                }
	            }
	        }
	    },
	    series: [{
	        name: '比例',
	        colorByPoint: true,
	        data: _data
	    }]
	});
}
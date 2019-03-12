$(function(){
	//加载表格数据
	$('#grid').datagrid({
		queryParams:{},//查询条件
		columns:[[
			{field:'name',title:'月份',width:100},
			{field:'y',title:'销售额',width:100}
		]],
		singleSelect: true,
		onLoadSuccess:function(data){
			//显示图
			showChart();
		}
	});

	//点击查询按钮
	$('#btnSearch').bind('click',function(){
		//把表单数据转换成json对象
		var formData = $('#searchForm').serializeJSON();
		
		//$('#grid').datagrid('load',formData);
		//点击查询的时候，配置url和参数
		$('#grid').datagrid({
			url:'report_tendReport',
			queryParams:formData
			
		});
	});
	
	
});

function showChart(){
	var months = new Array();
	for(var i =1;i<=12;i++){
		months.push(i+"月");
	}
	Highcharts.chart('trendChart', {
	    chart: {
	        type: 'line'
	    },
	    title: {
	    	 text: $('#year').combobox('getValue')+"年销售趋势分析"
	    },
	    subtitle: {
	        text: 'Source: WorldClimate.com'
	    },
	    xAxis: {
	        categories: months
	    },
	    yAxis: {
	        title: {
	            text: '销售额'
	        }
	    },
	    plotOptions: {
	        line: {
	            dataLabels: {
	                enabled: true
	            },
	            enableMouseTracking: false
	        }
	    },
	    series: [{
	        name: '销售额',
	        data: $('#grid').datagrid('getRows')
	    }],
	    legend: {
	        layout: 'vertical',
	        align: 'center',
	        verticalAlign: 'bottom'
	    },
	    tooltip:{valueSuffix:'元'},
	});
}

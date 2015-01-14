Ext.namespace('com.zz91.zzwork.hr.attendance');

com.zz91.zzwork.hr.attendance.AttendanceField=[
	{name:"id",mapping:"id"},
	{name:"name",mapping:"name"},
	{name:"code",mapping:"code"},
	{name:"account",mapping:"account"},
	{name:"gmt_work",mapping:"gmtWork"},
	{name:"gmt_created",mapping:"gmtCreated"},
	{name:"gmt_modeified",mapping:"gmtModeified"},
	{name:"scheduleId",mapping:"scheduleId"}
];

var SCHEDULE={};

com.zz91.zzwork.hr.attendance.MainGrid=Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var al=config.al||true;
		
		var _store=new Ext.data.JsonStore({
			root:"records",
			totalProperty:"totalRecords",
			remoteSort:true,
			fields:com.zz91.zzwork.hr.attendance.AttendanceField,
			url:Context.ROOT+"/hr/attendance/query.htm",
			autoLoad:al
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel();
		
		var _cm=new Ext.grid.ColumnModel([_sm,{
			header:"编号",
			width:30,
			sortable:true,
			dataIndex:"id",
			hidden:true
		},{
			header:"登记号码",
			sortable:false,
			dataIndex:"code"
		},{
			header:"姓名",
			sortable:false,
			dataIndex:"name"
		},{
			header:"系统账号",
			sortable:false,
			dataIndex:"account"
		},{
			header:"班次",
			sortable:false,
			dataIndex:"scheduleId",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				return SCHEDULE["s"+value];
			}	
		},{
			header:"出勤时间",
			sortable:false,
			dataIndex:"gmt_work",
			width:150,
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d H:i:s');
				}
				else{
					return "";
				}
			}	
		}
		]);
		
		var c={
			store:_store,
			sm:_sm,
			cm:_cm,
			loadMask:MESSAGE.loadmask,
			tbar:[
				{
					text:"考勤统计",
					iconCls:"play16",
					handler:function(){
						window.open(Context.ROOT+"/hr/attendance/analysis.htm");
					}
				},"->",{
					xtype:"datefield",
					id:"from",
					format:"Y-m-d H:i:s",
					emptyText:"开始时间",
					listeners:{
						"blur":function(field){
							var B = _store.baseParams;
							B = B||{};
							if(field.getValue()!=""){
								B["from"] = Ext.util.Format.date(field.getValue(), 'Y-m-d 00:00:00');
							}else{
								B["from"]=null;
							}
							_store.baseParams = B;
							_store.reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
						}
					}
				},{
					xtype:"datefield",
					id:"to",
					format:"Y-m-d H:i:s",
					emptyText:"结束时间",
					listeners:{
						"blur":function(field){
							var B = _store.baseParams;
							B = B||{};
							if(field.getValue()!=""){
								B["to"] =  Ext.util.Format.date(field.getValue(), 'Y-m-d 00:00:00');
							}else{
								B["to"]=null;
							}
							_store.baseParams = B;
							_store.reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
						}
					}
				},{
					xtype:"textfield",
					id:"code",
					emptyText:"按登记号查询",
					listeners:{
						"blur":function(field){
							var B = _store.baseParams;
							B = B||{};
							if(field.getValue()!=""){
								B["code"] = field.getValue();
							}else{
								B["code"]=null;
							}
							_store.baseParams = B;
							_store.reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
						}
					}
				},{
					xtype:"textfield",
					id:"name",
					emptyText:"按姓名查询",
					listeners:{
						"blur":function(field){
							var B = _store.baseParams;
							B = B||{};
							if(field.getValue()!=""){
								B["name"] = field.getValue();
							}else{
								B["name"]=null;
							}
							_store.baseParams = B;
							_store.reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
						}
					}
				}
			],
			bbar: new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: MESSAGE.paging.displayMsg,
				emptyMsg : MESSAGE.paging.emptyMsg,
				beforePageText : MESSAGE.paging.beforePageText,
				afterPageText : MESSAGE.paging.afterPageText,
				paramNames : MESSAGE.paging.paramNames
			})
		}
		
		com.zz91.zzwork.hr.attendance.MainGrid.superclass.constructor.call(this, c);
	}
});
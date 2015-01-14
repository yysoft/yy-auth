Ext.namespace('com.zz91.zzwork.hr.attendance.analysis');

var SCHEDULE={};

com.zz91.zzwork.hr.attendance.analysis.Field=[
	{name:"id",mapping:"id"},
	{name:"name",mapping:"name"},
	{name:"code",mapping:"code"},
	{name:"account",mapping:"account"},
	{name:"day_full",mapping:"dayFull"},
	{name:"day_actual",mapping:"dayActual"},
	{name:"day_leave",mapping:"dayLeave"},
	{name:"day_other",mapping:"dayOther"},
	{name:"day_unwork",mapping:"dayUnwork"},
	{name:"day_unrecord",mapping:"dayUnrecord"},
	{name:"day_late",mapping:"dayLate"},
	{name:"day_early",mapping:"dayEarly"},
	{name:"day_overtime",mapping:"dayOvertime"},
	{name:"gmt_target",mapping:"gmtTarget"},
	{name:"scheduleId",mapping:"scheduleId"}
];

com.zz91.zzwork.hr.attendance.analysis.BaseGrid=Ext.extend(Ext.grid.GridPanel,{
	enableEdit:true,
	autoStoreLoad:true,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store=new Ext.data.JsonStore({
			root:"records",
			totalProperty:"totalRecords",
			remoteSort:true,
			fields:com.zz91.zzwork.hr.attendance.analysis.Field,
			url:Context.ROOT+"/hr/analysis/query.htm",
			autoLoad:this.autoStoreLoad
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
			header:"班次",
			sortable:false,
			dataIndex:"scheduleId",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				return SCHEDULE["s"+value];
			}	
		},{
			header:"姓名",
			sortable:false,
			dataIndex:"name",
			renderer:function(value, metadata, record, rowIndex,colIndex, store){
				var from=Ext.util.Format.date(new Date(record.get("gmt_target").time), 'Y-m-01 00:00:00');
				return "<a href='"+Context.ROOT+"/hr/attendance/viewAttendance.htm?scheduleId="+record.get("scheduleId")+"&code="+record.get("code")+"&targetMonth="+from+"' target='_blank'>"+value+"</a>";
			}
		},{
			header:"系统账号",
			sortable:false,
			dataIndex:"account"
		},{
			header:"统计时间",
			sortable:false,
			dataIndex:"gmt_target",
			width:150,
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y年m月');
				}
				else{
					return "";
				}
			}
		},{
			header:"应出勤",
			dataIndex:"day_full",
			editor:{
				xtype:"numberfield",
				decimalPrecision:3
			}
		},{
			header:"实际出勤",
			dataIndex:"day_actual",
			editor:{
				decimalPrecision:3,
				xtype:"numberfield"
			}
		},{
			header:"请假",
			dataIndex:"day_leave",
			editor:{
				decimalPrecision:3,
				xtype:"numberfield"
			}
		},{
			header:"其他",
			dataIndex:"day_other",
			editor:{
				decimalPrecision:3,
				xtype:"numberfield"
			}
		},{
			header:"旷工",
			dataIndex:"day_unwork",
			editor:{
				decimalPrecision:3,
				xtype:"numberfield"
			}
		},{
			header:"未打卡",
			dataIndex:"day_unrecord",
			editor:{
				allowDecimals:false,
				xtype:"numberfield"
			}
		},{
			header:"迟到",
			dataIndex:"day_late",
			editor:{
				allowDecimals:false,
				xtype:"numberfield"
			}
		},{
			header:"早退",
			dataIndex:"day_early",
			editor:{
				allowDecimals:false,
				xtype:"numberfield"
			}
		},{
			header:"加班",
			dataIndex:"day_overtime",
			editor:{
				allowDecimals:false,
				xtype:"numberfield"
			}
		}
		]);
		
		var plugins=[];
		if(this.enableEdit){
			var editor = new Ext.ux.grid.RowEditor({
				saveText:"更新",
				cancelText:"取消",
				commitChangesText:"你需要先保存或取消当前的变更",
				errorText:"错误",
				listeners:{
					"afteredit":function(edit, changes, r, rowIdx){
						Ext.Ajax.request({
					        url:Context.ROOT+"/hr/analysis/update.htm",
					        params:{
					        	"id":r.get("id"),
					        	"dayFull":r.get("day_full"),
					        	"dayActual":r.get("day_actual"),
					        	"dayLeave":r.get("day_leave"),
					        	"dayOther":r.get("day_other"),
					        	"dayUnwork":r.get("day_unwork"),
					        	"dayUnrecord":r.get("day_unrecord"),
					        	"dayLate":r.get("day_late"),
					        	"dayEarly":r.get("day_early"),
					        	"dayOvertime":r.get("day_overtime")
					        },
					        success:function(response,opt){
					            var obj = Ext.decode(response.responseText);
					            if(obj.success){
					                com.zz91.zzwork.utils.Msg(MESSAGE.picTitleInfo,MESSAGE.saveSuccess);
					            }else{
					                Ext.MessageBox.show({
					                    title:MESSAGE.title,
					                    msg : MESSAGE.saveFailure,
					                    buttons:Ext.MessageBox.OK,
					                    icon:Ext.MessageBox.ERROR
					                });
					            }
					        },
					        failure:function(response,opt){
					            Ext.MessageBox.show({
					                title:MESSAGE.title,
					                msg : MESSAGE.submitFailure,
					                buttons:Ext.MessageBox.OK,
					                icon:Ext.MessageBox.ERROR
					            });
					        }
					    });
					}
				}
			});
			plugins=[editor];
		}
		
		var c={
			store:_store,
			sm:_sm,
			cm:_cm,
			loadMask:MESSAGE.loadmask,
			plugins:plugins,
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
		
		com.zz91.zzwork.hr.attendance.analysis.BaseGrid.superclass.constructor.call(this, c);
	}
});

com.zz91.zzwork.hr.attendance.analysis.MainGrid=Ext.extend(com.zz91.zzwork.hr.attendance.analysis.BaseGrid,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _this=this;
		
		var c={
			tbar:[{
				text:"导出",
				iconCls:"saveas16",
				handler:function(){
					if(Ext.getCmp("gmtTarget").getValue()==""){
						Ext.MessageBox.alert(MESSAGE.title, "请先筛选出指定月份的数据，一次只能导出一个月的数据");
						return false;
					}
					var month = Ext.util.Format.date(Ext.getCmp("gmtTarget").getValue(), 'Y-m-01 00:00:00')
					window.open(Context.ROOT+"/hr/analysis/export.htm?month="+month);
				}
			},"->",{
					xtype:"datefield",
					id:"gmtTarget",
					format:"Y年m月",
					emptyText:"统计时间",
					listeners:{
						"blur":function(field){
							var _store=_this.getStore();
							var B = _store.baseParams;
							B = B||{};
							if(field.getValue()!=""){
								B["gmtTarget"] = Ext.util.Format.date(field.getValue(), 'Y-m-01 00:00:00');
							}else{
								B["gmtTarget"]=null;
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
							var _store=_this.getStore();
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
							var _store=_this.getStore();
							var B = _store.baseParams;
							B = B||{};
							if(field.getValue()!=""){
								B["name"] = field.getValue();
							}else{
								B["name"]=null;
							}
							_store.baseParams = B;
							_store.reload({params:{"start":0, "limit":Context.PAGE_SIZE}});s
						}
					}
				}
			]
		}
		
		com.zz91.zzwork.hr.attendance.analysis.MainGrid.superclass.constructor.call(this, c);
	}
});

com.zz91.zzwork.hr.attendance.analysis.ViewGrid=Ext.extend(com.zz91.zzwork.hr.attendance.analysis.BaseGrid,{
	enableEdit:true,
	autoStoreLoad:true,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			enableEdit:false,
			autoStoreLoad:false
		}
		
		com.zz91.zzwork.hr.attendance.analysis.ViewGrid.superclass.constructor.call(this, c);
	}
});
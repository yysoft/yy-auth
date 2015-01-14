Ext.namespace("com.zz91.zzwork.feedback");

com.zz91.zzwork.feedback.StatusMap=["未解决","已解决","不解决","无法解决"];

com.zz91.zzwork.feedback.DtoFields=[
	{name:"id",mapping:"id"},
	{name:"bs_id",mapping:"bsId"},
	{name:"account",mapping:"account"},
	{name:"topic",mapping:"topic"},
	{name:"content",mapping:"content"},
	{name:"status",mapping:"status"},
	{name:"gmt_created",mapping:"gmtCreated"},
	{name:"gmt_modified",mapping:"gmtModified"}
	];

com.zz91.zzwork.feedback.Grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store=new Ext.data.JsonStore({
			root:"records",
			totalProperty:"totalRecords",
			remoteSort:true,
			fields:com.zz91.zzwork.feedback.DtoFields,
			url:Context.ROOT+"/feedback/queryFeedback.htm",
			autoLoad:true
		});
		
		var grid=this;
		
		var _sm=new Ext.grid.CheckboxSelectionModel();
		
		var _cm=new Ext.grid.ColumnModel([_sm,{
			header:"编号",
			width:30,
			sortable:true,
			dataIndex:"id",
			hidden:true
		},{
			header:"员工账号",
			sortable:false,
			dataIndex:"account"
		},{
			header:"反馈问题",
			sortable:false,
			dataIndex:"topic"
		},{//显示信息包括：姓名，性别，头像
			header:"处理状态",
			sortable:false,
			dataIndex:"status",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				return com.zz91.zzwork.feedback.StatusMap[value];
			}
		}]);
		
		var c={
			store:_store,
			sm:_sm,
			cm:_cm,
			loadMask:MESSAGE.loadmask,
			tbar:[{
				iconCls:"delete16",
				text:"删除",
				handler:function(btn){
					var rows = grid.getSelectionModel().getSelects();
					if(rows.length>0){
						//TODO 删除
					}
				}
			},"-",{
				text:"解决掉",
				handler:function(btn){
					//TODO 
				}
			},{
				text:"不解决",
				handler:function(btn){
					//TODO
				}
			},{
				text:"不可能",
				handler:function(btn){
					//TODO
				}
			}
			,"->",{
				xtype:"combo",
				lazyInit:true,
				mode:"local",
				triggerAction:"all",
				store:[
				       [0,"Open"],
				       [1,"Fixed"],
				       [2,"Won't Fix"],
				       [3,"Closed"]],
				id:"feedbackStatus",
				listeners:{
					"blur":function(field){
						if(Ext.get("feedbackStatus").dom.value==""){
							field.setValue("");
						}
						var B=_store.baseParams;
						B["status"]=field.getValue();
						_store.reload({params:{"startIndex":0, "pageSize":Context.PAGE_SIZE}});
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
		};
		
		com.zz91.zzwork.feedback.Grid.superclass.constructor.call(this, c);
	}
});

com.zz91.zzwork.feedback.Form = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		
		var form=this;
		
		var c={
			labelAlign:"right",
			labelWidth:80,
			layout:"form",
			defaults:{
				anchor:"99%",
				xtype:"textfield",
				labelSeparator:""
			},
			frame:true,
			items:[{
				fieldLabel:"提交人",
				name:"account"
			},{
				fieldLabel:"主要问题",
				name:"topic"
			},{
				xtype:"textarea",
				fieldLabel:"详细描述",
				height:500,
				name:"content"
			}]
		};
		
		com.zz91.zzwork.feedback.Form.superclass.constructor.call(this,c);
	},
	loadRecord:function(record){
		this.getForm().loadRecord(record);
	}
});

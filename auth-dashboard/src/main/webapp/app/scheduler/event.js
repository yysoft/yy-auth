Ext.namespace("com.zz91.zzwork.scheduler.event");

com.zz91.zzwork.scheduler.report.Grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:com.zz91.zzwork.scheduler.report.Field,
			//url:Context.ROOT +  "/scheduler/report/queryBs.htm",
			autoLoad:true
		});
		
		var grid = this;
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([{
			header : "标题",
			width : 350,
			sortable : false,
			dataIndex : "title"
		},{
			header : "年",
			width : 150,
			sortable : false,
			dataIndex : "year"
		},{
			header : "周",
			width : 150,
			sortable : false,
			dataIndex : "week"
		},{
			xtype:"textfield",
			name:"summary",
			fieldLabel:"总结",
			itemCls:"required",
			allowBlank:false
		}]);
		
		var c={
			loadMask:MESSAGE.loadmask,
			iconCls:"icon-grid",
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:this.mytoolbar,
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
		
		com.zz91.zzwork.scheduler.report.Grid.superclass.constructor.call(this, c);
	}
});

com.zz91.zzwork.scheduler.report.Form = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			region:"center",
			layout:"column",
			bodyStyle:'padding:5px 0 0',
			frame:true,
			labelAlign : "right",
			labelWidth : 80,
			items:[{
				fieldLabel : "标题",
				allowBlank : false,
				itemCls :"required",
				name : "title",
				id : "tile"
			},{
				xtype:"combo",
				allowBlank : false,
				itemCls :"required",
				fieldLabel:"时间",
				name:"typesStr",
				hiddenName:"types",
				mode:"local",
				triggerAction:"all",
				lazyRender:true,
				store:new Ext.data.ArrayStore({
				fields:["k","v"],
				data:[[0,"年"],[1,"周"]]
			}),
				valueField:"k",
				displayField:"v"
			},{
				xtype:"textfield",
				fieldLabel : "总结",
				allowBlank : false,
				itemCls :"required",
				name : "summary",
				id : "summary"
			}],
			buttons:[{
				scope:this,
				handler:this.saveForm
			}]
		};
		
		com.zz91.zzwork.scheduler.report.Form.EventGrid.superclass.constructor.call(this,c);

	}
});

com.zz91.zzwork.scheduler.event.EventGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:true,
			fields:com.zz91.zzwork.scheduler.report.Field,
			//url:Context.ROOT +  "/scheduler/event.htm",
			autoLoad:true
	});
		
		var grid = this;
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel([{
			xtype : "button",
			text:"删除",
			scope:this,
			handler:this.saveForm
		},{
			xtype : "radio",
		 	boxLabel:"周报"
		 	
		},{
			xtype : "radio",
	 		boxLabel:"日报"
	 	
		},_sm,{
			header : "编号",
			width : 250,
			sortable : false,
			dataIndex : "code"
		},{
			header : "日程",
			sortable : false,
			dataIndex : "schedule" 
		},{
			header : "完成情况",
			sortable : false,
			dataIndex : "event" 
		},{
			header :"重要程度",
			sortable : false,
			width : 200,
			dataIndex:"importance",
			renderer:function(value, metaData, record, rowIndex, colIndex, store){
				var url=record.get("url");
				if(url!=null && typeof(url)!="undefined" && url.length>0){
					return "<a href='"+url+"' target='_blank' >"+value+"</a>" ;
				}
				return value;
			}
		}
		]);
		
		var c={
			loadMask:MESSAGE.loadmask,
			iconCls:"icon-grid",
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:this.mytoolbar,
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
		
		com.zz91.zzwork.scheduler.report.EventGrid.superclass.constructor.call(this,c);
		
	}
});
com.zz91.zzwork.scheduler.event.Form = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			region:"center",
			layout:"form",
			bodyStyle:'padding:5px 0 0',
			frame:true,
			labelAlign : "right",
			labelWidth : 80,
			buttons:[{
				text : "删除",
				scope:this,
				handler:this.saveForm
			}],
			items:[{
				columnWidth:.5,
				xtype:"radio",
				boxLabel:"周报",
				name:"report"
				},{
				columnWidth:.5,
				checked:true,
				xtype:"radio",
				boxLabel:"日报",
				name:"report"
				},{
				fieldLabel : "编号",
				allowBlank : false,
				itemCls :"required",
				name : "code",
				id : "code"
				},{
				fieldLabel : "日程",
				allowBlank : false,
				itemCls :"required",
				name : "schedule"
				},{
				fieldLabel : "完成情况",
				allowBlank : false,
				itemCls :"required",
				name : "event"
				},{
				fieldLabel : "重要程度",
				allowBlank : false,
				itemCls :"required",
				name : "importance"
			}]
		};
		
		com.zz91.zzwork.scheduler.event.Form.EventGrid.superclass.constructor.call(this,c);
		
	}
});

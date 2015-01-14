Ext.namespace("com.zz91.zzwork.auth.right");

var RIGHT=new function(){
	this.EDITWIN="righteditwin";
	this.TREE="righttree";
}

com.zz91.zzwork.auth.right.AddFormWin = function(){
    var form = new com.zz91.zzwork.auth.right.FormPanel();
    form.saveUrl = Context.ROOT+"/auth/right/createRight.htm";
    form.initParentForAdd();
    
    var win = new Ext.Window({
            id:RIGHT.EDITWIN,
            title:"添加权限信息",
            width:380,
            autoHeight:true,
            modal:true,
            items:[form]
    });
    win.show();
}

com.zz91.zzwork.auth.right.EditFormWin = function(){
    var tree = Ext.getCmp(RIGHT.TREE);
    var node = tree.getSelectionModel().getSelectedNode();
    if(tree.getRootNode() == node){
            return false;
    }
    var form = new com.zz91.zzwork.auth.right.FormPanel();
    form.saveUrl = Context.ROOT+"/auth/right/updateRight.htm";
    form.initParentForUpdate();
    form.loadRight();
    var win = new Ext.Window({
            id:RIGHT.EDITWIN,
            title:"修改权限信息",
            width:380,
            autoHeight:true,
            modal:true,
            items:[form]
    });
    win.show();
}

com.zz91.zzwork.auth.right.FormPanel=Ext.extend(Ext.form.FormPanel,{
    constructor:function(config){
	    config = config || {};
	    Ext.apply(this,config);
	    
	    var c = {
            labelAlign : "right",
            labelWidth : 80,
            region:"center",
            layout:"form",
            bodyStyle:'padding:5px 0 0',
            frame:true,
            defaults:{
                    anchor:"95%",
                    xtype:"textfield",
                    labelSeparator:""
            },
            items:[{
                xtype:"hidden",
                id:"id",
                name:"id"
            },{
                xtype:"hidden",
                id:"parentCode",
                name:"parentCode"
            },{
                id:"parent",
                readOnly:true,
                fieldLabel:"上级权限"
            },{
                id:"code",
                name:"code",
                readOnly:true,
                fieldLabel:"权限code"
            },{
                id:"name",
                name:"name",
                fieldLabel:"权限名",
                itemCls:"required",
                allowBlank:false
            },{
                xtype:"textarea",
                id:"content",
                name:"content",
                fieldLabel:"规则"
            },{
            	id:"sort",
            	name:"sort",
            	fieldLabel:"排序"
            },{
                id:"menu",
                name:"menu",
                fieldLabel:"菜单名"
            },{
                id:"menuUrl",
                name:"menuUrl",
                fieldLabel:"菜单连接"
            },{
                id:"menuCss",
                name:"menuCss",
                fieldLabel:"菜单样式"
            }
            ],
            buttons:[{
                text:"保存",
                handler:this.saveForm,
                scope:this
            },{
                text:"关闭",
                handler:function(){
                    Ext.getCmp(RIGHT.EDITWIN).close();
                }
            }]
	    };
	    com.zz91.zzwork.auth.right.FormPanel.superclass.constructor.call(this,c);
	},
	initParentForAdd:function(){
	    var tree = Ext.getCmp(RIGHT.TREE);
	    var node = tree.getSelectionModel().getSelectedNode();
	    this.findById("parentCode").setValue(node.attributes.data);
	    this.findById("parent").setValue(node.text);
	},
	initParentForUpdate:function(){
	    var tree = Ext.getCmp(RIGHT.TREE);
	    var node = tree.getSelectionModel().getSelectedNode();
	    this.findById("parent").setValue(node.parentNode.text);
	},
	saveUrl:Context.ROOT+"/auth/right/createRight.htm",
	saveForm:function(btn){
	    if(this.getForm().isValid()){
            this.getForm().submit({
                url:this.saveUrl,
                method:"post",
                type:"json",
                success:this.onSaveSuccess,
                failure:this.onSaveFailure,
                scope:this
            });
	    }else{
            Ext.MessageBox.show({
                title:MESSAGE.title,
                msg : MESSAGE.unValidate,
                buttons:Ext.MessageBox.OK,
                icon:Ext.MessageBox.ERROR
            });
	    }
	},
	onSaveSuccess:function(_form,_action){
	    var tree = Ext.getCmp(RIGHT.TREE);
	    var node = tree.getSelectionModel().getSelectedNode();
	    if(this.findById("id").getValue() > 0){
            node.setText(this.findById("name").getValue()); 
	    }else{
            node.leaf= false;
            tree.getLoader().load(node,function(){
                node.expand();
            });
	    }
	    Ext.getCmp(RIGHT.EDITWIN).close();
	},
	onSaveFailure:function(_form,_action){
	    Ext.MessageBox.show({
            title:MESSAGE.title,
            msg : MESSAGE.saveFailure,
            buttons:Ext.MessageBox.OK,
            icon:Ext.MessageBox.ERROR
	    });
	},
	loadRight:function(){
	    var tree = Ext.getCmp(RIGHT.TREE);
	    var node = tree.getSelectionModel().getSelectedNode();
	    
	    var _fields = ["id","name","code","content","menu","menuUrl","menuCss","sort"];
	    var form = this;
	    var _store = new Ext.data.JsonStore({
            root : "records",
            fields : _fields,
            url : Context.ROOT + "/auth/right/queryOneRight.htm?code="+node.attributes.data,
            autoLoad : true,
            listeners : {
                "datachanged" : function() {
                    var record = _store.getAt(0);
                    if (record == null) {
                        Ext.MessageBox.alert(MESSAGE.title,MESSAGE.loadError);
                        Ext.getCmp(RIGHT.EDITWIN).close();
                    } else {
                        form.getForm().loadRecord(record);
                    }
                }
            }
	    });
	    
	}

}); 
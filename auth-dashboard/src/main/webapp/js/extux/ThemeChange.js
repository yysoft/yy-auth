Ext.ux.ThemeChange = Ext.extend(Ext.form.ComboBox,{  
    editable : false,  
    displayField : 'theme',  
    valueField : 'css',  
    typeAhead : true,  
    mode : 'local',  
    value : '默认',  
    readonly : true,  
    triggerAction : 'all',  
    selectOnFocus : true,  
    initComponent : function(){  
        var themes = [  
                ['默认', 'ext-all.css'],  
                ['浅灰色', 'xtheme-gray.css'],
                ['蓝色', 'xtheme-blue.css'],  
                ['Access', 'xtheme-access.css'],  
                
                ['黑色', 'xtheme-black.css'],  
                ['巧克力色', 'xtheme-chocolate.css'],  
                ['深灰色', 'xtheme-darkgray.css'],  
                ['橄榄色', 'xtheme-olive.css'],  
                ['椒盐色', 'xtheme-peppermint.css'],  
                ['粉色', 'xtheme-pink.css'],  
                ['紫色', 'xtheme-purple.css'],  
                ['暗蓝色', 'xtheme-slate.css'],  
                ['靛青色', 'xtheme-indigo.css'],  
                ['深夜', 'xtheme-midnight.css'],  
                ['银白色', 'xtheme-silverCherry.css']  
        ];  
        this.store = new Ext.data.SimpleStore({  
            fields : ['theme', 'css'],  
            data : themes  
        });  
    },  
    initEvents : function(){  
        this.on('collapse', function(){  
            //实际改变风格样式的处理  
            Ext.util.CSS.swapStyleSheet('theme', Context.ROOT+'/js/ext32/resources/css/'+ this.getValue());  
        });  
    }  
});  
Ext.reg('xthemeChange', Ext.ux.ThemeChange); 
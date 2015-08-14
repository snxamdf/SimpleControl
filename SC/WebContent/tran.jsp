<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>tran事务测试</title>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="/SC/bootstrap/css/bootstrap.min.css">
<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<link rel="stylesheet" href="/SC/bootstrap/css/bootstrap-theme.min.css">
<script type="text/javascript" src="/SC/js/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="/SC/bootstrap/js/bootstrap.min.js"></script>

<script type="text/javascript">
	$(function() {
		var json = ${msg};
		$.each(json,function(i,o){
			//document.write((o._id.$oid)+"  "+(o.uid)+"  "+(o.uname)+"  "+(o.age)+"<br/>");
		});
	});
</script>
<body>
<button class="btn btn-primary"  data-toggle="modal" data-target="#mymodal_id" type="button">点击我</button>
<div class="modal fade" id="mymodal_id">
    <div class="modal-dialog">
        <div class="modal-content">
        	<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span>
					<span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">模态弹出窗标题</h4>
			</div>
			<div class="modal-body">
				<p>模态弹出窗主体内容</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary">保存</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<button class="btn btn-primary" data-toggle="modal" data-target="#mymodal-data" type="button">通过data-target触发</button>
<!-- 模态弹出窗内容 -->
<div class="modal" id="mymodal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
    <div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				<h4 class="modal-title">模态弹出窗标题</h4>
			</div>
			<div class="modal-body">
				<p>模态弹出窗主体内容</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary">保存</button>
			</div>
		</div>
	</div>
</div>

<h3>大/小尺寸模态弹出框</h3>
<!-- Large modal -->
<button class="btn btn-primary" data-toggle="modal" data-target=".bs-example-modal-lg">
大的模态弹出窗
</button>

<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span>
					<span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">模态弹出窗标题</h4>
			</div>
			<div class="modal-body">
				<p>模态弹出窗主体内容</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary">保存</button>
			</div>
		</div>
	</div>
</div>

<!-- Small modal -->
<button class="btn btn-primary" data-toggle="modal" data-target=".bs-example-modal-sm">
小的模态弹出窗
</button>

<div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				<h4 class="modal-title">模态弹出窗标题</h4>
			</div>
			<div class="modal-body">
				<p>模态弹出窗主体内容</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary">保存</button>
			</div>
		</div>
	</div>
</div>
<br/>
------------------------------------------------------------------------------------------------
<h3>示例1</h3>
<div class="navbar navbar-default">
    <a href="##" class="navbar-brand">W3cplus</a>
    <ul class="nav navbar-nav">
    	<li class="dropdown">
			<a href="##" data-toggle="dropdown" class="dropdown-toggle" role="button" id="tutorial">教程<b class="caret"></b></a>
			<ul class="dropdown-menu" role="menu" aria-labelledby="tutorial">
				<li role="presentation"><a href="##">CSS3</a></li>
				<li role="presentation"><a href="##">HTML5</a></li>
				<li role="presentation"><a href="##">Sass</a></li>
			</ul>
		</li>
		<li><a href="##">前端论坛</a></li>
		<li class="dropdown">
			<a href="##" data-toggle="dropdown" class="dropdown-toggle" role="button">关于我们<b class="caret"></b></a>
			<ul class="dropdown-menu" role="menu" aria-labelledby="tutorial">
				<li role="presentation"><a href="##">测试12123</a></li>
				<li role="presentation"><a href="##">测试2123</a></li>
				<li role="presentation"><a href="##">Sass</a></li>
			</ul>
		</li>
	</ul>
</div>

<h3>示例2</h3>
<ul class="nav nav-pills">
	<li class="dropdown">
		<a href="##" data-toggle="dropdown" class="dropdown-toggle" role="button" id="tutorial">教程<b class="caret"></b></a>
		<ul class="dropdown-menu" role="menu" aria-labelledby="tutorial">
			<li role="presentation"><a href="##">CSS3</a></li>
			<li role="presentation"><a href="##">HTML5</a></li>
			<li role="presentation"><a href="##">Sass</a></li>
		</ul>
	</li>
	<li class="active"><a href="##">前端论坛</a></li>
	<li class="dropdown">
		<a href="##" data-toggle="dropdown" class="dropdown-toggle" role="button" id="guanyuwomen">关于我们<b class="caret"></b></a>
		<ul class="dropdown-menu" role="menu" aria-labelledby="tutorial">
			<li role="presentation"><a href="##">测试1</a></li>
			<li role="presentation"><a href="##">测试2</a></li>
			<li role="presentation"><a href="##">Sass</a></li>
		</ul>
	</li>
</ul>
------------------------------------------------------------------------------------------------
<!-- 选项卡组件（菜单项nav-tabs）-->
<ul id="myTab" class="nav nav-tabs" role="tablist">
    <li class="active"><a href="#bulletin" role="tab" >公告</a></li>
    <li><a href="#rule" role="tab" >规则</a></li>
    <li><a href="#forum" role="tab" >论坛</a></li>
	<li><a href="#security" role="tab" >安全</a></li>
	<li><a href="#welfare" role="tab" >公益</a></li>
</ul>
<!-- 选项卡面板 -->
<div id="myTabContent" class="tab-content">
	<div class="tab-pane active" id="bulletin">公告内容面板
		<!-- 选项卡组件（菜单项nav-tabs）-->
		<ul id="myTab2" class="nav nav-tabs" role="tablist">
		    <li  class="active"><a href="#yile" role="tab-pane" data-toggle="tab">娱乐</a></li>
		    <li><a href="#fangchan" role="tab-pane" data-toggle="tab">房产</a></li>
		    <li><a href="#guonei" role="tab-pane" data-toggle="tab">国内</a></li>
			<li><a href="#guowai" role="tab-pane" data-toggle="tab">国外</a></li>	
		</ul>
		<!-- 选项卡面板 -->
		<div id="myTabContent2" class="tab-content">
			<div class="tab-pane active" id="yile">娱乐内容面板</div>
			<div class="tab-pane" id="fangchan">房产内容面板</div>
			<div class="tab-pane" id="guonei">国内内容面板</div>
			<div class="tab-pane" id="guowai">国外内容面板</div>	
		</div>
	</div>
	<div class="tab-pane" id="rule">规则内容面板
		<!-- 选项卡组件（菜单项nav-tabs）-->
		<ul id="myTab2" class="nav nav-tabs" role="tablist">
		    <li  class="active"><a href="#yile" role="tab-pane" data-toggle="tab">娱乐</a></li>
		    <li><a href="#fangchan" role="tab-pane" data-toggle="tab">房产</a></li>
		    <li><a href="#guonei" role="tab-pane" data-toggle="tab">国内</a></li>
			<li><a href="#guowai" role="tab-pane" data-toggle="tab">国外</a></li>	
		</ul>
		<!-- 选项卡面板 -->
		<div id="myTabContent2" class="tab-content">
			<div class="tab-pane active" id="yile">娱乐内容面板</div>
			<div class="tab-pane" id="fangchan">房产内容面板</div>
			<div class="tab-pane" id="guonei">国内内容面板</div>
			<div class="tab-pane" id="guowai">国外内容面板</div>	
		</div>
	</div>
	<div class="tab-pane" id="forum">论坛内容面板</div>
	<div class="tab-pane" id="security">安全内容面板</div>
	<div class="tab-pane" id="welfare">公益内容面板</div>
</div>
------------------------------------------------------------------------------------------------
<h3>按钮做的提示框</h3>
  <button type="button" 
          class="btn btn-default" 
          data-toggle="tooltip" 
          data-placement="left" 
          data-original-title="提示框居左" 
          title="">
  提示框居左      
  </button>
  <button type="button" 
          class="btn btn-default" 
          data-toggle="tooltip" 
          data-placement="top" 
          data-original-title="提示框在顶部">
   提示框在顶部
  </button>
  <button type="button" 
          class="btn btn-default" 
          data-toggle="tooltip" 
          data-placement="bottom" 
          data-original-title="提示框在底部">
  提示框在底部             
  </button>
  <button type="button" 
          class="btn btn-default" 
          data-toggle="tooltip" 
          data-placement="right" 
          data-original-title="提示框居右">
  提示框居右      
  </button>
  <button type="button" id="popover"
          class="btn btn-default"
          data-toggle="popover"
          title="上"
          data-content="我是弹出框的内容">
          猛击我吧
  </button>
  <h3>链接制作的提示框</h3>
  <a class="btn btn-primary" 
     data-toggle="tooltip" 
     data-placement="left" 
     title="提示框居左">
     提示框居左
  </a>
  <a class="btn btn-primary" 
     data-toggle="tooltip" 
     data-placement="top" 
     title="提示框在顶部">
     提示框在顶部
  </a>
  <a class="btn btn-primary" 
     data-toggle="tooltip" 
     data-placement="bottom" 
     title="提示框在底部">
     提示框在底部
  </a>
  <a class="btn btn-primary" 
     data-toggle="tooltip" 
     data-placement="right" 
     title="提示框在居右">
     提示框居右
  </a>
  <a href="##" 
     class="btn btn-primary" 
     id="myTooltip">
     我是提示框
  </a>
  <a href="##" 
     class="btn btn-primary" 
     id="myTooltip2">
     我是提示框2
  </a>
  <br/>
------------------------------------------------------------------------------------------------
<h3>示例</h3>
<div class="alert alert-success" role="alert">
    <button class="close" data-dismiss="alert" type="button" >&times;</button>
    <p>恭喜您操作成功！</p>
</div>
<div class="alert alert-warning" role="alert" id="myAlert">
    <h4>谨防被骗</h4>
    <p>请确认您转账的信息是你的亲朋好友，不要轻意相信不认识的人...</p>
	<button type="button" data-target="#myAlert" class="btn btn-danger" data-dismiss="alert">关闭</button>
</div>

<h3>使用&times;作为关闭按</h3>
<div class="alert alert-success" role="alert">
    <button class="close" type="button" data-dismiss="alert">&times;</button>
    <p>恭喜您操作成功！</p>
</div>
<h3>使用a标签作为关闭按钮</h3>
<div class="alert alert-warning" role="alert">
	<h4>谨防被骗</h4>
	<p>请确认您转账的信息是你的亲朋好友，不要轻意相信不认识的人...</p>
	<a href="#" class="btn btn-danger" data-dismiss="alert">关闭</a>
</div>
<h3>使用button按钮作为关闭按钮</h3>
<div class="alert alert-warning" role="alert">
	<h4>谨防被骗</h4>
	<p>请确认您转账的信息是你的亲朋好友，不要轻意相信不认识的人...</p>
	<button type="button"class="btn btn-danger" data-dismiss="alert">关闭</button>
</div>

<div class="alert alert-warning" role="alert" id="myAlert">
    <h4>谨防被骗</h4>
    <p>请确认您转账的信息是你的亲朋好友，不要轻意相信不认识的人...</p>
    <button type="button"  class="btnbtn-danger" id="close">关闭</button>
</div>
<button class="btnbtn-primary" data-loading-text="正在加载中,请稍等..." type="button" id="loaddingBtn">加载</button>
<div class="btn-group">
    <label class="btn btn-primary">
        <input type="radio" name="options" id="options1">男
    </label>
    <label class="btn btn-primary">
        <input type="radio" name="options" id="options2">女
    </label>
    <label class="btn btn-primary">
        <input type="radio" name="options" id="options3">未知
    </label>
</div>
</body>
<script type="text/javascript">
	$(function(){
		$("#loaddingBtn").click(function () {
	        $(this).button("loading");
	      });
		$("#close").on("click",function(){
	        $(this).alert("close");
	    });
		$("#myTab a").click(function(e){
	        e.preventDefault();
	        $(this).tab("show");
	    });
		$('[data-toggle="tooltip"]').tooltip();
	    $('#myTooltip').tooltip({
	      title:"我是一个提示框，我在顶部出现",
	      placement:'top'
	    });
	    $('#myTooltip2').tooltip({
	      title:"我是一个提示框我是一个提示框我是一个提示框",
	      placement:'top'
	    });
	    $('#popover').popover({
		      placement:'top'
	    });
	})
</script>
</html>

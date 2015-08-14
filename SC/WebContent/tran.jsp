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
<style type="text/css">
    /* Custom Styles */
    ul.nav-tabs{
        width: 140px;
        margin-top: 20px;
        border-radius: 4px;
        border: 1px solid #ddd;
        box-shadow: 0 1px 4px rgba(0, 0, 0, 0.067);
    }
    ul.nav-tabs li{
        margin: 0;
        border-top: 1px solid #ddd;
    }
    ul.nav-tabs li:first-child{
        border-top: none;
    }
    ul.nav-tabs li a{
        margin: 0;
        padding: 8px 16px;
        border-radius: 0;
    }
    ul.nav-tabs li.active a, ul.nav-tabs li.active a:hover{
        color: #fff;
        background: #0088cc;
        border: 1px solid #0088cc;
    }
    ul.nav-tabs li:first-child a{
        border-radius: 4px 4px 0 0;
    }
    ul.nav-tabs li:last-child a{
        border-radius: 0 0 4px 4px;
    }
    ul.nav-tabs.affix{
        top: 30px; /* Set the top position of pinned element */
    }
</style>

<script type="text/javascript">
	$(function() {
		var json = ${msg};
		$.each(json,function(i,o){
			//document.write((o._id.$oid)+"  "+(o.uid)+"  "+(o.uname)+"  "+(o.age)+"<br/>");
		});
	});
</script>
<body  data-spy="scroll" data-target="#myScrollspy">
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
------------------------------------------------------------------------------------------------

<div class="panel-group" id="accordion">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">标题一</a></h4>
        </div>
        <div id="collapseOne" class="panel-collapse collapse in">
            <div class="panel-body">标题一对应的内容</div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">标题二</a></h4>
        </div>
        <div id="collapseTwo" class="panel-collapse collapse">
            <div class="panel-body">标题二对应的内容</div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title"><a data-toggle="collapse"data-parent="#accordion"href="#collapseThree">标题三</a></h4>
        </div>
        <div id="collapseThree" class="panel-collapse collapse">
            <div class="panel-body">标题三对应的内容</div>
        </div>
    </div>
</div>
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
<div id="myCarousel" class="carousel slide">
    <ol class="carousel-indicators">
       <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
       <li data-target="#myCarousel" data-slide-to="1"></li>
       <li data-target="#myCarousel" data-slide-to="2"></li>
    </ol>
    <div class="carousel-inner">
        <div class="item active">
            <img src="http://images3.c-ctrip.com/rk/201407/ll580x145.jpg" alt="">
            <div class="carousel-caption">
              <h4>标题一</h4>
              <p>图片一内容简介</p>
            </div>
        </div>
        <div class="item">
            <img src="http://images3.c-ctrip.com/dj/201408/zj/zj_580145.jpg" alt="">
            <div class="carousel-caption">
               <h4>标题二</h4>
               <p>图片二内容简介</p>
            </div>
        </div>
        <div class="item">
        	<img src="http://images3.c-ctrip.com/rk/201403/yfdd580145a.png" alt="">
            <div class="carousel-caption">
              <h4>标题三</h4>
              <p>图片三内容简介</p>
            </div>
        </div>
  </div>
    <a class="left carousel-control" href="#myCarousel" data-slide="prev">&lsaquo;</a>
    <a class="right carousel-control" href="#myCarousel" data-slide="next">&rsaquo;</a>
</div>
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
<div class="container">
    <div class="jumbotron">
        <h1>Bootstrap Affix</h1>
    </div>
    <div class="row">
        <div class="col-xs-3" id="myScrollspy">
            <ul class="nav nav-tabs nav-stacked" data-spy="affix" data-offset-top="125">
                <li class="active"><a href="#section-1">第一部分</a></li>
                <li><a href="#section-2">第二部分</a></li>
                <li><a href="#section-3">第三部分</a></li>
                <li><a href="#section-4">第四部分</a></li>
                <li><a href="#section-5">第五部分</a></li>
            </ul>
        </div>
        <div class="col-xs-9">
            <h2 id="section-1">第一部分</h2>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eu sem tempor, varius quam at, luctus dui. Mauris magna metus, dapibus nec turpis vel, semper malesuada ante. Vestibulum id metus ac nisl bibendum scelerisque non non purus. Suspendisse varius nibh non aliquet sagittis. In tincidunt orci sit amet elementum vestibulum. Vivamus fermentum in arcu in aliquam. Quisque aliquam porta odio in fringilla. Vivamus nisl leo, blandit at bibendum eu, tristique eget risus. Integer aliquet quam ut elit suscipit, id interdum neque porttitor. Integer faucibus ligula.</p>
           	<hr>
            <h2 id="section-2">第二部分</h2>
            <p>Nullam hendrerit justo non leo aliquet imperdiet. Etiam in sagittis lectus. Suspendisse ultrices placerat accumsan. Mauris quis dapibus orci. In dapibus velit blandit pharetra tincidunt. Quisque non sapien nec lacus condimentum facilisis ut iaculis enim. Sed viverra interdum bibendum. Donec ac sollicitudin dolor. Sed fringilla vitae lacus at rutrum. Phasellus congue vestibulum ligula sed consequat.</p>
           	<hr>
            <h2 id="section-3">第三部分</h2>
            <p>Integer pulvinar leo id risus pellentesque vestibulum. Sed diam libero, sodales eget sapien vel, porttitor bibendum enim. Donec sed nibh vitae lorem porttitor blandit in nec ante. Pellentesque vitae metus ipsum. Phasellus sed nunc ac sem malesuada condimentum. Etiam in aliquam lectus. Nam vel sapien diam. Donec pharetra id arcu eget blandit. Proin imperdiet mattis augue in porttitor. Quisque tempus enim id lobortis feugiat. Suspendisse tincidunt risus quis dolor fringilla blandit. Ut sed sapien at purus lacinia porttitor. Nullam iaculis, felis a pretium ornare, dolor nisl semper tortor, vel sagittis lacus est consequat eros. Sed id pretium nisl. Curabitur dolor nisl, laoreet vitae aliquam id, tincidunt sit amet mauris.</p>
           	<hr>
            <h2 id="section-4">第四部分</h2>
            <p>Ut ut risus nisl. Fusce porttitor eros at magna luctus, non congue nulla eleifend. Aenean porttitor feugiat dolor sit amet facilisis. Pellentesque venenatis magna et risus commodo, a commodo turpis gravida. Nam mollis massa dapibus urna aliquet, quis iaculis elit sodales. Sed eget ornare orci, eu malesuada justo. Nunc lacus augue, dictum quis dui id, lacinia congue quam. Nulla sem sem, aliquam nec dolor ac, tempus convallis nunc. Interdum et malesuada fames ac ante ipsum primis in faucibus. Nulla suscipit convallis iaculis. Quisque eget commodo ligula. Praesent leo dui, facilisis quis eleifend in, aliquet vitae nunc. Suspendisse fermentum odio ac massa ultricies pellentesque. Fusce eu suscipit massa.</p>
            <hr>
            <h2 id="section-5">第五部分</h2>
            <p>Sed vitae lobortis diam, id molestie magna. Aliquam consequat ipsum quis est dictum ultrices. Aenean nibh velit, fringilla in diam id, blandit hendrerit lacus. Donec vehicula rutrum tellus eget fermentum. Pellentesque ac erat et arcu ornare tincidunt. Aliquam erat volutpat. Vivamus lobortis urna quis gravida semper. In condimentum, est a faucibus luctus, mi dolor cursus mi, id vehicula arcu risus a nibh. Pellentesque blandit sapien lacus, vel vehicula nunc feugiat sit amet.</p>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
	$(function(){
		$('.carousel').carousel();
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

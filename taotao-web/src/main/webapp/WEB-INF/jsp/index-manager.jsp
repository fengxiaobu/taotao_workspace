<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div>
	<a class="easyui-linkbutton" onclick="importitems()">一键导入商品索引到数据库</a>
</div>
<div id="itemmsg" style="display: none">
	<h3 style="color: gren;">请稍等........</h3>
</div>
</body>
</html>


<script type="text/javascript">
	function importitems() {
		$("#itemmsg").show();
		$.post("/item/import", null, function() {
			$("#itemmsg").hide();
			$.messager.alert("提示", "商品数据导入完成!");
		});
	}
</script>
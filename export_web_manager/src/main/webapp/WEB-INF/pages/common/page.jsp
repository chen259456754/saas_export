<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<body>
<div class="pull-left">
    <div class="form-group form-inline">
        总共${pageInfo.pages} 页，共${pageInfo.total} 条数据。
        <label for="pageChange">每页显示</label><select id="pageChange" onchange="selectChange()">
        <option value="5" ${pageInfo.pageSize ==10?'selected="selected"':''}>5</option>
        <option value="10" ${pageInfo.pageSize ==10?'selected="selected"':''}>10</option>
        <option value="15" ${pageInfo.pageSize ==15?'selected="selected"':''}>15</option>
        <option value="20" ${pageInfo.pageSize ==20?'selected="selected"':''}>20</option>
    </select><label for="pageChange">条</label>
    </div>
</div>

<div class="box-tools pull-right">
    <ul class="pagination" style="margin: 0px;">
        <li>
            <a href="javascript:goPage(1)" aria-label="Previous">首页</a>
        </li>
        <li><a href="javascript:goPage(${pageInfo.prePage})">上一页</a></li>
        <c:forEach begin="1" end="${pageInfo.pages}" var="i">
            <li class="paginate_button ${pageInfo.pageNum==i ? 'active':''}"><a href="javascript:goPage(${i})">${i}</a>
            </li>
        </c:forEach>
        <li><a href="javascript:goPage(${pageInfo.nextPage})">下一页</a></li>
        <li>
            <a href="javascript:goPage(${pageInfo.pages})" aria-label="Next">尾页</a>
        </li>
    </ul>
</div>
<%--${param}中的param是EL提供的内置对象，用于获取请求参数值--%>
<%--${param.pageUrl}相当于request.getAttribute（“pageUrl”）--%>
<form id="pageForm" action="${param.pageUrl}" method="post">
    <input type="hidden" name="pageNum" id="pageNum">
    <input type="hidden" name="pageSize" id="pageSize">
</form>
<script>
    function goPage(pageNum) {
        //修改请求参数
        document.getElementById("pageNum").value = pageNum
        document.getElementById("pageForm").submit()
    }

    function selectChange() {
        let pageSizes = $('select option:selected').val();
        document.getElementById("pageSize").value = pageSizes;
        document.getElementById("pageForm").submit()
    }
</script>
</body>
</html>

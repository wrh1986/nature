<%@ include file="../commonHeader.jsp" %>

<!--main content start-->
<section class="panel">
    <header class="panel-heading">
        <s:if test="product.id.length() > 0">
            修改产品
        </s:if>
        <s:else>
            添加产品
        </s:else>
    </header>
    <div class="panel-body">
        <s:actionerror/><s:actionmessage/>
        <form id="product" class="form-horizontal tasi-form" action="${rootPath}/backend/product/save.action"
              method="post">
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">产品名称</label>

                <div class="col-lg-10">
                    <input name="product.id" type="hidden" value="${product.id}"/>
                    <input type="text" placeholder="产品名称" name="product.name" class="form-control"
                           autofocus="autofocus"
                           required="required" value="${product.name}"
                           <s:if test="product.id != null">readonly="readonly"</s:if>
                            />
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">编码</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="编码" name="product.code" class="form-control"
                              required="required" value="${product.code}">
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">分类</label>

                <div class="col-lg-10">
                    <s:select name="product.productLevelId" list="listProductLevel" listKey="id" listValue="name"
                              value="%{product.productLevelId}" cssClass="form-control"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">价格(元)</label>

                <div class="col-lg-10">
                    <input type="text" placeholder="价格(元)" class="form-control" name="product.price"
                          value="${product.price}"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">状态</label>

                <div class="col-lg-10">
                    <s:select cssClass="form-control" name="product.state" list="#{'0':'上架', '1':'下架'}"
                              value="product.state"/>
                </div>
            </div>
            <div class="form-group has-success">
                <label class="col-lg-2 control-label">摘要</label>

                <div class="col-lg-10">
                    <textarea placeholder="摘要" name="product.summary" class="form-control"
                              required="required" rows="20">${product.summary}</textarea>
                </div>
            </div>

            <div class="form-group">
                <div class="col-lg-offset-2 col-lg-10">
                    <button class="btn btn-info" type="submit">保存</button>
                    <button class="btn btn-info" type="button"
                            onclick="window.location.href='${rootPath}/backend/product/index.action'">取消
                    </button>
                </div>
            </div>
        </form>
    </div>
</section>
<script type="text/javascript">
</script>

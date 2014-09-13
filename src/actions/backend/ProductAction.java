package actions.backend;

import bl.beans.ProductBean;
import bl.beans.ProductLevelBean;
import bl.beans.SourceCodeBean;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.ProductBusiness;
import bl.mongobus.ProductLevelBusiness;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;

import java.util.List;

/**
 * @author pli
 * @since $Date:2014-09-13$
 */
public class ProductAction extends BaseBackendAction<ProductBusiness> {
    private static final long serialVersionUID = -5222876000116738224L;
    private static Logger LOG = LoggerFactory.getLogger(ProductAction.class);
    protected final static ProductLevelBusiness PLS = (ProductLevelBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_PRODUCTLEVEL);

    private ProductBean product;

    private List<ProductLevelBean> listProductLevel;

    public List<ProductLevelBean> getListProductLevel() {
        return listProductLevel;
    }

    public void setListProductLevel(List<ProductLevelBean> listProductLevel) {
        this.listProductLevel = listProductLevel;
    }

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    @Override
    public String getActionPrex() {
        return getRequest().getContextPath() + "/backend/product";
    }

    @Override
    public TableInitVo getTableInit() {
        TableInitVo init = new TableInitVo();
        init.getAoColumns().add(new TableHeaderVo("name", "产品名称").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("code", "编码").enableSearch());
        List<ProductLevelBean> productLevelBeans = (List<ProductLevelBean>) PLS.getAllLeaves().getResponseData();
        String[][] productLevelCodes = new String[2][productLevelBeans.size()];
        if (productLevelBeans.size() > 0) {
            for (int i = 0; i < productLevelBeans.size(); i++) {
                productLevelCodes[0][i] = productLevelBeans.get(i).getId();
                productLevelCodes[1][i] = productLevelBeans.get(i).getName();
            }
        } else {
            productLevelCodes = null;
        }
        init.getAoColumns().add(new TableHeaderVo("productLevelId", "分类").addSearchOptions(productLevelCodes).enableSearch());
        init.getAoColumns().add(new TableHeaderVo("state", "状态").enableSearch().addSearchOptions(new String[][]{{"0", "1"}, {"上架", "下架"}}));
        init.getAoColumns().add(new TableHeaderVo("price", "价格(元)", true));

        return init;
    }

    @Override
    public String getTableTitle() {
        return "<ul class=\"breadcrumb\"><li>产品管理</li><li class=\"active\">产品发布</li></ul>";
    }

    @Override
    public String save() throws Exception {
        if (StringUtils.isBlank(product.getId())) {
            ProductBean userTmp = (ProductBean) getBusiness().getLeafByName(product.getName()).getResponseData();
            if (userTmp != null) {
                addActionError("产品存在");
                return FAILURE;
            } else {
                product.set_id(ObjectId.get());
                getBusiness().createLeaf(product);
            }
        } else {
            ProductBean origProductLevel = (ProductBean) getBusiness().getLeaf(product.getId().toString()).getResponseData();
            ProductBean newProductLevel = (ProductBean) origProductLevel.clone();
            BeanUtils.copyProperties(newProductLevel, product);
            getBusiness().updateLeaf(origProductLevel, newProductLevel);
        }
        return SUCCESS;
    }

    @Override
    public String add() throws Exception {
        listProductLevel = (List<ProductLevelBean>) PLS.getAllLeaves().getResponseData();
        return SUCCESS;
    }

    @Override
    public String edit() throws Exception {
        listProductLevel = (List<ProductLevelBean>) PLS.getAllLeaves().getResponseData();
        product = (ProductBean) getBusiness().getLeaf(getId()).getResponseData();
        return SUCCESS;
    }


}
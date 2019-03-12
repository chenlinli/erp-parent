package com.cl.erp.realm;

import java.util.List;

import javax.swing.JEditorPane;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.cl.erp.biz.IEmpBiz;
import com.cl.erp.entity.Emp;
import com.cl.erp.entity.Menu;

import redis.clients.jedis.Jedis;

public class ErpRealm extends AuthorizingRealm{

	private IEmpBiz empBiz;
	

	public IEmpBiz getEmpBiz() {
		return empBiz;
	}

	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("执行授权");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		/*info.addStringPermission("部门");
		info.addStringPermission("采购订单的查询");
		info.addStringPermission("采购订单的审核");*/
		//获取当前登陆用户的菜单权限
		Emp emp = (Emp) principals.getPrimaryPrincipal();
		//尝试从缓存里取出menus，Jedis不支持对象的存储，只支持字符串，第一次存入缓存的时候，转成字符串
		List<Menu> menus = empBiz.getMenusByEmpuuid(emp.getUuid());
		//使用menuname作为授权的key，配置spring的授权访问的url等号右边perms["值"]。值是菜单名称
		for (Menu menu : menus) {
			info.addStringPermission(menu.getMenuname());
		}
		return info;
	}

	/**
	 * 认证
	 * return null:认证失败，AuthenticationInfo：成功
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//执行授权
		System.out.println("执行认证");
		UsernamePasswordToken upt = (UsernamePasswordToken) token;
		String pwd = new String(upt.getPassword());
		//通过令牌得到用户名密码
		//登录查询
		Emp emp = empBiz.findByUserNameAndPwd(upt.getUsername(), pwd);
		if(null==emp){
			return null;
		}
		//构造参数1：主角（登陆用户,之后可以拿到登陆用户）,参数二：授权码（密码），参数3：realm的名称
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(emp,pwd,getName());
		
		return info;
	}

}

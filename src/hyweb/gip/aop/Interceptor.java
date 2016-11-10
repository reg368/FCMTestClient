package hyweb.gip.aop;


import hyweb.gip.pojo.mybatis.table.InfoUser;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
public class Interceptor implements MethodInterceptor {
	
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		InfoUser infoUser = CheckLogin.check(request);
		
		CheckFunc.checkApFunc(infoUser, request);
		
		return methodInvocation.proceed();
	}

}

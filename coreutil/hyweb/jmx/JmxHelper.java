package hyweb.jmx;

import hyweb.jmx.JmxHelper.OneMBean;

import java.lang.management.ManagementFactory;
import java.util.Set;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.ReflectionException;

public class JmxHelper {
	public static interface OneMBean{
		public String getObjectName();
		public Object getAttr(String attr) throws Exception;
	}

	public OneMBean getC3P0MBean() throws Exception{
		String objectName = "com.mchange.v2.c3p0:type=PooledDataSource[*]";
		return new PlatformMBean(objectName);
	}
	public String getC3P0Msg() throws Exception{
		OneMBean mBean = this.getC3P0MBean();
		StringBuilder sb = new StringBuilder();
		sb.append("連線池最大大小：").append(mBean.getAttr("maxPoolSize")).append("<br/>\n");
		sb.append("使用中的連線數：").append(mBean.getAttr("numBusyConnections")).append("<br/>\n");
		//sb.append("statementCacheNumConnectionsWithCachedStatementsAllUsers：").append(mBean.getAttr("statementCacheNumConnectionsWithCachedStatementsAllUsers")).append("<br/>\n");
		//sb.append("閒置中的執行緒數量：").append(mBean.getAttr("threadPoolNumIdleThreads")).append("<br/>\n");
		sb.append("使用中的執行緒數量：").append(mBean.getAttr("threadPoolNumActiveThreads")).append("<br/>\n");		
		//sb.append("checkoutTimeout：").append(mBean.getAttr("checkoutTimeout")).append("<br/>\n");
		return sb.toString();
	}
}

class PlatformMBean implements OneMBean{
	private ObjectName objectName;
	private MBeanServer mbeanServer; 
	public PlatformMBean(String objectName) throws Exception{
		this.mbeanServer = ManagementFactory.getPlatformMBeanServer();
		Set<ObjectName> objectNameSet = this.mbeanServer.queryNames(new ObjectName(objectName), null);
		if(objectNameSet.size() != 1){
			throw new Exception("this object name:" + objectName + ", contain " + objectNameSet.size() + " object name" ); 
		}
		for(ObjectName on : objectNameSet){
			this.objectName = on;
		}
		/*
		for(MBeanAttributeInfo attr : this.mbeanServer.getMBeanInfo(this.objectName).getAttributes()){
			System.out.println(attr.getName());
		}
		*/
	}

	@Override
	public String getObjectName() {
		return this.objectName.getDomain();
	}

	@Override
	public Object getAttr(String attr) throws AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException {
		return this.mbeanServer.getAttribute(this.objectName, attr);
	}
}